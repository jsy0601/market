package com.market.service;

import com.market.domain.Cart;
import com.market.domain.CartItem;
import com.market.domain.Item;
import com.market.domain.User;
import com.market.dto.CartItemDto;
import com.market.repository.CartItemRepository;
import com.market.repository.CartRepository;
import com.market.repository.ItemRepository;
import com.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        //처음 장바구니에 담을 때 장바구니 엔티티 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        //현재 상품-> 장바구니에 존재 여부
        CartItem existCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (existCartItem != null) { //이미 들어있던 상품
            existCartItem.addCount(cartItemDto.getCount());
            return existCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
