package com.jorgerobertoargolo.personal_finance.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de modelo utilizada para estruturar as respostas de erro da API.
 * Inclui o código de status HTTP, uma mensagem amigável e opcionalmente o stacktrace da exceção.
 *
 * A anotação @JsonInclude garante que o campo stacktrace só será serializado no JSON
 * se estiver presente (não nulo), evitando poluir a resposta em produção.
 *
 * Essa classe é utilizada por ApiExceptionHandler.
 *
 * @author Jorge Roberto
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private String message;
    private String stacktrace;
}
