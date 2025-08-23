package com.jorgerobertoargolo.personal_finance.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorgerobertoargolo.personal_finance.categoria_transacao.dto.CategoriaTransacaoResponseDTO;
import com.jorgerobertoargolo.personal_finance.transacao.entity.enums.Tipo;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para requisições de criação e atualização de transações.
 * Contém os dados necessários para persistir uma transação no sistema.
 *
 * @author Jorge Roberto
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TransacaoPostRequestDto {

    @JsonProperty("valor")
    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @JsonProperty("descricao")
    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres.")
    private String descricao;

    @JsonProperty("categoria")
    @NotNull(message = "A categoria é obrigatória.")
    private CategoriaTransacaoResponseDTO categoria;

    @JsonProperty("tipo")
    @NotNull(message = "O tipo é obrigatório.")
    private Tipo tipo;

    @JsonProperty("data")
    @NotNull(message = "A data é obrigatória.")
    @PastOrPresent(message = "A data não pode ser futura.")
    private LocalDate data;

    @JsonProperty("emailUsuario")
    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail do usuário é obrigatório.")
    private String emailUsuario;
}
