package cn.c4code.robotchat.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2017/1/18.
 */

public class AppUtils {
    /**
     * 设置输入法,如果当前页面输入法打开则关闭
     * @param activity
     */
    public static void hideInputMethod(Activity activity){
        View a = activity.getCurrentFocus();
        if(a != null){
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 强制显示输入法
     * @param activity
     * @param view
     */
    public static void toggleSoftInput(Activity activity, View view){
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {

        }
    }

    public static void hiddenView(Activity activity, final View v) {
        v.startAnimation(AnimationUtils.makeOutAnimation(activity, true));
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.GONE);
            }
        }, 400);
    }

    public static void displayView(Activity activity, View v) {
        v.setVisibility(View.VISIBLE);
        v.startAnimation(AnimationUtils.makeInAnimation(activity, true));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
