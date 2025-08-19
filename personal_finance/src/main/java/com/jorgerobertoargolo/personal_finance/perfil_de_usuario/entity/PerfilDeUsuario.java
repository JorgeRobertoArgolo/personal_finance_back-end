package com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity;

import com.jorgerobertoargolo.personal_finance.infrastructure.entity.PersistenceEntity;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.enums.NivelAcesso;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Representa o perfil de um usuário no sistema.
 *
 * <p>O perfil é responsável por definir o {@link NivelAcesso} e as permissões
 * que um usuário pode exercer dentro da aplicação.</p>
 *
 * <p>Cada usuário obrigatoriamente possui um perfil vinculado,
 * o qual é criado no momento do cadastro do usuário.</p>
 *
 * <p>Herda de {@link PersistenceEntity}, que fornece um identificador único
 * gerado automaticamente.</p>
 *
 * @author Jorge Roberto
 */

@Entity
@Table(name = "perfis_usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PerfilDeUsuario extends PersistenceEntity implements Serializable {

    @Column(name = "acesso",  nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelAcesso acesso;

    @Column(name = "descricao", length = 255)
    private String descricao;
}
