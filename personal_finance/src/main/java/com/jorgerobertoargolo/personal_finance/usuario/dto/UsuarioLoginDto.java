package com.jorgerobertoargolo.personal_finance.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioLoginDto {

    @NotBlank
    @NotNull
    @Email(message = "Email inválido")
    private String email;

    @NotBlank
    @NotNull
    private String senha;
}
