package pl.akademiaspecjalistowit.jarfactory.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEditDto;

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

    @Column(nullable = false, name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(nullable = false, name = "small_jars")
    private Integer smallJars;

    @Column(nullable = false, name = "medium_jars")
    private Integer mediumJars;

    @Column(nullable = false, name = "large_jars")
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

    public void updateAllFields(JarOrderEditDto jarOrderEdited) {
        LocalDate deliveryDate = jarOrderEdited.getDeliveryDate();
        Integer smallJars = jarOrderEdited.getSmallJars();
        Integer mediumJars = jarOrderEdited.getMediumJars();
        Integer largeJars = jarOrderEdited.getLargeJars();

        validateEntryParam(deliveryDate, smallJars, mediumJars, largeJars);
        this.deliveryDate = deliveryDate;
        this.smallJars = smallJars;
        this.mediumJars = mediumJars;
        this.largeJars = largeJars;
        this.technicalId = jarOrderEdited.getTechnicalId();
    }
}
