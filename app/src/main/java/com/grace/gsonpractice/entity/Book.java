package com.grace.gsonpractice.entity;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

import java.util.SimpleTimeZone;

/**
 * Created by Administrator on 2016/7/18.
 */
public class Book {

    //name和description是Version4及之前的字段。
    @Until(5)
    private String name;
    @Until(5)
    private String description;

    //Version5之后新增字段。
    @Since(5)
    private double rating; //评价

    public Book(String name, String description, double rating) {
        this.name = name;
        this.description =description;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
