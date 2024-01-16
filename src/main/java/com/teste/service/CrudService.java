package com.teste.service;

import com.teste.repository.specification.criteria.GenericFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface CrudService<T, ID extends Serializable>{

    T saveAndFlush(T entity);

    Iterable<T> save(Iterable<T> iterable);

    void flush();

    T findOne(ID id);

    Page<T> filter(Pageable pageable, GenericFilterCriteria criteria);

}