package com.maibo.lys.xianhuicustomer.myentity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LYS on 2017/2/8.
 */

public class Project implements Serializable{
    private String agent_id;
    private String project_id;
    private String fullname;
    private String type;
    private String retail_price;
    private String summary;
    private String disabled;
    private String create_time;
    private String last_modify_time;
    private String[] images_url;

    //新增
    private String project_code;
    private String org_id;
    private String description;
    private String sync_time;
    private String creator;
    private String modifier;
    private List<LittleProject> project_detail;
    private List<LittleProject> project_note;
    public Project() {
    }

    public Project(String project_id, String agent_id, String project_code, String org_id, String type,
                   String retail_price, String summary, String description, String disabled, String sync_time,
                   String creator, String create_time, String modifier, String last_modify_time,
                   String[] images_url, String fullname, List<LittleProject> project_detail, List<LittleProject> project_note) {
        this.project_id = project_id;
        this.agent_id = agent_id;
        this.project_code = project_code;
        this.org_id = org_id;
        this.type = type;
        this.retail_price = retail_price;
        this.summary = summary;
        this.description = description;
        this.disabled = disabled;
        this.sync_time = sync_time;
        this.creator = creator;
        this.create_time = create_time;
        this.modifier = modifier;
        this.last_modify_time = last_modify_time;
        this.images_url = images_url;
        this.fullname = fullname;
        this.project_detail = project_detail;
        this.project_note = project_note;
    }

    public Project(String agent_id, String project_id, String fullname, String type, String retail_price, String summary,
                   String disabled, String create_time, String last_modify_time, String[] images_url) {
        this.agent_id = agent_id;
        this.project_id = project_id;
        this.fullname = fullname;
        this.type = type;
        this.retail_price = retail_price;
        this.summary = summary;
        this.disabled = disabled;
        this.create_time = create_time;
        this.last_modify_time = last_modify_time;
        this.images_url = images_url;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(String last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public String[] getImages_url() {
        return images_url;
    }

    public void setImages_url(String[] images_url) {
        this.images_url = images_url;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSync_time() {
        return sync_time;
    }

    public void setSync_time(String sync_time) {
        this.sync_time = sync_time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public List<LittleProject> getProject_detail() {
        return project_detail;
    }

    public void setProject_detail(List<LittleProject> project_detail) {
        this.project_detail = project_detail;
    }

    public List<LittleProject> getProject_note() {
        return project_note;
    }

    public void setProject_note(List<LittleProject> project_note) {
        this.project_note = project_note;
    }
}
