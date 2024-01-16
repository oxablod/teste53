package com.teste.controller;

import com.teste.model.BaseEntity;
import com.teste.repository.specification.criteria.GenericFilterCriteria;
import com.teste.service.CrudService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
public abstract class CrudController<T extends BaseEntity<ID>, D, ID extends Serializable> {

    protected abstract CrudService<T, ID> getService();
    protected abstract ModelMapper getModelMapper();
    private final Class<T> typeClass;
    private final Class<D> typeDtoClass;

    protected CrudController(Class<T> typeClass, Class<D> typeDtoClass) {
        this.typeClass = typeClass;
        this.typeDtoClass = typeDtoClass;
    }

    protected Class<T> getTypeClass() {
        return this.typeClass;
    }

    @PostMapping("batch")
    public ResponseEntity<List<D>> saveAll(@RequestBody @Valid @NotEmpty List<D> entities) {
        List<D> savedItens = new ArrayList<>();

        getService().save(entities.stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList()))
                .forEach(d -> savedItens.add(convertToDto(d)));

        return ResponseEntity.ok()
                .body(savedItens);
    }

    @GetMapping("v2/filter/page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<D>> filter(@Parameter(in = ParameterIn.QUERY, description = "Número da página da lista de resultados a serem exibidos. O valor padrão é 0.")
                                          @RequestParam @Valid @Min(0) int page,
                                          @Parameter(in = ParameterIn.QUERY, description = "Tamanho da página da lista de resultados a serem exibidos. O valor padrão é 10.")
                                          @RequestParam @Valid @Min(1) int size,
                                          @Parameter(in = ParameterIn.QUERY, description = "Nome da propriedade pela qual os resultados serão ordenados. Se não for fornecido, a ordenação será feita pela propriedade padrão.")
                                          @RequestParam(required = false) String order,
                                          @Parameter(in = ParameterIn.QUERY, description = "Indica se a ordenação será feita em ordem crescente (true) ou decrescente (false). O valor padrão é true.")
                                          @RequestParam(required = false, defaultValue = "true") Boolean asc,
                                          @ModelAttribute GenericFilterCriteria criteria) {

        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }

        return ResponseEntity.ok(getService().filter(pageRequest, criteria).map(this::convertToDto));
    }

    protected D convertToDto(T entity) {
        return getModelMapper().map(entity, this.typeDtoClass);
    }

    protected T convertToEntity(D entityDto) {
        return getModelMapper().map(entityDto, this.typeClass);
    }

}