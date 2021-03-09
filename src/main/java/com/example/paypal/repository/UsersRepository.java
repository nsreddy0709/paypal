package com.example.paypal.repository;


import com.example.paypal.model.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Integer> {

//    Users findByEmail(String email);
//
//    Users findByPassword(String password);
//    Users findUsersByEmail(String email);
//
//    Users findUsersByEmailAndPassword(String email,String password);
      Users findUsersByEmail(String email);
}
