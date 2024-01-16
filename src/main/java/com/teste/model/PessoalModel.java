package com.teste.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pessoa")
@EqualsAndHashCode(of = "id")
public class PessoalModel implements Serializable, BaseEntity<Long> {

    @Id
    @Column(name = "id_pessoa")
    private Long id;

    @Column(name = "nome_pessoa")
    private String nomePessoa;

}
