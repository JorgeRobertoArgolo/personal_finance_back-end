package com.jorgerobertoargolo.personal_finance.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe responsável por capturar e tratar exceções lançadas na aplicação de forma centralizada.
 * Utiliza a anotação @RestControllerAdvice do Spring para interceptar exceções lançadas
 * em qualquer controller REST e gerar uma resposta padronizada.
 *
 * Se a propriedade `server.error.include-exception=true` estiver configurada no application.properties,
 * a resposta incluirá também o stacktrace da exceção.
 *
 * @author Jorge Roberto
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // Flag configurável no application.properties para decidir se exibe ou não o stacktrace no retorno
    @Value(value = "${server.error.include-exception:false}")
    private boolean printStackTrace;

    /**
     * Manipula exceções do tipo BusinessException lançadas na aplicação.
     * Retorna um erro HTTP com status 400 (BAD_REQUEST).
     *
     * @param businessException A exceção lançada
     * @param request A requisição atual
     * @return ResponseEntity com detalhes do erro
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBusinessException(
            final BusinessException businessException,
            final WebRequest request
            ) {
        final String messagemErro = businessException.getMessage();

        log.error("API error : {}", messagemErro, businessException);

        return contruirMensagemDeErro (
            businessException, messagemErro, HttpStatus.BAD_REQUEST, request
        );
    }

    /**
     * Constrói o objeto de resposta de erro (ErrorResponse) contendo status, mensagem e, opcionalmente, o stacktrace.
     *
     * @param exception A exceção lançada
     * @param message Mensagem de erro a ser exibida
     * @param httpStatus Código de status HTTP
     * @param request Informações da requisição
     * @return ResponseEntity contendo ErrorResponse
     */
    private ResponseEntity<Object> contruirMensagemDeErro(
            final Exception exception,
            final String message,
            final HttpStatus httpStatus,
            final WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message, null);

        if (this.printStackTrace) {
            errorResponse.setStacktrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
