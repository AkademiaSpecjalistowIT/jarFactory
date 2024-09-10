package pl.akademiaspecjalistowit.jarfactory.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;
import pl.akademiaspecjalistowit.jarfactory.model.OrderResponse;
import pl.akademiaspecjalistowit.jarfactory.model.PatchRequestInner;
import pl.akademiaspecjalistowit.jarfactory.service.JarOrderService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
public class JarsController implements JarsApi {
    private final JarOrderService jarOrderService;

    @Override
    public ResponseEntity<OrderResponse> createJarOrder(JarOrderRequestDto jarOrderRequestDto) {
        UUID orderId = jarOrderService.addOrder(jarOrderRequestDto);
        OrderResponse response = new OrderResponse(orderId.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> patchJarOrder(UUID id, List<PatchRequestInner> patchRequestInner) {
        return JarsApi.super.patchJarOrder(id, patchRequestInner);
    }
}
