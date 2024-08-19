package pl.akademiaspecjalistowit.jarfactory.service;

import pl.akademiaspecjalistowit.jarfactory.model.JarOrderRequestDto;

import java.util.UUID;

public interface JarOrderService {
    UUID addOrder(JarOrderRequestDto jarOrderRequestDto);
}
