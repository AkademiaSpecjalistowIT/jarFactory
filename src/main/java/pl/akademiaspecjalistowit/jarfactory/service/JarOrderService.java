package pl.akademiaspecjalistowit.jarfactory.service;

import com.github.fge.jsonpatch.JsonPatch;
import pl.akademiaspecjalistowit.model.JarOrderRequestDto;

import java.util.UUID;
import java.util.jar.JarException;

public interface JarOrderService {
    UUID addOrder(JarOrderRequestDto jarOrderRequestDto);

    void updateOrder(UUID orderId, JsonPatch patch);
}
