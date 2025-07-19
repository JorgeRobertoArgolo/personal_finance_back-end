package com.jorgerobertoargolo.personal_finance.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "email")
    private String email;
}
