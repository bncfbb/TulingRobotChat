package cn.c4code.robotchat.widget.item;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;

/**
 * Created by Administrator on 2016/12/31.
 */

public class MessageItem {
    public enum MsgType {
        SEND, RECV
    }

    public String nick;
    public String touxian;
    public Drawable head;
    public SpannableString message;
    public MsgType type;

    public MessageItem(String nick, String touxian, Drawable head, SpannableString message, MsgType type) {
        this.nick = nick;
        this.touxian = touxian;
        this.head = head;
        this.message = message;
        this.type = type;
    }
}
