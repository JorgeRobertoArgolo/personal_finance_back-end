package com.jorgerobertoargolo.personal_finance.usuario.controller;

import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioLoginDTO;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthenticationController {

    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UsuarioLoginDTO dto) {
        Usuario usuario = usuarioService.findByEmail(dto.getEmail());
        if (usuario != null && usuario.getSenha().equals(dto.getSenha())) {
            return  ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class));
        }

        log.error("Credenciais incorretas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais incorretas");
    }
}
