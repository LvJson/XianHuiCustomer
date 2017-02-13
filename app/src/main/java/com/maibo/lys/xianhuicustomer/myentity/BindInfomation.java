package com.maibo.lys.xianhuicustomer.myentity;

import java.util.List;

/**
 * Created by LYS on 2017/2/8.
 */

public class BindInfomation {
    private String agent_id;
    private String agent_name;
    private String user_id;
    private String display_name;
    private String org_id;
    private String org_name;
    private List<Card> vipcard_list;
    private List<CourseCard> courseCards_list;

    public BindInfomation() {
    }

    public BindInfomation(String agent_id, String agent_name, String user_id, String display_name, String org_id,
                          String org_name, List<Card> vipcard_list, List<CourseCard> courseCards_list) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.user_id = user_id;
        this.display_name = display_name;
        this.org_id = org_id;
        this.org_name = org_name;
        this.vipcard_list = vipcard_list;
        this.courseCards_list = courseCards_list;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public List<CourseCard> getCourseCards_list() {
        return courseCards_list;
    }

    public void setCourseCards_list(List<CourseCard> courseCards_list) {
        this.courseCards_list = courseCards_list;
    }

    public List<Card> getVipcard_list() {
        return vipcard_list;
    }

    public void setVipcard_list(List<Card> vipcard_list) {
        this.vipcard_list = vipcard_list;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
