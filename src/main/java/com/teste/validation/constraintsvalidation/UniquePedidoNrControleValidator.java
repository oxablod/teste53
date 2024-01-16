package com.teste.validation.constraintsvalidation;


import com.teste.dto.PedidosDTO;
import com.teste.model.PedidosModel;
import com.teste.repository.PedidosRepository;
import com.teste.validation.constraints.UniquePedidoNrControle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


public class UniquePedidoNrControleValidator implements ConstraintValidator<UniquePedidoNrControle, PedidosDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniquePedidoNrControleValidator.class);

    private final PedidosRepository pedidosRepository;

    public UniquePedidoNrControleValidator(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    @Override
    public boolean isValid(PedidosDTO pedidosDTO,
                           ConstraintValidatorContext constraintValidatorContext) {
        try {
            Optional<PedidosModel> pedido = pedidosRepository.findByNrControle(pedidosDTO.getNrControle());
            return pedido.map(membroModel -> membroModel.getNrControle().equals(pedidosDTO.getNrControle())).orElse(true);
        } catch (IncorrectResultSizeDataAccessException e) {
            LOGGER.error("Erro número de controle único: {}: {}", e.getClass().getCanonicalName(), e.getMessage());
            return false;
        }
    }
}