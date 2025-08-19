package com.jorgerobertoargolo.personal_finance.usuario.entity;

import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.PerfilDeUsuario;
import com.jorgerobertoargolo.personal_finance.person.entity.Pessoa;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um usuário do sistema.
 *
 * <p>Um usuário é definido por suas credenciais de acesso ({@code email} e {@code senha})
 * e obrigatoriamente possui:</p>
 *
 * <ul>
 *   <li>Um {@link PerfilDeUsuario}, que define suas permissões e nível de acesso.</li>
 *   <li>Uma {@link Pessoa}, que armazena seus dados pessoais.</li>
 *   <li>Uma lista de {@link Transacao}, que registra suas movimentações financeiras.</li>
 * </ul>
 *
 * <p>Herda de {@link PersistenceEntity}, que fornece um identificador único
 * gerado automaticamente.</p>
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

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "perfil_id", nullable = false, unique = true)
    private PerfilDeUsuario perfil;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "pessoa_id", nullable = false,  unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();
}
