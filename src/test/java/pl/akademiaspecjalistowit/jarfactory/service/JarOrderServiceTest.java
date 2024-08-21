package pl.akademiaspecjalistowit.jarfactory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.akademiaspecjalistowit.jarfactory.configuration.EmbeddedPostgresConfiguration;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.repository.JarOrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.jar.JarException;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ExtendWith(EmbeddedPostgresConfiguration.EmbeddedPostgresExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EmbeddedPostgresConfiguration.class})
@ActiveProfiles("test")
//@SpringBootTest
class JarOrderServiceTest {
    private final LocalDate CORRECT_DATE = LocalDate.of(2024, 9, 29);
    private final Integer CORRECT_QUANTITY_JARS = 100;
    @Autowired
    private JarOrderService jarOrderService;
    @Autowired
    private JarOrderRepository jarOrderRepository;

    @Test
    void schould_create_order_with_correct_input_date() throws JarException {
        //given
        //when
        jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        List<JarOrderEntity> byDeliveryDate = jarOrderRepository.getByDeliveryDate(CORRECT_DATE);
        //then
        assertThat(byDeliveryDate.size()).isEqualTo(1);
    }

}