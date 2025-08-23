package com.jorgerobertoargolo.personal_finance.usuario.controller;

import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.PerfilDeUsuario;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.enums.NivelAcesso;
import com.jorgerobertoargolo.personal_finance.person.entity.Pessoa;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioPostRequestDTO;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciamento de usuários.
 * <p>
 * Disponibiliza endpoints para recuperação da lista de usuários e para cadastro de um novo usuário.
 * A classe utiliza {@link UsuarioIService} para lógica de negócio e {@link ObjectMapperUtil} para conversão
 * entre DTOs e entidades.
 * </p>
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping(path = "/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    /**
     * Recupera a lista completa de usuários cadastrados.
     *
     * @return {@link ResponseEntity} contendo a lista de usuários convertida para
     *         {@link UsuarioGetResponseDTO} e status HTTP 200 (OK).
     */
    @GetMapping(path = "/findall",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.usuarioService.findAll(),
                        UsuarioGetResponseDTO.class
                ));
    }

    /**
     * Salva um novo usuário com base nos dados fornecidos no corpo da requisição.
     * <p>
     * O objeto recebido é convertido em uma entidade {@link Usuario}, persistido no banco,
     * e o resultado é convertido em um {@link UsuarioGetResponseDTO} para retorno seguro.
     * </p>
     *
     * @param usuarioPostRequestDto Objeto DTO contendo os dados do usuário a ser salvo.
     * @return {@link ResponseEntity} contendo o usuário salvo convertido em {@link UsuarioGetResponseDTO}
     *         e status HTTP 201 (Created).
     */
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioGetResponseDTO> save(@RequestBody @Valid UsuarioPostRequestDTO usuarioPostRequestDto) {

        PerfilDeUsuario perfil = new PerfilDeUsuario();
        perfil.setAcesso(NivelAcesso.COMUM);
        perfil.setDescricao(usuarioPostRequestDto.getPerfil().getDescricao());

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(usuarioPostRequestDto.getNome());

        Usuario usuario = objectMapperUtil.map(usuarioPostRequestDto, Usuario.class);
        usuario.setPessoa(pessoa);
        usuario.setPerfil(perfil);

        UsuarioGetResponseDTO dto = usuarioService.save((usuario));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    /**
     * Exclui um usuário com base no ID fornecido na URL.
     *
     * @param id Identificador único do usuário a ser excluído.
     * @return {@link ResponseEntity} com status HTTP 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza os dados de um usuário com base nas informações fornecidas no corpo da requisição.
     * <p>
     * O objeto recebido é convertido para a entidade {@link Usuario} e enviado ao serviço de atualização.
     * </p>
     *
     * @param usuarioPostRequestDto Objeto DTO contendo os dados atualizados do usuário.
     * @return {@link ResponseEntity} com status HTTP 204 (No Content) em caso de sucesso.
     */
    //TODO: Corrigir esse, pois está dando erro
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update (@RequestBody @Valid UsuarioPostRequestDTO usuarioPostRequestDto) {
        Usuario usuario = objectMapperUtil.map(usuarioPostRequestDto, Usuario.class);
        usuario.setId(usuarioPostRequestDto.getId());
        usuarioService.update(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Recupera um usuário pelo email fornecido como parâmetro de consulta.
     *
     * @param email O email do usuário a ser buscado.
     * @return {@link ResponseEntity} contendo o usuário convertido para {@link UsuarioGetResponseDTO}
     *         e status HTTP 200 (OK). Caso o usuário não seja encontrado, a exceção do service
     *         será propagada (ou você pode tratar para retornar 404).
     */
    @GetMapping(path = "/findbyemail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioGetResponseDTO> findByEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class));
    }
}
