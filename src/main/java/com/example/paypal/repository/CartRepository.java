package com.example.paypal.repository;

import com.example.paypal.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CartRepository extends JpaRepository<Cart,Integer> {

    void deleteCartByUid(Integer id);
}
