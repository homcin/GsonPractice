package com.grace.gsonpractice.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
public class Category {
    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private List<Category> children;

    private Category parent;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
