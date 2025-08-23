package com.jorgerobertoargolo.personal_finance.transacao.service;

import com.jorgerobertoargolo.personal_finance.transacao.dto.TransacaoResponseDto;
import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;

import java.util.List;

/**
 * Interface que define os serviços relacionados a {@link Transacao}.
 * Deve ser implementada pela camada de serviço.
 *
 * @author Jorge Roberto
 */
public interface TransacaoIService {

    /**
     * Salva uma nova transação.
     *
     * @param transacao Entidade da transação.
     * @return DTO da transação salva.
     */
    TransacaoResponseDto save(Transacao transacao);

    /**
     * Atualiza uma transação existente.
     *
     * @param transacao Entidade com dados atualizados.
     * @return DTO da transação atualizada.
     */
    TransacaoResponseDto update(Transacao transacao);

    /**
     * Deleta uma transação pelo ID.
     *
     * @param id Identificador da transação.
     */
    void delete(Long id);

    /**
     * Retorna todas as transações de um usuário autenticado.
     *
     * @param id ID do usuário.
     * @return Lista de transações.
     */
    List<TransacaoResponseDto> getTransactionsByAuthenticated(Long id);

    /**
     * Busca uma transação pelo ID.
     *
     * @param id Identificador da transação.
     * @return Entidade da transação encontrada.
     */
    Transacao findById(Long id);
}
