package com.twx.test.domain;

/**
 * Created by vincent.tong on 2017/3/3.
 */
public class Car {
    private String color;
    private int usedYear;

    public String getColor() {
        return color;
    }

    public Car setColor(String color) {
        this.color = color;
        return this;
    }

    public int getUsedYear() {
        return usedYear;
    }

    public Car setUsedYear(int usedYear) {
        this.usedYear = usedYear;
        return this;
    }
}
