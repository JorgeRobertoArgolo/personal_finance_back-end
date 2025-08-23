package com.jorgerobertoargolo.personal_finance.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorgerobertoargolo.personal_finance.categoria_transacao.dto.CategoriaTransacaoResponseDTO;
import com.jorgerobertoargolo.personal_finance.transacao.entity.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para resposta de transações.
 * Contém os dados da transação que serão retornados ao cliente.
 *
 * @author Jorge Roberto
 */
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TransacaoResponseDto {
    @JsonProperty(value = "valor")
    private BigDecimal valor;

    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "categoria")
    private CategoriaTransacaoResponseDTO categoria;

    @JsonProperty(value = "tipo")
    private Tipo tipo;

    @JsonProperty(value = "data")
    private LocalDate data;
}
