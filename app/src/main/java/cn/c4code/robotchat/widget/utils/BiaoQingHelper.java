package cn.c4code.robotchat.widget.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.c4code.robotchat.utils.AppUtils;
import cn.c4code.robotchat.widget.item.BiaoQingItem;

/**
 * Created by Administrator on 2017/1/18.
 */

public class BiaoQingHelper {
    private static final String TAG = "BiaoQingHelper";
    private static Pattern p;

    private int mBqSize;

    public HashMap<String, Bitmap> getmImageSpanSet() {
        return mImageSet;
    }

    HashMap<String, Bitmap> mImageSet = new HashMap<>();

    private Context context;

    public BiaoQingHelper(Context context) {
        this.context = context;
        mBqSize = AppUtils.dip2px(context, 30);


        try {
            AssetManager assets = context.getAssets();
            String[] fileSet = assets.list("");
            
            for(String bq : fileSet) {
                if(bq.endsWith(".png")) {
                    InputStream is = assets.open(bq);
                    Bitmap bmp = BitmapFactory.decodeStream(is);

                    bmp = Bitmap.createScaledBitmap(bmp, mBqSize, mBqSize, true);
                    //bmp.setWidth(80);
                    //Drawable d = new BitmapDrawable(bmp);
                    mImageSet.put(bq.substring(0, bq.length() - 4), bmp);
                    is.close();
                }
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        p = Pattern.compile("\\[(.*?)\\]");
    }

    public SpannableString parseMessage(String msg) {
        SpannableString span = new SpannableString(msg);
        Matcher m = p.matcher(msg);
        while(m.find()) {
            String bq = m.group(1);
            if(mImageSet.keySet().contains(bq)) {
                ImageSpan image = new ImageSpan(context, mImageSet.get(bq));
                span.setSpan(image, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

        }
        return span;
    }

    public CharSequence biaoQingtoCharSequence(BiaoQingItem item) {
        SpannableString span = new SpannableString("[" + item.getName() + "]");
        span.setSpan(new ImageSpan(context, mImageSet.get(item.getName())), 0, item.getName().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}

