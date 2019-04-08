package com.pucpr.alexandre.crudmarple.Model;

public class Ingredient {

    private String name;
    private Integer priority;

    public Ingredient(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}

