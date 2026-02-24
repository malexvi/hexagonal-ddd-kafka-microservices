package com.food.ordering.system.order.service.domain.helper;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TestHelper {

    private TestHelper() {
    }

    public static CreateOrderCommand.CreateOrderCommandBuilder createBaseOrderCommandBuilder(
            UUID customerId, UUID restaurantId, UUID productId, BigDecimal price
    ) {
        return CreateOrderCommand.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .address(createDefaultAddress())
                .price(price)
                .items(List.of(
                        createOrderItem(productId, 1, "50.00", "50.00"),
                        createOrderItem(productId, 3, "50.00", "150.00")
                ));
    }

    public static OrderAddress createDefaultAddress() {
        return OrderAddress.builder()
                .street("street_1")
                .postalCode("1000AB")
                .city("Mongolia")
                .build();
    }

    public static OrderItem createOrderItem(UUID productId, int quantity, String price, String subTotal) {
        return OrderItem.builder()
                .productId(productId)
                .quantity(quantity)
                .price(new BigDecimal(price))
                .subTotal(new BigDecimal(subTotal))
                .build();
    }
}
