package com.jorgerobertoargolo.personal_finance.categoria_transacao.repository;

import com.jorgerobertoargolo.personal_finance.categoria_transacao.entity.CategoriaTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável pelo acesso aos dados de {@link CategoriaTransacao}.
 * Extende {@link JpaRepository} para fornecer operações de CRUD.
 *
 * @author Jorge Roberto
 */
public interface CategoriaTransacaoRepository extends JpaRepository<CategoriaTransacao, Long> {
    CategoriaTransacao findByNomeIgnoreCase(String nome);
}
