package com.teste.repository.specification;

import com.teste.repository.specification.criteria.GenericFilterCriteria;
import com.teste.utils.date.DateTimeUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class GenericSpecification<T> implements Specification<T> {

    private final GenericFilterCriteria criteria;

    public GenericSpecification(GenericFilterCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.getFilters().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                Path<?> path = getPathFromKey(root, key);

                // If path is null, the attribute does not exist
                if (path != null) {
                    Predicate predicate = getPredicateForPath(path, value, criteriaBuilder);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Path<?> getPathFromKey(Root<T> root, String key) {
        String[] parts = key.split("\\.");
        From<?, ?> fromPath = root;
        for (int i = 0; i < parts.length - 1; i++) {
            if (!entityHasAttribute(fromPath, parts[i])) {
                return null;
            }
            fromPath = fromPath.join(parts[i], JoinType.INNER);
        }
        if (!entityHasAttribute(fromPath, parts[parts.length-1])) {
            return null;
        }
        return fromPath.get(parts[parts.length - 1]);
    }

    private Predicate getPredicateForPath(Path<?> path, Object value, CriteriaBuilder criteriaBuilder) {
        Class<?> attributeType = path.getJavaType();

        if (attributeType.equals(String.class)) {
            return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
        } else if (isWrapperType(attributeType)) {
            if (attributeType.equals(LocalDateTime.class)) {
                return criteriaBuilder.equal(path, DateTimeUtils.toLocalDateTimeISO(value.toString()));
            } else if (value instanceof String && ((String) value).contains(",")) {
                return path.in(Arrays.stream(((String) value).split(","))
                        .collect(Collectors.toList()));
            } else if (attributeType.isEnum()) {
                Optional<?> enumValue = Arrays.stream(attributeType.getEnumConstants())
                        .filter(p -> p.toString().equals(value))
                        .findFirst();
                if (enumValue.isPresent()) {
                    return criteriaBuilder.equal(path, enumValue.get());
                }
            } else {
                return criteriaBuilder.equal(path, value);
            }
        }
        return null;
    }

    private boolean entityHasAttribute(From<?, ?> from, String attributeName) {
        try {
            from.get(attributeName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isWrapperType(Class<?> clazz) {
        return clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Boolean.class) ||
                clazz.equals(String.class) ||
                clazz.equals(Date.class) ||
                clazz.equals(LocalDate.class) ||
                clazz.equals(LocalDateTime.class) ||
                clazz.equals(LocalTime.class) ||
                clazz.isEnum();
    }
}
