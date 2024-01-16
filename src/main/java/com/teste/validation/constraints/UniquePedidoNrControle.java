package com.teste.validation.constraints;

import com.teste.validation.constraintsvalidation.UniquePedidoNrControleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniquePedidoNrControleValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePedidoNrControle {
    String message() default "{pedido.nrControle}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
