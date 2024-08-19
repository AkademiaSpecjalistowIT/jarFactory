package pl.akademiaspecjalistowit.jarfactory.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/jars/order")
public class JarFactoryController {
    private final JarOrderService jarOrderService;
    @PostMapping
    public UUID createNewJarsOrder(@RequestBody JarOrderRequestDto jarOrderRequestDto) {
        return jarOrderService.addOrder(jarOrderRequestDto);
    }
}
