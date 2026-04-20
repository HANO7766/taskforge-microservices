package com.taskforge.project.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

// FeignClient para llamar al api-gateway o directamente al user-service
// A través de Eureka encuentra "user-service"
@FeignClient(name = "user-service")
public interface UserServiceClient {

    // endpoint simulado que debería existir en el User Service en el futuro
    @GetMapping("/api/v1/users/{id}/exists")
    boolean checkUserExists(@PathVariable("id") UUID userId);

}
