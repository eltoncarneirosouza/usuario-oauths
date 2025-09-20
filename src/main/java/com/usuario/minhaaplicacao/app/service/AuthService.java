package com.usuario.minhaaplicacao.app.service;

import com.usuario.minhaaplicacao.app.entity.User;
import com.usuario.minhaaplicacao.app.entity.dto.RegisterRequest;
import com.usuario.minhaaplicacao.app.entity.dto.TokenResponse;
import com.usuario.minhaaplicacao.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public TokenResponse authenticate(String email, String password) {
    authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
    );

    User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    String accessToken = jwtService.generateAccessToken(user);

    return new TokenResponse(accessToken);
  }

  public TokenResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BadCredentialsException("Email já cadastrado");
    }

    User user = User.builder()
          .name(request.getName())
          .email(request.getEmail())
          .password(passwordEncoder.encode(request.getPassword()))
          .build();

    userRepository.save(user);

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return new TokenResponse(accessToken);
  }

  public TokenResponse refreshToken(String refreshToken) {
    String userEmail = jwtService.extractUsername(refreshToken);
    User user = userRepository.findByEmail(userEmail)
          .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    if (jwtService.isTokenValid(refreshToken, user)) {
      String accessToken = jwtService.generateAccessToken(user);
      return new TokenResponse(accessToken);
    }
    throw new BadCredentialsException("Refresh token inválido");
  }
}
