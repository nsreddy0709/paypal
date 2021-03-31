package com.example.paypal.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "flag")
public class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "uid")
    private int uid;
    @Column(name = "amount")
    private int amount;
    @Column(name = "flag")
    private String flag;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    @Column(name = "time")
    private String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
