package pl.akademiaspecjalistowit.jarfactory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@Table(name = "jar_orders")
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

    public JarOrderEntity(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) {
        validateEntryParam(deliveryDate, smallJars, mediumJars, largeJars);
        this.deliveryDate = deliveryDate;
        this.smallJars = smallJars;
        this.mediumJars = mediumJars;
        this.largeJars = largeJars;
        this.technicalId = UUID.randomUUID();
    }

    private static void validateEntryParam(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) {
        if (deliveryDate == null) {
            throw new IllegalArgumentException("Delivery date cannot be null");
        }
        if (smallJars == null || smallJars < 0) {
            throw new IllegalArgumentException("Small jars count must be a positive integer or zero and cannot be null");
        }
        if (mediumJars == null || mediumJars < 0) {
            throw new IllegalArgumentException("Medium jars count must be a positive integer or zero and cannot be null");
        }
        if (largeJars == null || largeJars < 0) {
            throw new IllegalArgumentException("Large jars count must be a positive integer or zero and cannot be null");
        }
    }
}
