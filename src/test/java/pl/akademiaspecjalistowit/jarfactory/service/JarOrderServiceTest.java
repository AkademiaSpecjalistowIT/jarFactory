package pl.akademiaspecjalistowit.jarfactory.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.akademiaspecjalistowit.jarfactory.configuration.EmbeddedPostgresConfiguration;
import pl.akademiaspecjalistowit.jarfactory.exception.JarFactoryException;
import pl.akademiaspecjalistowit.jarfactory.exception.NoExistOrderException;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderEntity;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.repository.JarOrderRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ExtendWith(EmbeddedPostgresConfiguration.EmbeddedPostgresExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EmbeddedPostgresConfiguration.class})
@ActiveProfiles("test")
class JarOrderServiceTest {
    private final LocalDate CORRECT_DATE = LocalDate.of(2024, 9, 29);
    private final Integer CORRECT_QUANTITY_JARS = 100;
    private final Integer INCORRECT_QUANTITY_JARS = -100;
    @Autowired
    private JarOrderService jarOrderService;
    @Autowired
    private JarOrderRepository jarOrderRepository;

    @Value("${parametrs.limits.L_jar}")
    private Integer maxFactoryCapacityLargeJars;
    @Value("${parametrs.limits.M_jar}")
    private Integer maxFactoryCapacityMediumJars;
    @Value("${parametrs.limits.S_jar}")
    private Integer maxFactoryCapacitySmallJars;

    @Test
    void should_create_order_with_correct_input_data() throws JarException {
        //given
        //when
        jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        List<JarOrderEntity> byDeliveryDate = jarOrderRepository.getByDeliveryDate(CORRECT_DATE);
        //then
        assertThat(byDeliveryDate.size()).isEqualTo(1);
    }

    @Test
    void should_throw_exception_with_empty_input_delivery_date() throws JarException {
        //given
        //when
        Executable e = () -> jarOrderService.addOrder(new JarOrderRequestDto(null, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        //then
        assertThrows(IllegalArgumentException.class, e);
    }

    @Test
    void should_throw_exception_with_incorrect_input_quantity_any_jars() throws JarException {
        //given
        //when
        Executable e = () -> jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, CORRECT_QUANTITY_JARS, INCORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        //then
        assertThrows(IllegalArgumentException.class, e);
    }

    @Test
    void should_throw_exception_when_input_quantity_any_jars_exceeds_than_max_capacity() throws JarException {
        //given
        //when
        Executable e = () -> jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, maxFactoryCapacitySmallJars + 1, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        //then
        assertThrows(JarFactoryException.class, e);
    }

    @Test
    void should_throw_exception_when_total_quantity_exceeds_max_capacity_for_day() throws JarException {
        //given
        jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, maxFactoryCapacitySmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        //when
        Executable e = () -> jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, 1, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        //then
        assertThrows(JarFactoryException.class, e);
    }

    @Test
    void should_update_order_when_input_quantitySmallJars_is_correct() throws IOException {
        //given
        Integer firstSmallJars = 100;
        Integer changedSmallJars = 101;
        UUID uuidNewOrder = jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, firstSmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String patchString = String.format("[{\"op\": \"replace\", \"path\": \"/smallJars\", \"value\": %d}]", changedSmallJars);
        JsonNode jsonPatchNode = objectMapper.readTree(patchString);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonPatchNode);

        //when
        jarOrderService.updateOrder(uuidNewOrder, jsonPatch);
        //then
        JarOrderEntity byTechnicalId = jarOrderRepository.findByTechnicalId(uuidNewOrder).orElseThrow(() -> new NoExistOrderException("There is not order with ID: " + uuidNewOrder));
        assertThat(byTechnicalId.getSmallJars()).isEqualTo(changedSmallJars);
    }

    @Test
    void should_throw_exception_when_input_quantitySmallJars_exceeds_max_capacity_for_day_when_there_is_onse_order() throws IOException {
        //given
        Integer firstSmallJars = 100;
        Integer changedSmallJars = maxFactoryCapacitySmallJars + 1;
        UUID uuidNewOrder = jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, firstSmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String patchString = String.format("[{\"op\": \"replace\", \"path\": \"/smallJars\", \"value\": %d}]", changedSmallJars);
        JsonNode jsonPatchNode = objectMapper.readTree(patchString);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonPatchNode);

        //when
        Executable e = () -> jarOrderService.updateOrder(uuidNewOrder, jsonPatch);
        //then
        assertThrows(JarFactoryException.class, e);
    }

    @Test
    void should_throw_exception_when_input_quantitySmallJars_is_negative() throws IOException {
        //given
        Integer firstSmallJars = 100;
        Integer changedSmallJars = -100;
        UUID uuidNewOrder = jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, firstSmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String patchString = String.format("[{\"op\": \"replace\", \"path\": \"/smallJars\", \"value\": %d}]", changedSmallJars);
        JsonNode jsonPatchNode = objectMapper.readTree(patchString);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonPatchNode);

        //when
        Executable e = () -> jarOrderService.updateOrder(uuidNewOrder, jsonPatch);
        //then
        assertThrows(IllegalArgumentException.class, e);
    }

    @Test
    void should_throw_exception_when_input_quantitySmallJars_exceeds_max_capacity_for_day_when_there_are_some_orders() throws IOException {
        //given
        Integer firstSmallJars = 100;
        Integer changedSmallJars = maxFactoryCapacitySmallJars + 1 - firstSmallJars;
        jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, firstSmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));
        UUID uuidNewOrder = jarOrderService.addOrder(new JarOrderRequestDto(CORRECT_DATE, firstSmallJars, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String patchString = String.format("[{\"op\": \"replace\", \"path\": \"/smallJars\", \"value\": %d}]", changedSmallJars);
        JsonNode jsonPatchNode = objectMapper.readTree(patchString);
        JsonPatch jsonPatch = JsonPatch.fromJson(jsonPatchNode);
        System.out.println(jarOrderRepository.findAll().toArray().toString());

        //when
        Executable e = () -> jarOrderService.updateOrder(uuidNewOrder, jsonPatch);
        //then
        assertThrows(JarFactoryException.class, e);
    }

}