package com.jorgerobertoargolo.personal_finance.usuario.service;

import com.jorgerobertoargolo.personal_finance.infrastructure.exception.BusinessException;
import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
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
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Retorna uma lista com todos os usuários cadastrados.
     *
     * @return lista de usuários
     */
    @Transactional(readOnly = true)
    @Override
    public List<UsuarioGetResponseDTO> findAll() {
        return objectMapperUtil.mapAll(usuarioRepository.findAll(),  UsuarioGetResponseDTO.class);
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id identificador do usuário
     * @return o usuário correspondente
     * @throws BusinessException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    @Override
    public UsuarioGetResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new BusinessException(String.format("Usuário com id={%d} não foi encontrado.", id))
        );
        return objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class);
    }

    /**
     * Salva um novo usuário no banco de dados.
     *
     * @param usuario objeto do tipo {@link Usuario}
     * @return o usuário salvo
     * @throws BusinessException se ocorrer erro durante a persistência (ex: e-mail duplicado)
     */
    @Transactional
    @Override
    public UsuarioGetResponseDTO save(Usuario usuario) {
        try {
            return objectMapperUtil.map(usuarioRepository.save(usuario), UsuarioGetResponseDTO.class);
        } catch (Exception e) {
            throw new BusinessException("Erro ao salvar usuário. Possivelmente o e-mail já está cadastrado.");
        }
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario objeto com os dados atualizados
     * @return o usuário atualizado
     * @throws BusinessException se o usuário não existir
     */
    @Transactional
    @Override
    public UsuarioGetResponseDTO update(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new BusinessException(String.format(
                    "Usuário com id={%d} não encontrado.", usuario.getId()));
        }
        return objectMapperUtil.map(usuarioRepository.save(usuario),  UsuarioGetResponseDTO.class);
    }

    /**
     * Remove um usuário do banco de dados.
     *
     * @param id id do usuário a ser removido
     * @throws BusinessException se o usuário não existir
     */
    @Transactional
    @Override
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessException(String.format(
                    "Usuário com id={%d} não encontrado.", id));
        }
        usuarioRepository.delete(usuarioRepository.findById(id).get());
    }

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email e-mail do usuário
     * @return o usuário correspondente
     * @throws BusinessException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    @Override
    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            return null;
        }
        return usuario;
    }
}
