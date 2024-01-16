package com.teste.repository;

import com.teste.model.PedidosModel;

import java.util.Optional;

public interface PedidosRepository extends Repository<PedidosModel, Long>{
    Optional<PedidosModel> findByNrControle(Integer nrControle);
}
