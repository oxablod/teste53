package com.teste.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teste.dto.PedidosDTOListWrapper;
import com.teste.model.PedidosModel;
import com.teste.repository.PedidosRepository;
import com.teste.repository.Repository;
import com.teste.repository.specification.criteria.GenericFilterCriteria;
import com.teste.service.PedidosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidosServiceImpl extends CrudServiceImpl<PedidosModel, Long> implements PedidosService {

    private final PedidosRepository pedidosRepository;
    private final ObjectMapper xmlMapper = new XmlMapper();


    public PedidosServiceImpl(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    @Override
    protected Repository<PedidosModel, Long> getRepository() {
        return this.pedidosRepository;
    }

    @Override
    protected Long getId(PedidosModel entity) {
        return entity.getId();
    }

    public ResponseEntity<String> upload(List<MultipartFile> files) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (isValidFileExtension(fileName)) {
                byte[] fileBytes = file.getBytes();
                try {
                    List<PedidosModel> pedidosList = new ArrayList<>();
                    if (fileName.toLowerCase().endsWith(".json")) {
                        pedidosList = objectMapper.readValue(fileBytes, objectMapper.getTypeFactory().constructCollectionType(List.class, PedidosModel.class));
                    } else if (fileName.toLowerCase().endsWith(".xml")) {
                        xmlMapper.registerModule(new JavaTimeModule());
                        PedidosDTOListWrapper pedidosDTOListWrapper = xmlMapper.readValue(fileBytes, PedidosDTOListWrapper.class);
                        pedidosList = pedidosDTOListWrapper.getElements();
                    } else {
                        return new ResponseEntity<>("Tipo de arquivo não suportado: " + fileName, HttpStatus.BAD_REQUEST);
                    }
                    Iterator<PedidosModel> iterator = pedidosList.iterator();
                    while (iterator.hasNext()) {
                        PedidosModel pedido = iterator.next();
                        // Verifica se o número de controle já existe no repositório
                        Optional<PedidosModel> pedidoExistente = pedidosRepository.findByNrControle(pedido.getNrControle());
                        // Se o pedido existir, remove-o da lista
                        pedidoExistente.ifPresent(existingPedido -> iterator.remove());
                    }
                    pedidosList.forEach(PedidosModel::validaInformacoes);
                    if(pedidosList.isEmpty()){
                        return new ResponseEntity<>("Nenhum pedido importado todos já estão registrados: " + fileName, HttpStatus.BAD_REQUEST);
                    }
                    pedidosRepository.saveAll(pedidosList);
                }catch (IOException e) {
                    return new ResponseEntity<>("Erro ao desserializar o JSON do arquivo: " + fileName, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else {
                return new ResponseEntity<>("Tipo de arquivo não suportado: " + fileName, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Arquivos carregados com sucesso!", HttpStatus.OK);
    }
    private boolean isValidFileExtension(String fileName) {
        String[] allowedExtensions = {".xml", ".json"};
        for (String extension : allowedExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
