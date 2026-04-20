package com.taskforge.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantService {

    private final ChatClient chatClient;

    public AiAssistantService(ChatClient.Builder chatClientBuilder) {
        // En Spring AI > 1.0.0-M1 el cliente se construye por inyección
        this.chatClient = chatClientBuilder.build();
    }

    public String generateProjectSummary(String projectDataJson) {
        String prompt = "Eres un asistente virtual de gestión de proyectos experto (Project Manager). " +
                "Analiza el siguiente resumen de proyecto en formato JSON y redacta un reporte ejecutivo " +
                "de máximo 3 párrafos destacando el progreso, tareas atrasadas, y sugiriendo prioridades " +
                "para el equipo de desarrollo. \n\nDatos:\n" + projectDataJson;
                
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String askQuestion(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
