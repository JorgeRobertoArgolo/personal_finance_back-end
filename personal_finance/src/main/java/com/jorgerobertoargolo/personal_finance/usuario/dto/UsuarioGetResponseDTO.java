package com.jorgerobertoargolo.personal_finance.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.dto.PerfilDeUsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.person.dto.PessoaGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de resposta para retorno de informações do usuário.
 *
 * <p>Inclui os dados básicos de identificação,
 * informações pessoais e perfil de acesso.</p>
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "pessoa")
    private PessoaGetResponseDTO pessoa;

    @JsonProperty(value = "perfil")
    private PerfilDeUsuarioGetResponseDTO perfil;
}
