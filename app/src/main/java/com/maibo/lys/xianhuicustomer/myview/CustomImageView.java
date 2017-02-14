package com.maibo.lys.xianhuicustomer.myview;

/**
 * Created by LYS on 2017/2/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 自定义图片控件，实现点击变高亮(会覆盖父容器的点击事件)
 *
 * @author zengxw 2013-08-22
 */
public class CustomImageView extends ImageView {

    /**
     * 保存当前容器原来设置的图片
     */
    private Drawable drawableSave;
    /**
     * 高亮图片
     */
    private Drawable drawableGrap;

    public CustomImageView(Context context) {
        super(context);
        setListener();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setListener();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {

        this.drawableSave = drawable;
        setSuperImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {

        super.setImageResource(resId);
        this.drawableSave = this.getDrawable();
    }

    /**
     * 调用父类设置图片方法
     */
    private void setSuperImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    /**
     * 设置正常状态
     */
    public void setNormalState() {
        setSuperImageDrawable(drawableSave);
    }

    /**
     * 设置点击状态
     */
    public void setClickedState() {
        setDrawableGrap();//生成点击后图片
        setSuperImageDrawable(drawableGrap);
    }

    /**
     * 绑定事件
     */
    private void setListener() {
        //鼠标事件
        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //鼠标按下
                        setClickedState();
                        break;
                    case MotionEvent.ACTION_UP:     //鼠标松开
                        setNormalState();
                        break;
                    case MotionEvent.ACTION_CANCEL: //取消
                        setNormalState();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    /**
     * 生成高亮图片
     */
    private void setDrawableGrap() {
        if (drawableGrap == null && drawableSave != null)//是否已生成高亮图片
        {
            /**Drawable转Bitmap*/
            int w = drawableSave.getIntrinsicWidth(); //取 drawable 的长宽
            int h = drawableSave.getIntrinsicHeight();

            Bitmap.Config config = drawableSave.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565; //取 drawable 的颜色格式
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);//建立对应 bitmap
            Canvas canvas = new Canvas(bitmap);  //建立对应 bitmap 的画布
            drawableSave.setBounds(0, 0, w, h);
            drawableSave.draw(canvas); //把 drawable 内容画到画布中

            /**转颜色*/
            int array[] = new int[w * h];
            int n = 0;
            int diff = 100;//差异亮度
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) //从上往下扫描
                {
                    //像素pixel是32位的整数，四个字节分别对应透明通道、红色、绿色、蓝色通道
                    int color = bitmap.getPixel(j, i);
                    int r = Color.red(color) + diff;
                    int g = Color.green(color) + diff;
                    int b = Color.blue(color) + diff;
                    int a = Color.alpha(color);

                    if (r > 255) r = 255;
                    if (g > 255) g = 255;
                    if (b > 255) b = 255;

                    color = Color.argb(a, r, g, b);

                    array[n] = color;
                    n++;
                }
            }
            bitmap = Bitmap.createBitmap(array, w, h, Bitmap.Config.ARGB_8888);
            drawableGrap = new BitmapDrawable(getResources(), bitmap);
        }
    }
}