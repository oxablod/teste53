package com.teste.service.impl;


import com.teste.repository.Repository;
import com.teste.repository.specification.GenericSpecification;
import com.teste.repository.specification.criteria.GenericFilterCriteria;
import com.teste.service.CrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class CrudServiceImpl<T, ID extends Serializable>
        implements CrudService<T, ID> {

    protected abstract Repository<T, ID> getRepository();

    protected abstract ID getId(T entity);

    @Override
    @Transactional(readOnly = false)
    public void flush() {
        getRepository().flush();
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(ID id) {
        return getRepository().findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    @Transactional()
    public Iterable<T> save(Iterable<T> iterable) {
        return getRepository().saveAll(iterable);
    }

    @Override
    @Transactional()
    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> filter(Pageable pageable, GenericFilterCriteria criteria) {
        Specification<T> spec = new GenericSpecification<>(criteria);
        return getRepository().findAll(spec, pageable);
    }
    @Transactional
    public T partiallyUpdate(ID id, T updates) {
        Optional<T> existingEntity = getRepository().findById(id);
        return existingEntity.map(entity -> {
            copyNonNullProperties(updates, entity);
            return getRepository().save(entity);
        }).orElse(null);
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }
}