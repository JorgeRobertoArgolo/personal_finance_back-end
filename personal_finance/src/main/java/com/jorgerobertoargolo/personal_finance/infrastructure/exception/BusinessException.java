package com.jorgerobertoargolo.personal_finance.infrastructure.exception;

import java.io.Serial;

/**
 * Classe de exceção personalizada para representar erros de regra de negócio.
 * Pode ser utilizada em qualquer parte da aplicação onde uma regra de negócio for violada.
 *
 * @author Jorge Roberto
 */
public class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public BusinessException() {
    super();
  }

  public BusinessException(final String message) {
        super(message);
    }

  public BusinessException(final Throwable cause) {
    super(cause);
  }

  public BusinessException(final String message, final Throwable cause) {
    super(message,  cause);
  }
}
