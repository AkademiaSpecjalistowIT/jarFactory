package pl.akademiaspecjalistowit.jarfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
@Repository
public interface JarOrderRepository extends JpaRepository<JarOrderEntity, Long> {
}
