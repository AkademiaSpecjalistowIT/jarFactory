package pl.akademiaspecjalistowit.jarfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JarOrderRepository extends JpaRepository<JarOrderEntity, Long> {

    List<JarOrderEntity> getByDeliveryDate(LocalDate deliveryDate);
}
