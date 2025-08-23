package com.jorgerobertoargolo.personal_finance.transacao.service;

import com.jorgerobertoargolo.personal_finance.infrastructure.exception.BusinessException;
import com.jorgerobertoargolo.personal_finance.infrastructure.mapper.ObjectMapperUtil;
import com.jorgerobertoargolo.personal_finance.transacao.dto.TransacaoResponseDto;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import com.jorgerobertoargolo.personal_finance.transacao.repository.TransacaoRepository;
import com.jorgerobertoargolo.personal_finance.usuario.dto.UsuarioGetResponseDTO;
import com.jorgerobertoargolo.personal_finance.usuario.service.UsuarioIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransacaoService implements TransacaoIService {

    private final TransacaoRepository transacaoRepository;

    private final UsuarioIService usuarioService;

    private final ObjectMapperUtil  objectMapperUtil;

    @Override @Transactional
    public TransacaoResponseDto save(Transacao transacao) {
        try {
            transacaoRepository.save(transacao);
            return objectMapperUtil.map(transacao, TransacaoResponseDto.class);
        } catch (Exception e) {
            throw new BusinessException("Error ao salvar transacao");
        }
    }

    @Override @Transactional
    public TransacaoResponseDto update(Transacao transacao) {
        if (!transacaoRepository.existsById(transacao.getId())) {
            throw new BusinessException(
                    String.format("Transação com id={%d} não encontrada",  transacao.getId())
            );
        }
        transacaoRepository.save(transacao);
        return objectMapperUtil.map(transacao, TransacaoResponseDto.class);
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!transacaoRepository.existsById(id)) {
            throw new BusinessException(
                    String.format("Transação com id={%d} não encontrada",  id)
            );
        }
        transacaoRepository.deleteById(this.findById(id).getId());
    }

    @Override @Transactional(readOnly = true)
    public List<TransacaoResponseDto> getTransactionsByAuthenticated(Long id) {

        UsuarioGetResponseDTO usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new BusinessException("Usuário inexistente");
        }

        List<Transacao> transacoes = transacaoRepository.findAllByUsuarioId(usuario.getId());

        return objectMapperUtil.mapAll(transacoes, TransacaoResponseDto.class);
    }

    @Override @Transactional(readOnly = true)
    public Transacao findById(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(
                        () -> new BusinessException(
                                String.format("Transação com id={%d} não encontrada",  id)
                        )
                );
    }
}
