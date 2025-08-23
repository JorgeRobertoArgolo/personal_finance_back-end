package com.jorgerobertoargolo.personal_finance.categoria_transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTransacaoResponseDTO {

    @JsonProperty(value = "nome")
    @NotBlank(message = "O Nome é obrigatório.")
    @NotNull(message = "O Nome não pode ser vazio.")
    private String nome;
}
