package com.food.ordering.system.order.service.domain.exception;

import com.food.ordering.system.domain.exception.DomainException;

public class OrderDomainNotFoundException extends DomainException {
    public OrderDomainNotFoundException(String message) {
        super(message);
    }

    public OrderDomainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}