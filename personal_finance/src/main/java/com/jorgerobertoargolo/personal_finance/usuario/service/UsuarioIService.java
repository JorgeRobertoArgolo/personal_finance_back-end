package com.jorgerobertoargolo.personal_finance.usuario.service;

import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;

import java.util.List;

/**
 * Interface que define os serviços disponíveis para a entidade {@link Usuario}.
 * Fornece métodos para operações CRUD e consulta por e-mail.
 *
 * @author Jorge Roberto
 */
public interface UsuarioIService {

    /**
     * Retorna todos os usuários cadastrados.
     *
     * @return lista de usuários
     */
    public abstract List<Usuario> findAll();

    /**
     * Busca um usuário pelo ID.
     *
     * @param id o identificador do usuário
     * @return o usuário correspondente
     */
    public abstract Usuario findById(Long id);

    /**
     * Salva um novo usuário.
     *
     * @param usuario o usuário a ser salvo
     * @return o usuário salvo
     */
    public abstract Usuario save(Usuario usuario);

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario o usuário com os dados atualizados
     * @return o usuário atualizado
     */
    public abstract Usuario update(Usuario usuario);

    /**
     * Exclui um usuário.
     *
     * @param id o id do usuário a ser excluído
     */
    public abstract void delete(Long id);

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email o e-mail do usuário
     * @return o usuário correspondente
     */
    public abstract Usuario findByEmail(String email);
}
