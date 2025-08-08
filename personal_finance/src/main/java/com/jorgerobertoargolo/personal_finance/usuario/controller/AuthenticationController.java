package com.jorgerobertoargolo.personal_finance.usuario.controller;

import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDto;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioLoginDto;
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
@RequestMapping("/api")
public class AuthenticationController {

    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UsuarioLoginDto dto) {
        Usuario usuario = usuarioService.findByEmail(dto.getEmail());
        if (usuario != null && usuario.getSenha().equals(dto.getSenha())) {
            return  ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.map(usuario, UsuarioGetResponseDto.class));
        }

        log.error("Credenciais incorretas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais incorretas");
    }
}
