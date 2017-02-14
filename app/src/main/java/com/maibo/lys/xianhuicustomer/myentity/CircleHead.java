package com.maibo.lys.xianhuicustomer.myentity;

import android.content.Intent;

/**
 * Created by LYS on 2017/2/13.
 */

public class CircleHead {
    private Intent intent;
    private int tag;

    public CircleHead(Intent intent, int tag) {
        this.intent = intent;
        this.tag = tag;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
