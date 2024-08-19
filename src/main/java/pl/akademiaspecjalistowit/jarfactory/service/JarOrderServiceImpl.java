package pl.akademiaspecjalistowit.jarfactory.service;

import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.repository.JarOrderRepository;

import java.util.UUID;

@Service
public class JarOrderServiceImpl implements JarOrderService {
    private final JarOrderRepository jarOrderRepository;

    public JarOrderServiceImpl(JarOrderRepository jarOrderRepository) {
        this.jarOrderRepository = jarOrderRepository;
    }

    @Override
    public UUID addOrder(JarOrderRequestDto jarOrderRequestDto) {
        JarOrderEntity jarOrder = new JarOrderEntity(jarOrderRequestDto.getDeliveryDate(),
                jarOrderRequestDto.getSmallJars(),
                jarOrderRequestDto.getMediumJars(),
                jarOrderRequestDto.getLargeJars());
        jarOrderRepository.save(jarOrder);
        return jarOrder.getTechnicalId();
    }
}
