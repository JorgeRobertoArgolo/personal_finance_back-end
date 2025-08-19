package com.jorgerobertoargolo.personal_finance.person.entity;

import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

/**
 * Representa uma pessoa no sistema.
 *
 * <p>Esta entidade armazena informações pessoais que estão vinculadas a um
 * {@link com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario}.
 * Cada usuário deve estar obrigatoriamente associado a uma única pessoa.</p>
 *
 * <p>Herda de {@link PersistenceEntity}, que fornece um identificador único
 * gerado automaticamente.</p>
 *
 * @author Jorge Roberto
 */

@Entity
@Table(name = "pessoas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Pessoa extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

}
