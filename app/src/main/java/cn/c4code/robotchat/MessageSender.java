package cn.c4code.robotchat;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageButton;

import cn.c4code.robotchat.utils.TulingRobot;

/**
 * Created by Administrator on 2017/1/1.
 */

public class MessageSender extends Thread {
    private View v;
    private Handler handler;
    private String msg;
    private TulingRobot robot;
    private ImageButton sendButton;

    public MessageSender(View v, Handler handler, TulingRobot robot, String msg, ImageButton sendButton) {
        this.v = v;
        this.handler = handler;
        this.robot = robot;
        this.msg = msg;
        this.sendButton = sendButton;
    }

    @Override
    public void run() {
        if(!robot.send(msg)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(v, "网络连接失败!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    sendButton.setEnabled(true);
                }
            });
        }

    }
}
