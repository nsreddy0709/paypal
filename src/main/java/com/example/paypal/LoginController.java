package com.example.paypal;

import com.example.paypal.config.JwtTokenUtil;

import com.example.paypal.model.Orders;
import com.example.paypal.model.PaypalUsers;
import com.example.paypal.model.Users;
import com.example.paypal.repository.OrdersRepository;
import com.example.paypal.repository.PaypalUsersRepository;

import com.example.paypal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    PaypalUsersRepository paypalUsersRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @RequestMapping(value = "/")
    public String checkMVC(@RequestParam("jwt") String jwt,Model model) {
        System.out.println(jwt);
        model.addAttribute("jwt", jwt);
        return "login";
    }

    @RequestMapping(value = "/login")
    public String loginHomePage(@RequestParam("uname") String uname,@RequestParam("jwt") String jwt, Model model){
        PaypalUsers u = null;
        try
        {
            u = paypalUsersRepository.findPaypalUsersByNumber(uname);
        }
        catch (Exception e){
            System.out.println("Invalid user");
        }
        if(u!=null){
            model.addAttribute("uname",uname);
            //System.out.println(jwt);
            String name = jwtTokenUtil.extractUsername(jwt);
            Users users = usersRepository.findUsersByEmail(name);
            //System.out.println(users.getUser_id());
            ArrayList<Orders> orders = ordersRepository.findOrdersByUid(users.getUser_id());
            //System.out.println(orders.size());
            int sum = 0;
            for (Orders o:
                    orders) {

                sum = sum + o.getPrice();
                //System.out.println("sum = "+sum);
            }
//            for(int i=0;i<orders.size();i++)
//            {
//                sum = sum + orders.getPric;
//            }

            model.addAttribute("amount",sum);

            return "home";
        }
        return "login";
    }

    @RequestMapping(value = "/pay")
    public String payNow(@RequestParam("uname") String uname,@RequestParam("amount") int amount,Model model){
        PaypalUsers u = paypalUsersRepository.findPaypalUsersByNumber(uname);
        int balance = u.getAmount();
        System.out.println(balance);
        if(balance>=amount)
        {
            System.out.println(balance-amount);
            int a = balance-amount;
            u.setAmount(a);
            paypalUsersRepository.save(u);

            System.out.println(u.getAmount());
            System.out.println(u.getId());
            model.addAttribute("message","Payment completed successfully");
        }
        else
        {
            model.addAttribute("message","insufficient balance");
        }
        return "payment";
    }
}
