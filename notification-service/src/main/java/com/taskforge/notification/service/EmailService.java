package com.taskforge.notification.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    // Simula el envío de un correo usando un hilo separado
    // Cubre Programación Concurrente/Threads del roadmap
    @Async
    public void sendEmail(UUID userId, String subject, String body) {
        System.out.println("Enviando correo a usuario " + userId + " en hilo: " + Thread.currentThread().getName());
        try {
            // Simulamos latencia de red
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Correo enviado exitosamente: [" + subject + "]");
    }
}
