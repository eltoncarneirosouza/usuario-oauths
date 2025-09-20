package com.usuario.minhaaplicacao.app.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Resposta de erro de validação")
public record ValidationErrorResponse(
      @Schema(description = "Código do status HTTP")
      int status,
      @Schema(description = "Título do erro")
      String title,
      @Schema(description = "Mensagem descritiva")
      String message,
      @Schema(description = "Detalhes dos erros por campo")
      Map<String, String> errors,
      @Schema(description = "Data e hora do erro")
      LocalDateTime timestamp
) {

}