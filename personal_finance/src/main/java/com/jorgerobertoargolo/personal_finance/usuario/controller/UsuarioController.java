package com.jorgerobertoargolo.personal_finance.usuario.controller;

import com.jorgerobertoargolo.personal_finance.infrastructure.util.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UserResponseDto;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioPostRequestDto;
import com.jorgerobertoargolo.personal_finance.usuario.entity.Usuario;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findall",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        this.usuarioService.findAll(),
                        UserResponseDto.class
                ));
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody UsuarioPostRequestDto usuarioPostRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.save(
                        (objectMapperUtil.map(usuarioPostRequestDto, Usuario.class))
                ));
    }
}
