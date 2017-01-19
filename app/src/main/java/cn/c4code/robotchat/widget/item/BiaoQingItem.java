package cn.c4code.robotchat.widget.item;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/1/18.
 */

public class BiaoQingItem {
    public BiaoQingItem(String name, Bitmap bmp) {
        this.name = name;
        this.bmp = bmp;
    }

    public Bitmap getImage() {
        return bmp;
    }

    public String getName() {

        return name;
    }

    private String name;
    private Bitmap bmp;
}
