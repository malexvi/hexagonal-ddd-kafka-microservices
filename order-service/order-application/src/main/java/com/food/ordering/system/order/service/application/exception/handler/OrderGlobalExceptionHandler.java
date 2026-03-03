package com.food.ordering.system.order.service.application.exception.handler;

import com.food.ordering.system.application.handler.ErrorDTO;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderDomainNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class OrderGlobalExceptionHandler {

    @ExceptionHandler(value = OrderDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(OrderDomainException orderDomainException){
        log.error(orderDomainException.getMessage(), orderDomainException);
        return ErrorDTO.builder()
                .code(orderDomainException.getClass().getSimpleName())
                .message(orderDomainException.getMessage())
                .build();
    }

    @ExceptionHandler(value = OrderDomainNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(OrderDomainNotFoundException orderDomainNotFoundException){
        log.error(orderDomainNotFoundException.getMessage(), orderDomainNotFoundException);
        return ErrorDTO.builder()
                .code(orderDomainNotFoundException.getClass().getSimpleName())
                .message(orderDomainNotFoundException.getMessage())
                .build();
    }
}
