package com.maibo.lys.xianhuicustomer.myentity;

/**
 * Created by LYS on 2017/1/17.
 */

public class Ad {
    private int iconResId;
    private String intro;


    public Ad(int iconResId, String intro) {
        super();
        this.iconResId = iconResId;
        this.intro = intro;
    }
    public int getIconResId() {
        return iconResId;
    }
    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }


}
