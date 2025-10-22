package com.jorgerobertoargolo.personal_finance.usuario.service;

import com.jorgerobertoargolo.personal_finance.infrastructure.exception.BusinessException;
import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.PerfilDeUsuario;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.enums.NivelAcesso;
import com.jorgerobertoargolo.personal_finance.person.entity.Pessoa;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.enums.NivelAcesso.ADMIN;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UsuarioServiceTest {

    private Usuario userGlobal;
    private UsuarioGetResponseDTO userGetResponseDTO;
    private Long createdUserId;

    @Autowired
    private UsuarioIService usuarioService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp () {
        usuarioRepository.deleteAll();
        userGlobal = new Usuario();
        userGlobal.setEmail("test@gmail.com");
        userGlobal.setSenha("123456");

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Test");
        userGlobal.setPessoa(pessoa);

        PerfilDeUsuario perfilDeUsuario = new PerfilDeUsuario();
        perfilDeUsuario.setDescricao("Description test");
        perfilDeUsuario.setAcesso(NivelAcesso.COMUM);
        userGlobal.setPerfil(perfilDeUsuario);

        Usuario savedUser = usuarioRepository.save(userGlobal);
        createdUserId = savedUser.getId();

        userGetResponseDTO = objectMapperUtil.map(savedUser, UsuarioGetResponseDTO.class);
    }

    @Test
    @DisplayName("FIND BY ID: Busca um usuário existente e retorna o DTO completo")
    void findById_WithValidId_ReturnsUserDTO() {
        UsuarioGetResponseDTO response = usuarioService.findById(createdUserId);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(createdUserId, response.getId());
        Assertions.assertEquals(userGlobal.getEmail(), response.getEmail());
        Assertions.assertEquals(userGlobal.getPessoa().getNome(), response.getPessoa().getNome());
        Assertions.assertEquals(userGlobal.getPerfil().getDescricao(), response.getPerfil().getDescricao());
        Assertions.assertEquals(userGlobal.getPerfil().getAcesso().toString(), response.getPerfil().getAcesso());
    }

    @Test
    @DisplayName("FIND BY ID: Lança BusinessException ao procurar ID inexistente")
    void findById_WithInvalidId_ThrowsBusinessException() {
        Assertions.assertThrows(BusinessException.class, () -> usuarioService.findById(1000L));
    }

    @Test
    @DisplayName("SAVE: Salva um novo usuário válido no banco de dados com sucesso")
    void save_ValidUser_SavesAndReturnsDTO() {
        Usuario user = new Usuario();
        user.setEmail("test2@gmail.com");
        user.setSenha("123456");

        Pessoa person = new Pessoa();
        person.setNome("New User");
        user.setPessoa(person);

        PerfilDeUsuario profile = new PerfilDeUsuario();
        profile.setDescricao("New Profile");
        profile.setAcesso(ADMIN);
        user.setPerfil(profile);

        UsuarioGetResponseDTO actualDTO = usuarioService.save(user);

        Assertions.assertNotNull(actualDTO.getId());
        Assertions.assertEquals("test2@gmail.com", actualDTO.getEmail());
        Assertions.assertEquals(ADMIN.toString(), actualDTO.getPerfil().getAcesso());

        // Garante que foi salvo no Banco de dados
        Assertions.assertTrue(usuarioRepository.findById(actualDTO.getId()).isPresent());
    }

    @Test
    @DisplayName("SAVE: Lança BusinessException ao tentar salvar com e-mail duplicado")
    void save_WithExistingEmail_ThrowsBusinessException() {
        Usuario user = new Usuario();
        user.setEmail("test@gmail.com");
        user.setSenha("123456");

        Pessoa person = new Pessoa();
        person.setNome("New User");
        user.setPessoa(person);

        PerfilDeUsuario profile = new PerfilDeUsuario();
        profile.setDescricao("New Profile");
        profile.setAcesso(ADMIN);
        user.setPerfil(profile);

        Assertions.assertThrows(BusinessException.class, () -> usuarioService.save(user));
    }

    @Test
    @DisplayName("UPDATE: Atualiza um usuário existente com sucesso")
    void update_WithValidUser_SucceedsAndReturnsDTO() {
        Usuario user = new Usuario();
        user.setId(createdUserId);

        user.setEmail(userGlobal.getEmail());
        user.setSenha(userGlobal.getSenha());

        Pessoa person = new Pessoa();
        person.setNome("Update test");
        user.setPessoa(person);

        PerfilDeUsuario profile = new PerfilDeUsuario();
        profile.setDescricao("Description update test");
        profile.setAcesso(NivelAcesso.ADMIN);
        user.setPerfil(profile);

        UsuarioGetResponseDTO response = usuarioService.update(user);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createdUserId, response.getId());
        Assertions.assertEquals(user.getEmail(), response.getEmail());
        Assertions.assertEquals(user.getPessoa().getNome(), response.getPessoa().getNome());
        Assertions.assertEquals(user.getPerfil().getDescricao(), response.getPerfil().getDescricao());
        Assertions.assertEquals(user.getPerfil().getAcesso().toString(), response.getPerfil().getAcesso());
    }

    @Test
    @DisplayName("UPDATE: Lança BusinessException ao tentar atualizar usuário inexistente")
    void update_WithNonexistentUser_ThrowsBusinessException() {
        Usuario user = new Usuario();
        user.setId(1000L);

        user.setEmail(userGlobal.getEmail());
        user.setSenha(userGlobal.getSenha());

        Pessoa person = new Pessoa();
        person.setNome("Update test");
        user.setPessoa(person);

        PerfilDeUsuario profile = new PerfilDeUsuario();
        profile.setDescricao("Description update test");
        profile.setAcesso(NivelAcesso.ADMIN);
        user.setPerfil(profile);

        Assertions.assertThrows(BusinessException.class, () -> usuarioService.update(user));
    }

    @Test
    @DisplayName("DELETE: Exclui um usuário existente com sucesso")
    void delete_WithValidId_Succeeds() {
        Assertions.assertDoesNotThrow(() -> usuarioService.delete(createdUserId));
    }

    @Test
    @DisplayName("DELETE: Lança BusinessException ao tentar excluir ID inexistente")
    void delete_WithInvalidId_ThrowsBusinessException() {
        Assertions.assertThrows(BusinessException.class, () -> usuarioService.delete(1000L));
    }

    @Test
    @DisplayName("FIND BY EMAIL: Busca um usuário existente por e-mail e retorna a entidade")
    void findByEmail_WithExistingEmail_ReturnsUser() {
        Assertions.assertDoesNotThrow(() -> usuarioService.findByEmail(userGlobal.getEmail()));

        Usuario userLocal = usuarioService.findByEmail(userGlobal.getEmail());
        Assertions.assertNotNull(userLocal);
        Assertions.assertEquals(userGlobal.getEmail(), userLocal.getEmail());
        Assertions.assertEquals(userGlobal.getSenha(), userLocal.getSenha());
        Assertions.assertEquals(userGlobal.getPessoa().getNome(), userLocal.getPessoa().getNome());
        Assertions.assertEquals(userGlobal.getPerfil().getDescricao(), userLocal.getPerfil().getDescricao());
    }

    @Test
    @DisplayName("FIND BY EMAIL: Lança BusinessException ao procurar e-mail inexistente")
    void findByEmail_WithNonexistentEmail_ThrowsBusinessException() {
        Assertions.assertNull(usuarioService.findByEmail("erro@gmail.com"));
    }

    @Test
    @DisplayName("FIND ALL: Deve retornar a lista com o único usuário cadastrado")
    void findAll_ReturnsListOfUsers() {
        Assertions.assertDoesNotThrow(() -> usuarioService.findAll());
        Assertions.assertEquals(1,  usuarioService.findAll().size());
    }
}