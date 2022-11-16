package com.market;

import com.market.constant.ItemSellStatus;
import com.market.domain.Item;
import com.market.domain.OrderItem;
import com.market.domain.Orders;
import com.market.domain.User;
import com.market.dto.OrderDto;
import com.market.repository.ItemRepository;
import com.market.repository.OrderRepository;
import com.market.repository.UserRepository;
import com.market.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    public Item saveItem() {
        Item item = new Item();
        item.setTitle("테스트테스트");
        item.setPrice(10000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockQuantity(50);
        return itemRepository.save(item);
    }

    public User saveUser() {
        User user = new User();
        user.setEmail("test@test.com");
        return userRepository.save(user);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        User user = saveUser();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, user.getEmail());

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }
}
