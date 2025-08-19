package com.jorgerobertoargolo.personal_finance.categoria_transacao.entity;

import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma categoria de transação financeira.
 *
 * <p>Uma categoria organiza as {@link Transacao}, permitindo que o usuário
 * classifique suas movimentações financeiras (ex: Alimentação, Transporte, Lazer).</p>
 *
 * <p>Herda de {@link PersistenceEntity}, que fornece um identificador único
 * gerado automaticamente.</p>
 *
 * @author Jorge Roberto
 */

@Entity
@Table(name = "categorias_transacoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CategoriaTransacao extends PersistenceEntity implements Serializable {

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes = new ArrayList<>();
}
