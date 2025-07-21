package com.jorgerobertoargolo.personal_finance.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "nome")
    @NotBlank (message = "O Nome é obrigatório.")
    @NotNull (message = "O Nome não pode ser vazio.")
    private String nome;

    @JsonProperty(value = "email")
    @Email(message = "E-mail inválido")
    private String email;

    @JsonProperty(value = "senha")
    @NotBlank (message = "A senha é obrigatório.")
    @NotNull (message = "A senha não pode ser vazio.")
    @Length(min = 4, max = 100)
    private String senha;
}
