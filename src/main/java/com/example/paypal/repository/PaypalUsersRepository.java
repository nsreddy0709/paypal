package com.example.paypal.repository;

import com.example.paypal.model.PaypalUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PaypalUsersRepository extends JpaRepository<PaypalUsers,Integer> {
    PaypalUsers findPaypalUsersByNumber(String uname);
}
