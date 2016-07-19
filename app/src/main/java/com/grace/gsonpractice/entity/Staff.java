package com.grace.gsonpractice.entity;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Staff {

    public static String department;

    final String company = "Baidu";

    public String name;

    private double salary;

    protected String duty;

    int age;

    public Staff(String name, int age, double salary, String duty) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.duty = duty;
    }
}
