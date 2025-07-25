package com.jorgerobertoargolo.personal_finance.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class ApiExceptionHandler {

    // Flag configurável no application.properties para decidir se exibe ou não o stacktrace no retorno
    @Value(value = "${server.error.include-exception:false}")
    private boolean printStackTrace;

    /**
     * Manipula exceções do tipo {@link MethodArgumentNotValidException}, que ocorrem quando
     * os dados enviados na requisição não atendem às validações definidas por anotações
     * como {@code @NotNull}, {@code @Size}, entre outras.
     *
     * Extrai os erros de campo e retorna uma resposta estruturada contendo detalhes sobre
     * os campos inválidos e as mensagens de validação associadas.
     *
     * @param validationException exceção lançada pelo Spring quando há falha na validação dos argumentos da requisição.
     * @return ResponseEntity contendo os detalhes da exceção de validação no corpo da resposta,
     * com código de status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleValidationException(
            MethodArgumentNotValidException validationException
    ) {
        log.error("Api Error : ", validationException);

        List<FieldError> fieldErrors = validationException.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Validation failed")
                        .details("Verifique os campos e corrija os erros")
                        .developerMessage(validationException.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

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
