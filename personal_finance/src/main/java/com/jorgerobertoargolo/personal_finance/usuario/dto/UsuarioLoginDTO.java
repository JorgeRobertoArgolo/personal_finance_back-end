package com.jorgerobertoargolo.personal_finance.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO utilizado para autenticação de usuário.
 *
 * <p>Contém apenas e-mail e senha, necessários
 * para login no sistema.</p>
 *
 * @author Jorge Roberto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLoginDTO {

    @NotBlank
    @NotNull
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @NotNull
    private String senha;
}
