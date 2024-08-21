package pl.akademiaspecjalistowit.jarfactory.service;

import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;

import java.util.UUID;
import java.util.jar.JarException;

public interface JarOrderService {
    UUID addOrder(JarOrderRequestDto jarOrderRequestDto) throws JarException;
}
