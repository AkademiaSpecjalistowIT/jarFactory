package pl.akademiaspecjalistowit.jarfactory.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.jar.JarException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JarOrderEntityTest {
    private final LocalDate CORRECT_DATE = LocalDate.of(2024, 9, 29);
    private final Integer CORRECT_QUANTITY_JARS = 100;
    private final Integer INCORRECT_QUANTITY_JARS = -100;
    @Test
    void should_create_order_with_correct_input_data() {
        //given&when
        JarOrderEntity jarOrderEntity = new JarOrderEntity(CORRECT_DATE, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS);
        //then
        assertThat(jarOrderEntity.getSmallJars()).isEqualTo(CORRECT_QUANTITY_JARS);
    }

    @Test
    void should_throw_exception_with_empty_input_delivery_date() throws JarException {
        //given&when
        Executable e = () -> new JarOrderEntity(null, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS);
        //then
        assertThrows(IllegalArgumentException.class, e);
    }

    @Test
    void should_throw_exception_with_incorrect_input_quantity_any_jars() throws JarException {
       //given&when
        Executable e = () -> new JarOrderEntity(CORRECT_DATE, CORRECT_QUANTITY_JARS, INCORRECT_QUANTITY_JARS, CORRECT_QUANTITY_JARS);
        //then
        assertThrows(IllegalArgumentException.class, e);
    }
}