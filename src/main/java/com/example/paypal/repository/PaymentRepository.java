package com.example.paypal.repository;

import com.example.paypal.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
