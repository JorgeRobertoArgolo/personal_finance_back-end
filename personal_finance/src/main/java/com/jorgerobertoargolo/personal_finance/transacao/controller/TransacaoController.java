package com.jorgerobertoargolo.personal_finance.transacao.controller;

import com.jorgerobertoargolo.personal_finance.categoria_transacao.entity.CategoriaTransacao;
import com.jorgerobertoargolo.personal_finance.categoria_transacao.repository.CategoriaTransacaoRepository;
import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.transacao.dto.TransacaoPostRequestDto;
import com.jorgerobertoargolo.personal_finance.transacao.dto.TransacaoResponseDto;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import com.jorgerobertoargolo.personal_finance.transacao.service.TransacaoService;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerenciar as transações.
 * Fornece endpoints para salvar, atualizar, deletar e buscar transações.
 *
 * @author Jorge Roberto
 */
@RestController
@RequestMapping(path = "/api/transacao")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TransacaoController {
    private final TransacaoService transacaoService;
    private final ObjectMapperUtil  objectMapperUtil;
    private final UsuarioService usuarioService;
    private final CategoriaTransacaoRepository categoriaTransacaoRepository;

    /**
     * Salva uma nova transação.
     *
     * @param body DTO contendo os dados da transação.
     * @return Transação criada ou erro caso não seja possível salvar.
     */
    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransacaoResponseDto> save(@RequestBody @Valid TransacaoPostRequestDto body) {
        Transacao transacao = objectMapperUtil.map(body, Transacao.class);

        // Garante que o usuário existe
        transacao.setUsuario(usuarioService.findByEmail(body.getEmailUsuario()));
        if (transacao.getUsuario() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Verifica se a categoria já existe
            CategoriaTransacao categoria = categoriaTransacaoRepository
                    .findByNomeIgnoreCase(body.getCategoria().getNome().toUpperCase());

            if (categoria == null) {
                categoria = new CategoriaTransacao();
                categoria.setNome(body.getCategoria().getNome().toUpperCase());
                categoria = categoriaTransacaoRepository.save(categoria);
            }

            transacao.setCategoria(categoria);

            TransacaoResponseDto responseDto = transacaoService.save(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Deleta uma transação pelo ID.
     *
     * @param id Identificador da transação.
     * @return HTTP 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca todas as transações do usuário autenticado.
     *
     * @param id ID do usuário.
     * @return Lista de transações.
     */
    @GetMapping(path = "/getTransactionsByAuthenticated/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransacaoResponseDto>> getTransactionsByAuthenticated(@PathVariable("id") Long id) {
        List<TransacaoResponseDto> list = transacaoService.getTransactionsByAuthenticated(id);
        return ResponseEntity.ok(list);
    }

    /**
     * Atualiza uma transação existente.
     *
     * @param id ID da transação.
     * @param body DTO contendo os novos dados.
     * @return Transação atualizada.
     */
    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransacaoResponseDto> update(@PathVariable("id") Long id, @RequestBody @Valid TransacaoPostRequestDto body) {
        Transacao transacao = objectMapperUtil.map(body, Transacao.class);

        // Garante que o usuário existe
        transacao.setUsuario(usuarioService.findByEmail(body.getEmailUsuario()));
        if (transacao.getUsuario() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Verifica se a categoria já existe
            CategoriaTransacao categoria = categoriaTransacaoRepository
                    .findByNomeIgnoreCase(body.getCategoria().getNome().toUpperCase());

            if (categoria == null) {
                categoria = new CategoriaTransacao();
                categoria.setNome(body.getCategoria().getNome().toUpperCase());
                categoria = categoriaTransacaoRepository.save(categoria);
            }

            transacao.setCategoria(categoria);

            transacao.setId(id);

            TransacaoResponseDto responseDto = transacaoService.update(transacao);
            return ResponseEntity.ok(responseDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
