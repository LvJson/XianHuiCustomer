package com.maibo.lys.xianhuicustomer.myentity;

/**
 * Created by LYS on 2017/2/8.
 */

public class CourseCard extends Card{
    private String times;

    public CourseCard() {
    }

    public CourseCard(String card_num, String amount, String card_name, String times) {
        super(card_num, amount, card_name);
        this.times = times;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
