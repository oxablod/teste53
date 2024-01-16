package com.teste.controller;

import com.teste.dto.PedidosDTO;
import com.teste.model.PedidosModel;
import com.teste.service.CrudService;
import com.teste.service.PedidosService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("pedidos")
public class PedidosController  extends CrudController<PedidosModel, PedidosDTO, Long> {

    private final PedidosService pedidosService;
    private final ModelMapper modelMapper;

    public PedidosController(PedidosService pedidosService, ModelMapper modelMapper) {
        super(PedidosModel.class, PedidosDTO.class);
        this.pedidosService = pedidosService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("files") List<MultipartFile> files) throws IOException {
        return pedidosService.upload(files);
    }

    @Override
    protected CrudService<PedidosModel, Long> getService() {
        return this.pedidosService ;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }

}
