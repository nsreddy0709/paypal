package com.example.paypal;

import com.example.paypal.config.JwtTokenUtil;

import com.example.paypal.model.*;
import com.example.paypal.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
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

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    FlagRepository flagRepository;

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
            model.addAttribute("jwt",jwt);
            //System.out.println(jwt);
            String name = jwtTokenUtil.extractUsername(jwt);
            System.out.println(name);
            Users users = usersRepository.findUsersByEmail(name);
            System.out.println(users);
            //System.out.println(users.getUser_id());
            ArrayList<Cart> carts = cartRepository.findCartByUid(users.getUser_id());
            //System.out.println(orders.size());
            int sum = 0;
            for (Cart c:
                    carts) {

                sum = sum + c.getAmount();
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
    public String payNow(@RequestParam("uname") String uname,@RequestParam("amount") int amount,@RequestParam("jwt") String jwt,Model model){
        PaypalUsers u = paypalUsersRepository.findPaypalUsersByNumber(uname);
        int balance = u.getAmount();
        String name = jwtTokenUtil.extractUsername(jwt);
        Users users = usersRepository.findUsersByEmail(name);
        System.out.println(balance);
        if(balance>=amount)
        {
            System.out.println(balance-amount);
            int a = balance-amount;
            u.setAmount(a);
            u.setNumber(u.getNumber());
            paypalUsersRepository.save(u);

//            System.out.println(u.getAmount());
//            System.out.println(u.getId());
//
            Payment p = new Payment();


            LocalDate localDate = LocalDate.now();
            String date = localDate.toString();

            LocalTime localTime = LocalTime.now();
            String time = localTime.toString();
            p.setUid(u.getId());
            p.setStatus("Succesful");
            p.setAmount(amount);
            p.setDate(date+" "+time);
            paymentRepository.save(p);

            Flag f = new Flag();
            f.setUid(users.getUser_id());
            f.setAmount(amount);
            f.setFlag("Payment Success");
            f.setDate(date+" "+time);
            flagRepository.save(f);


            cartRepository.deleteCartByUid(users.getUser_id());
            ordersRepository.deleteOrdersByUid(users.getUser_id());


            model.addAttribute("message","Payment completed successfully");
        }
        else
        {
            model.addAttribute("message","insufficient balance");
            Payment p = new Payment();


            LocalDate localDate = LocalDate.now();
            String date = localDate.toString();

            LocalTime localTime = LocalTime.now();
            String time = localTime.toString();
            p.setUid(u.getId());
            p.setStatus("Un-Succesful");
            p.setAmount(amount);
            p.setDate(date+" "+time);
            paymentRepository.save(p);

            Flag f = new Flag();
            f.setUid(users.getUser_id());
            f.setAmount(amount);
            f.setFlag("Payment Un-Success");
            f.setDate(date+" "+time);
            flagRepository.save(f);
        }

        return "payment";
    }
}
