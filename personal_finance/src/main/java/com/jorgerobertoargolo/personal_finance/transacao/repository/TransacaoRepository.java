package com.jorgerobertoargolo.personal_finance.transacao.repository;

import com.jorgerobertoargolo.personal_finance.transacao.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório responsável pelo acesso aos dados de {@link Transacao}.
 * Fornece operações de CRUD e consultas personalizadas.
 *
 * @author Jorge Roberto
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByUsuarioId(Long id);
}
