package com.jorgerobertoargolo.personal_finance.perfil_de_usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDeUsuarioPostRequestDTO {
/*
    @JsonProperty(value = "acesso")
    @NotBlank(message = "O acesso é obrigatório.")
    @NotNull(message = "O acesso não pode ser vazio.")
    private String acesso;
*/
    @JsonProperty(value = "descricao")
    @NotBlank(message = "A Descrição é obrigatória.")
    @NotNull(message = "A Descrição não pode ser vazia.")
    private String descricao;
}
