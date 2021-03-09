package com.example.paypal.repository;



import com.example.paypal.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    ArrayList<Orders> findOrdersByUid(Integer id);
}
