package com.taskforge.ai.controller;

import com.taskforge.common.dto.ApiResponse;
import com.taskforge.ai.service.AiAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    @Autowired
    private AiAssistantService aiService;

    // Recibe JSON de un proyecto y retorna análisis IA
    @PostMapping("/summarize-project")
    public ResponseEntity<ApiResponse<String>> summarizeProject(@RequestBody String projectDataJson) {
        String analysis = aiService.generateProjectSummary(projectDataJson);
        return ResponseEntity.ok(ApiResponse.success("Análisis IA generado con éxito", analysis));
    }

    // Endpoint genérico para chatear con la IA de DeepSeek
    @GetMapping("/chat")
    public ResponseEntity<ApiResponse<String>> chat(@RequestParam("message") String message) {
        // Aprovecharemos el mismo bean ChatModel inyectado en el aiService
        String response = aiService.askQuestion(message);
        return ResponseEntity.ok(ApiResponse.success("Respuesta IA", response));
    }
}
