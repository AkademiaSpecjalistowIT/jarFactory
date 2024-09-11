package pl.akademiaspecjalistowit.jarfactory.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "jar_orders")
@DynamicUpdate
public class JarOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID technicalId;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(nullable = false)
    private Integer smallJars;

    @Column(nullable = false)
    private Integer mediumJars;

    @Column(nullable = false)
    private Integer largeJars;

    @Version
    private Integer version;

    public JarOrderEntity(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) {
        validateEntryParam(deliveryDate, smallJars, mediumJars, largeJars);
        this.deliveryDate = deliveryDate;
        this.smallJars = smallJars;
        this.mediumJars = mediumJars;
        this.largeJars = largeJars;
        this.technicalId = UUID.randomUUID();
        this.version = 0;
    }

    private static void validateEntryParam(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) {
        validateDeliveryDate(deliveryDate);
        validateQuantityJars(smallJars, "Small");
        validateQuantityJars(mediumJars, "Medium");
        validateQuantityJars(largeJars, "Large");
    }

    private static void validateQuantityJars(Integer Jars, String formJars) {
        if (Jars == null || Jars < 0) {
            throw new IllegalArgumentException(formJars + " jars count must be a positive integer or zero and cannot be null");
        }
    }

    private static void validateDeliveryDate(LocalDate deliveryDate) {
        if (deliveryDate == null) {
            throw new IllegalArgumentException("Delivery date cannot be null");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTechnicalId(UUID technicalId) {
        this.technicalId = technicalId;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        validateDeliveryDate(deliveryDate);
        this.deliveryDate = deliveryDate;
    }

    public void setSmallJars(Integer smallJars) {
        validateQuantityJars(smallJars, "Small");
        this.smallJars = smallJars;
    }

    public void setMediumJars(Integer mediumJars) {
        validateQuantityJars(mediumJars, "Medium");
        this.mediumJars = mediumJars;
    }

    public void setLargeJars(Integer largeJars) {
        validateQuantityJars(largeJars, "Large");
        this.largeJars = largeJars;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
