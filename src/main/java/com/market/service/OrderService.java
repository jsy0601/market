package com.market.service;

import com.market.domain.*;
import com.market.dto.OrderDto;
import com.market.dto.OrderInfoDto;
import com.market.dto.OrderItemDto;
import com.market.repository.ItemImgRepository;
import com.market.repository.ItemRepository;
import com.market.repository.OrderRepository;
import com.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    //주문
    public Long order(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Orders orders = Orders.createOrder(user, orderItemList);
        orderRepository.save(orders);

        return orders.getId();
    }

    //주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderInfoDto> getOrderList(String email, Pageable pageable) {
        List<Orders> orders = orderRepository.findOrders(email, pageable); //주문 목록 조회
        Long totalCount = orderRepository.countOrder(email); //주문 총 개수 조회
        log.info("정서연 {} ", orders.get(0).getOrderDate());

        List<OrderInfoDto> orderInfoDtos = new ArrayList<>();

        for (Orders order : orders) {
            OrderInfoDto orderInfoDto = new OrderInfoDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImage itemImage = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImage.getImgUrl());
                orderInfoDto.addOrderItemDto(orderItemDto);
            }
            orderInfoDtos.add(orderInfoDto);
        }
        return new PageImpl<OrderInfoDto>(orderInfoDtos, pageable, totalCount);
    }

    //    주문 취소
    public void cancelOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        orders.cancelOrder();
    }
}

