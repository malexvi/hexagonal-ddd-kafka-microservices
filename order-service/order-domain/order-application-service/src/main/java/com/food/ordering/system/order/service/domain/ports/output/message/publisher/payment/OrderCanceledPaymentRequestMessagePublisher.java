package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;

public interface OrderCanceledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {

}
