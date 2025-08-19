package com.jorgerobertoargolo.personal_finance.usuario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.dto.PerfilDeUsuarioPostRequestDTO;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.PerfilDeUsuario;
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
public class UsuarioPostRequestDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty(value = "perfil")
    private PerfilDeUsuarioPostRequestDTO perfil;


}
