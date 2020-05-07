package com.example.a1725121013_lichuanjian_lesson09;

public class DataItem {
    private String name;
    private boolean favorate;

    public DataItem(String name, boolean favorate) {
        this.name = name;
        this.favorate = favorate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorate() {
        return favorate;
    }

    public void setFavorate(boolean favorate) {
        this.favorate = favorate;
    }
}
