package com.example.paypal.repository;

import com.example.paypal.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


@Transactional
public interface FlagRepository extends JpaRepository<Flag,Integer> {



}
