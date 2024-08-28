package pl.akademiaspecjalistowit.jarfactory.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class JarOrderRequestDto {
    @NotNull(message = "Delivery date cannot be null")
    private LocalDate deliveryDate;

    @NotNull(message = "Small jars count cannot be null")
    @Min(value = 0, message = "Small jars count must be a positive integer or zero")
    private Integer smallJars;

    @NotNull(message = "Medium jars count cannot be null")
    @Min(value = 0, message = "Medium jars count must be a positive integer or zero")
    private Integer mediumJars;

    @NotNull(message = "Large jars count cannot be null")
    @Min(value = 0, message = "Large jars count must be a positive integer or zero")
    private Integer largeJars;
}
