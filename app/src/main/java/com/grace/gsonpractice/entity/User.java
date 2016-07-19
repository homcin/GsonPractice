package com.grace.gsonpractice.entity;

import com.google.gson.annotations.SerializedName;

/**
 * POJO类
 */
public class User {

    private String name;

    private int age;

    @SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
    private String emailAddress;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
