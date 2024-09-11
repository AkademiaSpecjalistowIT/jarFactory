package pl.akademiaspecjalistowit.jarfactory.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import pl.akademiaspecjalistowit.api.JarsApi;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;
import pl.akademiaspecjalistowit.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.model.OrderResponse;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JarFactoryController implements JarsApi {
    private final JarOrderService jarOrderService;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/order")
//    public UUID createNewJarsOrder(@RequestBody JarOrderRequestDto jarOrderRequestDto) throws JarException {
//        return jarOrderService.addOrder(jarOrderRequestDto);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PatchMapping("/order/{id}")
//    public void updateJarOrder(@PathVariable("id") UUID orderId, @RequestBody JsonPatch patch) {
//        jarOrderService.updateOrder(orderId, patch);
//    }

    @Override
    public ResponseEntity<OrderResponse> createJarOrder(JarOrderRequestDto jarOrderRequestDto) {
        UUID orderId = jarOrderService.addOrder(jarOrderRequestDto);

        OrderResponse orderResponse = new OrderResponse(orderId.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @Override
    public ResponseEntity<Void> updateJarOrder(JarOrderRequestDto jarOrderRequestDto) {
        return JarsApi.super.updateJarOrder(jarOrderRequestDto);
    }

    @Override
    @PatchMapping("/order/{id}")
    public Optional<NativeWebRequest> getRequest() {
        return JarsApi.super.getRequest();
    }
}
