package com.teste.service;

import com.teste.model.PedidosModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PedidosService extends CrudService<PedidosModel, Long> {
    ResponseEntity<String> upload(List<MultipartFile> files) throws IOException;
}
