package com.usuario.minhaaplicacao.app.controller;

import com.usuario.minhaaplicacao.app.entity.dto.LoginRequest;
import com.usuario.minhaaplicacao.app.entity.dto.RegisterRequest;
import com.usuario.minhaaplicacao.app.entity.dto.TokenResponse;
import com.usuario.minhaaplicacao.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @Operation(summary = "Register new user",
        description = "Register a new user with name, email and password")
  public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequest request) {
    TokenResponse response = authService.register(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  @Operation(summary = "Authenticate user",
        description = "Authenticate user with email and password")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
    TokenResponse response = authService.authenticate(request.getEmail(), request.getPassword());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh access token",
        description = "Get a new access token using a valid refresh token")
  public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String refreshToken) {
    String token = refreshToken.startsWith("Bearer ")
          ? refreshToken.substring(7)
          : refreshToken;

    TokenResponse response = authService.refreshToken(token);
    return ResponseEntity.ok(response);
  }
}
