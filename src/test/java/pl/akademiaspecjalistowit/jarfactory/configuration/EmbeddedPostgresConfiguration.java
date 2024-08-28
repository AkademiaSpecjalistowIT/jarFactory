package pl.akademiaspecjalistowit.jarfactory.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testcontainers.utility.DockerImageName;
import pl.akademiaspecjalistowit.jarfactory.mapper.JarMapper;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
import pl.akademiaspecjalistowit.jarfactory.repository.JarOrderRepository;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderServiceImpl;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableJpaRepositories(basePackageClasses = JarOrderRepository.class)
@EntityScan(basePackageClasses = JarOrderEntity.class)
public class EmbeddedPostgresConfiguration {
    private static EmbeddedPostgres embeddedPostgres;

    @Bean
    public DataSource dataSource() throws IOException {
        embeddedPostgres = EmbeddedPostgres.builder()
                .setImage(DockerImageName.parse("postgres:14.1"))
                .start();

        return embeddedPostgres.getPostgresDatabase();
    }

    @Bean
    public JarOrderService jarOrderService(JarOrderRepository jarOrderRepository, ApiProperties apiProperties) {
        return new JarOrderServiceImpl(jarOrderRepository, apiProperties, new ObjectMapper(), new JarMapper());
    }

    @Bean
    public ApiProperties apiProperties() {
        return new ApiProperties();
    }

    public static class EmbeddedPostgresExtension implements AfterAllCallback {
        @Override
        public void afterAll(ExtensionContext context) throws Exception {
            if (embeddedPostgres == null) {
                return;
            }
            embeddedPostgres.close();
        }
    }
}
