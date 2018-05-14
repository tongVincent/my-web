package com.twx.core.util.freemarker;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author vincent.tong on 2017/7/2
 */
public class ObjectMethod implements TemplateMethodModelEx {
    private final People people = new People("vincent", 23);

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return people;
    }

    public static class People {
        private String name;
        private int age;
        private BigDecimal money;
        private Date birthDay;

        public People() {
        }

        public People(String name, int age) {
            this.name = name;
            this.age = age;
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

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public String getMyName() {
            return name;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }
    }
}




