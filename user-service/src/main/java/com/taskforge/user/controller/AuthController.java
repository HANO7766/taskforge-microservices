package com.taskforge.user.controller;

import com.taskforge.common.dto.ApiResponse;
import com.taskforge.user.dto.AuthResponse;
import com.taskforge.user.dto.LoginRequest;
import com.taskforge.user.dto.RegisterRequest;
import com.taskforge.user.model.User;
import com.taskforge.user.security.JwtTokenProvider;
import com.taskforge.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        // Obtener datos del usuario logeado para enviar en respuesta
        org.springframework.security.core.userdetails.User userDetails = 
            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Extraer rol 
        com.taskforge.user.model.Role roleEnum = com.taskforge.user.model.Role.valueOf(
            userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "")
        );

        AuthResponse authResponse = new AuthResponse(jwt, userDetails.getUsername(), roleEnum);

        return ResponseEntity.ok(ApiResponse.success("Autenticación exitosa", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            User user = userService.registerUser(signUpRequest);
            return ResponseEntity.ok(ApiResponse.success("Usuario registrado exitosamente", user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
