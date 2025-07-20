package com.jorgerobertoargolo.personal_finance.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Classe base para entidades persistentes.
 *
 * <p>Fornece um identificador único gerado automaticamente para as subclasses.
 * Pode ser utilizada como superclasse para outras entidades JPA.</p>
 *
 * @author Jorge Roberto
 */
@MappedSuperclass
public class PersistenceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Getter @Setter private Long id;
}
