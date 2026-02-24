package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.helper.TestHelper;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

/*    @MockitoBean
    private RestaurantRepository restaurantRepository;*/

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("d123f3f1-1234-3df4-45g6-43fd632cfb23");
    private final UUID RESTAURANT_ID = UUID.fromString("d123f3f2-1234-3df4-45g6-43fd632cfb23");
    private final UUID PRODUCT_ID = UUID.fromString("d123f3f3-1234-3df4-45g6-43fd632cfb23");
    private final UUID ORDER_ID = UUID.fromString("d123f3f4-1234-3df4-45g6-43fd632cfb23");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    public void init() {
        // 1. Success command
        createOrderCommand = TestHelper
                .createBaseOrderCommandBuilder(CUSTOMER_ID, RESTAURANT_ID, PRODUCT_ID, PRICE)
                .build();

        // 2. Wrong price command
        createOrderCommandWrongPrice = TestHelper
                .createBaseOrderCommandBuilder(CUSTOMER_ID, RESTAURANT_ID, PRODUCT_ID, PRICE)
                .price(new BigDecimal("250.00")) // Wron g Price
                .build();

        // 3. Wrong PRODUCT PRICE
        createOrderCommandWrongProductPrice = TestHelper
                .createBaseOrderCommandBuilder(CUSTOMER_ID, RESTAURANT_ID, PRODUCT_ID, new BigDecimal("210.00"))
                .items(List.of( // (We override one the item list and price, now it don't match)
                        TestHelper.createOrderItem(PRODUCT_ID, 1, "60.00", "60.00"),
                        TestHelper.createOrderItem(PRODUCT_ID, 3, "50.00", "150.00")
                ))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        /*restaurantResponse*/

        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .productList(
                        List.of(
                                new Product(
                                        new ProductId(PRODUCT_ID),
                                        "product-1",
                                        new Money(new BigDecimal("50.00"))
                                ),
                                new Product(
                                        new ProductId(PRODUCT_ID),
                                        "product-2",
                                        new Money(new BigDecimal("50.00"))
                                )
                        )
                )
                .active(true)
                .build();

        /*Order*/

        Order order = orderDataMapper.createOrderCommandToOder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        /*when*/
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of((customer)));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }
}
