package com.jorgerobertoargolo.personal_finance.person.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para retorno das informações básicas de uma pessoa.
 *
 * @author Jorge Roberto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaGetResponseDTO {
    @JsonProperty(value = "nome")
    private String nome;
}
