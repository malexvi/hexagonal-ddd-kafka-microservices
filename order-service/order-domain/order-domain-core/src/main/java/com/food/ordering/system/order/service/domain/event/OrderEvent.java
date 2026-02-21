package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.events.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime time;

    public OrderEvent(Order order, ZonedDateTime time) {
        this.order = order;
        this.time = time;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getTime() {
        return time;
    }
}
