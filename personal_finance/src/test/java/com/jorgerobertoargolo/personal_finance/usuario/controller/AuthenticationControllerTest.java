package com.jorgerobertoargolo.personal_finance.usuario.controller;

import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.PerfilDeUsuario;
import com.jorgerobertoargolo.personal_finance.perfil_de_usuario.entity.enums.NivelAcesso;
import com.jorgerobertoargolo.personal_finance.person.entity.Pessoa;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioLoginDTO;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.repository.UsuarioRepository;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioIService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthenticationControllerTest {

    private Usuario userGlobal;
    private UsuarioLoginDTO loginDtoWithValidCredentials;
    private UsuarioLoginDTO loginDtoWithInvalidEmail;

    @Autowired
    private AuthenticationController controller;

    @Autowired
    private UsuarioIService usuarioService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
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

        loginDtoWithValidCredentials = new UsuarioLoginDTO(userGlobal.getEmail(), userGlobal.getSenha());
        loginDtoWithInvalidEmail = new UsuarioLoginDTO("abc@gmail.com", "123456");
    }

    @Test
    @DisplayName("AUTH: Credenciais válidas retornam status 200 e DTO do usuário")
    void authenticate_WithValidCredentials_ReturnsUserDTO() {
        ResponseEntity<?> response = controller.authenticate(loginDtoWithValidCredentials);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Usuario expectedUser = usuarioService.findByEmail(userGlobal.getEmail());
        UsuarioGetResponseDTO expectedDto = objectMapperUtil.map(expectedUser, UsuarioGetResponseDTO.class);

        Assertions.assertEquals(expectedDto, response.getBody());
    }

    @Test
    @DisplayName("AUTH: E-mail inexistente deve retornar status 401 (Unauthorized)")
    void authenticate_WithInvalidEmail_ReturnsUnauthorized() {
        ResponseEntity<?> response = controller.authenticate(loginDtoWithInvalidEmail);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Credenciais incorretas", response.getBody());
    }

    @Test
    @DisplayName("❌ AUTH: Senha incorreta deve retornar status 401 (Unauthorized)")
    void authenticate_WithInvalidPassword_ReturnsUnauthorized() {
        UsuarioLoginDTO dto = new UsuarioLoginDTO(userGlobal.getEmail(), "senha_errada_404");
        ResponseEntity<?> response = controller.authenticate(dto);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assertions.assertEquals("Credenciais incorretas", response.getBody());
    }
}