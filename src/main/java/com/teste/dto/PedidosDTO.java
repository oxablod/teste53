package com.teste.dto;

import com.teste.validation.constraints.UniquePedidoNrControle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@UniquePedidoNrControle
public class PedidosDTO  implements Serializable {
    private Long id;
    private LocalDateTime dtCadastro;
    private String nomeProduto;
    private BigDecimal vlProduto;
    private Integer quantidade;
    private Integer codigoCliente;
    @NotNull
    private Integer nrControle;
    private BigDecimal vlTotal;
}
