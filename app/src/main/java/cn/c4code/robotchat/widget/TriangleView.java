package cn.c4code.robotchat.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import cn.c4code.robotchat.R;

/**
 * Created by Administrator on 2017/1/2.
 */

public class TriangleView extends View {
    private int color;
    private String direction;

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Triangle);
        color = a.getColor(R.styleable.Triangle_color, 0xfffffff);
        direction = a.getString(R.styleable.Triangle_direction);
    }

    public TriangleView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(color);

        Path path = new Path();

        switch (direction){
            case "left":

                path.moveTo(0, canvas.getHeight() / 2);// 此点为多边形的起点
                path.lineTo(canvas.getWidth(), 0);
                path.lineTo(canvas.getWidth(), canvas.getHeight());

                break;
            case "right":
                path.moveTo(canvas.getWidth(), canvas.getHeight() / 2);// 此点为多边形的起点
                path.lineTo(0, 0);
                path.lineTo(0, canvas.getHeight());

                break;
        }

        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

    }
}
