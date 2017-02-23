package com.twx.test.domain;

import java.math.BigDecimal;

/**
 * Created by vincent.tong on 2017/2/23.
 */
public class Student extends People {
    private BigDecimal score;

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
