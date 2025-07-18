package com.jorgerobertoargolo.personal_finance.usuario.service;

import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócios relacionada à entidade {@link Usuario}.
 * <p>
 * Esta classe fornece métodos para operações CRUD sobre usuários,
 * como busca, persistência, atualização e remoção.
 * </p>
 *
 * @author Jorge Roberto
 */
@RequiredArgsConstructor
@Service
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Retorna uma lista com todos os usuários cadastrados.
     *
     * @return lista de usuários
     */
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id identificador do usuário
     * @return o usuário correspondente
     * @throws RuntimeException se o usuário não for encontrado
     */
    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Usuário com id={%d} não foi encontrado.", id))
        );
    }

    /**
     * Salva um novo usuário no banco de dados.
     *
     * @param usuario objeto do tipo {@link Usuario}
     * @return o usuário salvo
     * @throws RuntimeException se ocorrer erro durante a persistência (ex: e-mail duplicado)
     */
    @Override
    public Usuario save(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário. Possivelmente o e-mail já está cadastrado.");
        }
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario objeto com os dados atualizados
     * @return o usuário atualizado
     * @throws RuntimeException se o usuário não existir
     */
    @Override
    public Usuario update(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException(String.format(
                    "Usuário com id={%d} não encontrado.", usuario.getId()));
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Remove um usuário do banco de dados.
     *
     * @param usuario usuário a ser removido
     * @throws RuntimeException se o usuário não existir
     */
    @Override
    public void delete(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException(String.format(
                    "Usuário com id={%d} não encontrado.", usuario.getId()));
        }
        usuarioRepository.delete(usuario);
    }

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email e-mail do usuário
     * @return o usuário correspondente
     * @throws RuntimeException se o usuário não for encontrado
     */
    @Override
    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException(String.format(
                    "Usuário com e-mail {%s} não encontrado.", email));
        }
        return usuario;
    }
}
