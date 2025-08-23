package com.jorgerobertoargolo.personal_finance.transacao.service;

import com.jorgerobertoargolo.personal_finance.transacao.dto.TransacaoResponseDto;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;

import java.util.List;

public interface TransacaoIService {
    TransacaoResponseDto save(Transacao transacao);
    TransacaoResponseDto update(Transacao transacao);
    void delete(Long id);
    List<TransacaoResponseDto> getTransactionsByAuthenticated(Long id);
    Transacao findById(Long id);
}
