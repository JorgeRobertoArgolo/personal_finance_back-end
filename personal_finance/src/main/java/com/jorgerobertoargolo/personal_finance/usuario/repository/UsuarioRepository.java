package com.jorgerobertoargolo.personal_finance.usuario.repository;

import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositório responsável pelas operações de persistência da entidade {@link Usuario}.
 *
 * <p>Estende {@link JpaRepository} para fornecer operações básicas como salvar, deletar,
 * buscar por ID e listar todos os registros.</p>
 *
 * <p>Inclui também métodoo específico para buscar um usuário pelo e-mail.</p>
 *
 * @author Jorge Roberto
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /**
     * Busca um usuário pelo endereço de e-mail.
     *
     * @param email o e-mail do usuário
     * @return o usuário correspondente ou {@code null} se não encontrado
     */
    Usuario findByEmail(String email);
}
