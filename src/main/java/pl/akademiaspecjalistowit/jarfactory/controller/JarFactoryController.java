package pl.akademiaspecjalistowit.jarfactory.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;

import java.util.UUID;
import java.util.jar.JarException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/jars")
public class JarFactoryController {
    private final JarOrderService jarOrderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order")
    public UUID createNewJarsOrder(@RequestBody JarOrderRequestDto jarOrderRequestDto) throws JarException {
        return jarOrderService.addOrder(jarOrderRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/order/{id}")
    public void updateJarOrder(@PathVariable("id") UUID orderId, @RequestBody JsonPatch patch) {
        jarOrderService.updateOrder(orderId, patch);
    }
}
