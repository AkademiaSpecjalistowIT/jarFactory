package pl.akademiaspecjalistowit.jarfactory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.jarfactory.configuration.ApiProperties;
import pl.akademiaspecjalistowit.jarfactory.exception.JarFactoryException;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.repository.JarOrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.jar.JarException;
import java.util.stream.Collectors;

@Service
public class JarOrderServiceImpl implements JarOrderService {
    private final JarOrderRepository jarOrderRepository;
    @Autowired
    private ApiProperties apiProperties;

    public JarOrderServiceImpl(JarOrderRepository jarOrderRepository) {
        this.jarOrderRepository = jarOrderRepository;
    }

    @Override
    public UUID addOrder(JarOrderRequestDto jarOrderRequestDto) throws JarException {
        LocalDate deliveryDate = jarOrderRequestDto.getDeliveryDate();
        Integer smallJars = jarOrderRequestDto.getSmallJars();
        Integer mediumJars = jarOrderRequestDto.getMediumJars();
        Integer largeJars = jarOrderRequestDto.getLargeJars();
        checkMaxCapabilities(deliveryDate, smallJars, mediumJars, largeJars);
        JarOrderEntity jarOrder = new JarOrderEntity(deliveryDate, smallJars, mediumJars, largeJars);
        jarOrderRepository.save(jarOrder);
        return jarOrder.getTechnicalId();
    }

    private void checkMaxCapabilities(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) throws JarException {
        List<JarOrderEntity> listOfRequiredQuantities = jarOrderRepository.getByDeliveryDate(deliveryDate);

        Map<String, Integer> listOfExistingQuantitiesForThisDate = getTotalAmountForDate(listOfRequiredQuantities);

        Integer existingSmallJars = listOfExistingQuantitiesForThisDate.getOrDefault("smallJars", 0);
        Integer existingMediumJars = listOfExistingQuantitiesForThisDate.getOrDefault("mediumJars", 0);
        Integer existingLargeJars = listOfExistingQuantitiesForThisDate.getOrDefault("largeJars", 0);

        Integer productionСapacitySmallJars = apiProperties.getS_jar() - smallJars - existingSmallJars;
        Integer productionСapacityMediumJars = apiProperties.getM_jar() - mediumJars - existingMediumJars;
        Integer productionСapacityLargeJars = apiProperties.getL_jar() - largeJars - existingLargeJars;

        StringBuilder textError = generateErrorMessage(productionСapacitySmallJars, productionСapacityMediumJars, productionСapacityLargeJars);
        if (textError.length() > 0) {
            throw new JarFactoryException("Zamówienie przekraczajace zdolności produkcyjne" + System.lineSeparator() + textError);
        }
    }

    private static StringBuilder generateErrorMessage(Integer productionСapacitySmallJars, Integer productionСapacityMediumJars, Integer productionСapacityLargeJars) {
        StringBuilder textError = new StringBuilder();
        if (productionСapacitySmallJars < 0) {
            textError = textError.append(" for small jars - ").append(productionСapacitySmallJars * (-1)).append(System.lineSeparator());
        }
        if (productionСapacityMediumJars < 0) {
            textError = textError.append(" for medium jars - ").append(productionСapacityMediumJars * (-1)).append(System.lineSeparator());
        }
        if (productionСapacityLargeJars < 0) {
            textError = textError.append(" for large jars - ").append(productionСapacityLargeJars * (-1)).append(System.lineSeparator());
        }
        return textError;
    }

    private static Map<String, Integer> getTotalAmountForDate(List<JarOrderEntity> listOfRequiredQuantities) {
        Map<String, Integer> totalQuantities = listOfRequiredQuantities.stream()
                .collect(Collectors.groupingBy(
                        order -> "smallJars",
                        Collectors.summingInt(JarOrderEntity::getSmallJars)
                ));
        totalQuantities.merge("mediumJars", listOfRequiredQuantities.stream()
                .collect(Collectors.summingInt(JarOrderEntity::getMediumJars)), Integer::sum);

        totalQuantities.merge("largeJars", listOfRequiredQuantities.stream()
                .collect(Collectors.summingInt(JarOrderEntity::getLargeJars)), Integer::sum);
        return totalQuantities;
    }
}
