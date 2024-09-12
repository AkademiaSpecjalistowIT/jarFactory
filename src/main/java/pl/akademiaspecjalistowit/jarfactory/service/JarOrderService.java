package pl.akademiaspecjalistowit.jarfactory.service;

import com.github.fge.jsonpatch.JsonPatch;
import pl.akademiaspecjalistowit.jarfactory.exception.JarFactoryException;
import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;

import java.util.UUID;

public interface JarOrderService {

    UUID addOrder(JarOrderRequestDto jarOrderRequestDto) throws JarFactoryException;

    void updateOrder(UUID orderId, JsonPatch patch);
}
