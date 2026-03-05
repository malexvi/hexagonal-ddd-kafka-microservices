package com.food.ordering.system.order.service.dataaccess.order.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntityId implements Serializable {// Because identifier class should be able to serialize when persisting the entity

    private Long id;
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntityId that = (OrderItemEntityId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
