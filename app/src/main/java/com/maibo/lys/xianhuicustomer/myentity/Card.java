package com.maibo.lys.xianhuicustomer.myentity;

/**
 * Created by LYS on 2017/2/8.
 */

public class Card {
    public String card_num;
    public String amount;
    public String card_name;

    public Card() {
    }

    public Card(String card_num, String amount, String card_name) {
        this.card_num = card_num;
        this.amount = amount;
        this.card_name = card_name;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }
}
