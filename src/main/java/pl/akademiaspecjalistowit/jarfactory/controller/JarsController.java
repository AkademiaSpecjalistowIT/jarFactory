package pl.akademiaspecjalistowit.jarfactory.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.model.OrderResponse;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;

import java.util.UUID;


@AllArgsConstructor
@RestController
public class JarsController implements ApiApi {
    private final JarOrderService jarOrderService;

    @Override
    public ResponseEntity<OrderResponse> createJarOrder(JarOrderRequestDto jarOrderRequestDto) {
        UUID orderId = jarOrderService.addOrder(jarOrderRequestDto);
        OrderResponse response = new OrderResponse(orderId.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/api/v1/jars/order/{id}")
    public void updateJarOrder(@PathVariable("id") UUID orderId, @RequestBody JsonPatch patch) {
        jarOrderService.updateOrder(orderId, patch);
    }
}
