package com.jorgerobertoargolo.personal_finance.perfil_de_usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para retorno de informações de perfil de usuário.
 * Representa os dados de acesso e descrição do perfil.
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDeUsuarioGetResponseDTO {
    @JsonProperty(value = "acesso")
    private String acesso;

    @JsonProperty(value = "descricao")
    private String descricao;
}
