package com.jorgerobertoargolo.personal_finance.transacao.entity;

import com.jorgerobertoargolo.personal_finance.categoria_transacao.entity.CategoriaTransacao;
import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import com.jorgerobertoargolo.personal_finance.transacao.entity.enums.Tipo;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa uma transação financeira realizada pelo usuário.
 *
 * <p>Cada transação possui:</p>
 * <ul>
 *   <li>Um valor monetário.</li>
 *   <li>Uma descrição opcional.</li>
 *   <li>Uma categoria ({@link CategoriaTransacao}).</li>
 *   <li>Um tipo ({@link Tipo}), indicando se é receita ou despesa.</li>
 *   <li>Uma data de ocorrência.</li>
 *   <li>Um {@link Usuario} responsável pela movimentação.</li>
 * </ul>
 *
 * <p>Herda de {@link PersistenceEntity}, que fornece um identificador único
 * gerado automaticamente.</p>
 *
 * @author Jorge Roberto
 */

@Entity
@Table(name = "transacoes")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class Transacao extends PersistenceEntity implements Serializable {

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(optional = false,  fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id",  nullable = false)
    private CategoriaTransacao categoria;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "data",  nullable = false)
    private LocalDate data;

    @ManyToOne(optional = false,  fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",  nullable = false)
    private Usuario usuario;
}
