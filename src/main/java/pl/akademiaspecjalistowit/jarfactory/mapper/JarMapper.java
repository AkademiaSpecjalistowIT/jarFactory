package pl.akademiaspecjalistowit.jarfactory.mapper;

import org.springframework.stereotype.Component;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEditDto;
import pl.akademiaspecjalistowit.jarfactory.entity.JarOrderEntity;

@Component
public class JarMapper {

//    public JarOrderEntity toEntity(JarOrderRequestDto jarOrderRequestDto) {
//        return new JarOrderEntity(jarOrderRequestDto.getDeliveryDate(), jarOrderRequestDto.getSmallJars(),
//                jarOrderRequestDto.getMediumJars(), jarOrderRequestDto.getLargeJars());
//    }

    public JarOrderEditDto fromEntityToJarOrderEdit(JarOrderEntity entity) {
        return new JarOrderEditDto(entity.getTechnicalId(), entity.getDeliveryDate(),
                entity.getSmallJars(), entity.getMediumJars(), entity.getLargeJars());
    }
}
