package com.teste.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pedidos")
public class PedidosModel implements Serializable, BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_cadastro")
    private LocalDateTime dtCadastro;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "vl_produto")
    private BigDecimal vlProduto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "codigo_cliente")
    private Integer codigoCliente;

    @Column(name = "vl_total")
    private BigDecimal vlTotal;

    @Column(name = "nr_controle")
    private Integer nrControle;

    @JsonCreator
    public PedidosModel(
            @JsonProperty("codigoCliente") Integer codigoCliente,
            @JsonProperty("dtCadastro") LocalDateTime dtCadastro,
            @JsonProperty("nrControle") Integer nrControle,
            @JsonProperty("nomeProduto") String nomeProduto,
            @JsonProperty("quantidade") Integer quantidade,
            @JsonProperty("vlProduto") BigDecimal vlProduto
    ) {
        this.codigoCliente = codigoCliente;
        this.dtCadastro = dtCadastro;
        this.nrControle = nrControle;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.vlProduto = vlProduto;
    }
    public void validaInformacoes() {
        // Verificar se a dtCadastro foi enviada
        this.dtCadastro = (dtCadastro != null) ? dtCadastro : LocalDateTime.now();

        // Verificar e aplicar desconto com base na quantidade
        if (quantidade <= 0) {
            this.quantidade = 1;
        }
        this.vlTotal = vlProduto.multiply(BigDecimal.valueOf(quantidade));
        if (quantidade > 5 && quantidade < 10) {
            this.vlTotal = vlTotal.multiply(BigDecimal.valueOf(0.95)); // Aplicar 5% de desconto
        } else if (quantidade >= 10) {
            this.vlTotal = vlTotal.multiply(BigDecimal.valueOf(0.90)); // Aplicar 10% de desconto
        }else{
            this.vlTotal = vlProduto;
        }
    }

}
