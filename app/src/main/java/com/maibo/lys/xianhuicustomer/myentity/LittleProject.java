package com.maibo.lys.xianhuicustomer.myentity;

import java.io.Serializable;

/**
 * Created by LYS on 2017/2/9.
 */

public class LittleProject implements Serializable{
    private String project_id;
    private String line;
    private String name;
    private String price;
    private String qty;
    private String unit;

    private String title;
    private String content;

    public LittleProject() {
    }

    public LittleProject(String project_id, String line, String title, String content) {
        this.project_id = project_id;
        this.line = line;
        this.title = title;
        this.content = content;
    }

    public LittleProject(String project_id, String line, String name, String price, String qty, String unit) {
        this.project_id = project_id;
        this.line = line;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
