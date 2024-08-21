package pl.akademiaspecjalistowit.jarfactory.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "max-factory-capacity")
@Getter
@Setter
public class ApiProperties {
    private Integer L_jar;
    private Integer M_jar;
    private Integer S_jar;
}
