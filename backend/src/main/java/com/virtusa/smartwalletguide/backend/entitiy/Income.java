package com.virtusa.smartwalletguide.backend.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INCOME")
public class Income extends SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "INCOME_CATEGORY")
    private String income_category;
    @Column(name = "INCOME")
    private double income;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    @JsonIgnore
    private User user;

    public Income(String income_category, double income, Date date, User user) {
        this.income_category = income_category;
        this.income = income;
        this.date = date;
        this.user = user;
    }

    public Income(String income_category, double income) {
        this.income_category = income_category;
        this.income = income;
    }



    public Income(String income_category, double income, User user) {
        this.income_category = income_category;
        this.income = income;
        this.user = user;
    }

    public Income() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncome_category() {
        return income_category;
    }

    public void setIncome_category(String income_category) {
        this.income_category = income_category;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
