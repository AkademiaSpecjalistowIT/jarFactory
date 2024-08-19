package pl.akademiaspecjalistowit.jarfactory.model;

import jakarta.persistence.*;
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
    private LocalDate deliveryDate;
    private Integer smallJars;
    private Integer mediumJars;
    private Integer largeJars;

    public JarOrderEntity(LocalDate deliveryDate, Integer smallJars, Integer mediumJars, Integer largeJars) {
        this.deliveryDate = deliveryDate;
        this.smallJars = smallJars;
        this.mediumJars = mediumJars;
        this.largeJars = largeJars;
        this.technicalId = UUID.randomUUID();
    }
}
