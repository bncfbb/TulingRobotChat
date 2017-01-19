package cn.c4code.robotchat.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/1.
 */

public class TulingRobot {
    private String content;
    private String key;
    private MessageListener listener;

    public TulingRobot(String key, MessageListener listener) {
        content = makeContent();
        this.key = key;
        this.listener = listener;
    }

    public int randomInt(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    private String makeContent() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 24; i++) {
            sb.append((char)randomInt(65, 90));
        }
        return sb.toString();
    }

    public boolean send(String msg) {
        String url = "http://www.tuling123.com/openapi/api";
        String data = "key=" + key + "&info=" + URLEncoder.encode(msg) + "&userid=" + content;
        String result = HttpUtils.sendPost(url, data);
        if(result == null) {
            return false;
        }
        try {
            JSONObject json = new JSONObject(result);
            result = json.getString("text");
            result = json.isNull("url") ? result : result + "\n" + json.getString("url");

            listener.onMessage(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public interface MessageListener {
        void onMessage(String message);
    }
}
