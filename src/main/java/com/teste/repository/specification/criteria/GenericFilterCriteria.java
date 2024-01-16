package com.teste.repository.specification.criteria;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class GenericFilterCriteria implements Serializable {
    private Map<String, Object> filters = new HashMap<>();
}
