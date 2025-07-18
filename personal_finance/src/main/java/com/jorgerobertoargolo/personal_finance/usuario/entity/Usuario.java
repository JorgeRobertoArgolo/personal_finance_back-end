package com.jorgerobertoargolo.personal_finance.usuario.entity;

import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

/**
 * Entidade que representa um usuário do sistema.
 *
 * <p>Esta classe herda de {@link com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity}
 * e possui um identificador único {@code id} gerado automaticamente.</p>
 *
 * <p>Os atributos principais do usuário incluem:
 * <ul>
 *     <li>{@code nome}: o nome completo do usuário;</li>
 *     <li>{@code email}: o endereço de e-mail do usuário (único);</li>
 *     <li>{@code senha}: a senha de acesso do usuário.</li>
 * </ul>
 * </p>
 *
 * <p>O métodoo {@code equals} e {@code hashCode} consideram apenas o campo {@code id}.</p>
 *
 * @author Jorge Roberto
 */

@Entity
@Table(name = "usuarios")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Usuario extends PersistenceEntity implements Serializable {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

}
