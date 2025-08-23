package com.jorgerobertoargolo.personal_finance.transacao.repository;

import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByUsuarioId(Long id);
}
