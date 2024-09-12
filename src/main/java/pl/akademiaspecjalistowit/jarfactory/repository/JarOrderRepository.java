package pl.akademiaspecjalistowit.jarfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akademiaspecjalistowit.jarfactory.entity.JarOrderEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JarOrderRepository extends JpaRepository<JarOrderEntity, Long> {

    List<JarOrderEntity> getByDeliveryDate(LocalDate deliveryDate);
    Optional<JarOrderEntity> findByTechnicalId(UUID orderId);

}
