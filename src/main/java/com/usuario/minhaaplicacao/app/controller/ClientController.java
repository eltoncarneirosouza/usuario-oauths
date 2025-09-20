package com.usuario.minhaaplicacao.app.controller;

import com.usuario.minhaaplicacao.app.entity.Client;
import com.usuario.minhaaplicacao.app.entity.dto.ClientCreateRequest;
import com.usuario.minhaaplicacao.app.entity.dto.ClientUpdateRequest;
import com.usuario.minhaaplicacao.app.entity.dto.ValidationErrorResponse;
import com.usuario.minhaaplicacao.app.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "API para gerenciamento de clientes")
@SecurityRequirement(name = "access_token")
public class ClientController {

  private final ClientService clientService;

  @PostMapping
  @Operation(
        summary = "Criar cliente",
        description = "Cria um novo cliente no sistema. CPF é obrigatório na criação.",
        responses = {
              @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso"
              ),
              @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação",
                    content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = ValidationErrorResponse.class)
                    )
              )
        }
  )
  public ResponseEntity<Client> createClient(@Valid @RequestBody ClientCreateRequest request) {
    Client savedClient = clientService.save(request);
    return ResponseEntity.ok(savedClient);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar cliente", description = "Retorna um cliente pelo ID")
  @ApiResponse(responseCode = "200", description = "Cliente encontrado")
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  public ResponseEntity<Client> getClient(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.findById(id));
  }

  @GetMapping
  @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados")
  @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
  public ResponseEntity<List<Client>> getAllClients() {
    return ResponseEntity.ok(clientService.findAll());
  }

  @PutMapping("/{id}")
  @Operation(
        summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente. CPF não pode ser alterado.",
        responses = {
              @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso"
              ),
              @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
              )
        }
  )
  public ResponseEntity<Client> updateClient(
        @PathVariable Long id,
        @Valid @RequestBody ClientUpdateRequest request) {
    return ResponseEntity.ok(clientService.update(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
  @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso")
  @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    clientService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(Exception ex) {
    Map<String, String> errors = new HashMap<>();
    String message = ex.getMessage();

    if (message != null && message.contains("CPF")) {
      errors.put("cpf", message);
    } else {
      errors.put("error", "Erro de validação");
    }

    ValidationErrorResponse errorResponse = new ValidationErrorResponse(
          HttpStatus.BAD_REQUEST.value(),
          "Erro de validação",
          "Existem campos inválidos na requisição",
          errors,
          LocalDateTime.now()
    );

    return ResponseEntity.badRequest().body(errorResponse);
  }
}
