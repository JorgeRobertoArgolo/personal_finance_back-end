package com.jorgerobertoargolo.personal_finance.categoria_transacao.repository;

import com.jorgerobertoargolo.personal_finance.categoria_transacao.entity.CategoriaTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaTransacaoRepository extends JpaRepository<CategoriaTransacao, Long> {
    CategoriaTransacao findByNomeIgnoreCase(String nome);
}
