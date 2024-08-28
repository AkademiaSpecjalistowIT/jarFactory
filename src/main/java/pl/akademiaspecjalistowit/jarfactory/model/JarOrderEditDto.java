package pl.akademiaspecjalistowit.jarfactory.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class JarOrderEditDto {
    private UUID technicalId;

    private LocalDate deliveryDate;

    private Integer smallJars;

    private Integer mediumJars;

    private Integer largeJars;
}
