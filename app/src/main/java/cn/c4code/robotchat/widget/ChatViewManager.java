package cn.c4code.robotchat.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cn.c4code.robotchat.widget.adapter.ChatRecyclerViewAdapter;
import cn.c4code.robotchat.widget.item.MessageItem;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ChatViewManager {
    private Context context;
    private RecyclerView chat_view;
    private ChatRecyclerViewAdapter adapter;

    public ChatViewManager(Context context, RecyclerView chat_view) {
        this.context = context;
        this.chat_view = chat_view;

        LinearLayoutManager layout = new LinearLayoutManager(context);
        layout.setStackFromEnd(true);
        chat_view.setLayoutManager(layout);

        adapter = new ChatRecyclerViewAdapter(context);
        chat_view.setAdapter(adapter);
        chat_view.setItemAnimator(new DefaultItemAnimator());
    }

    public void pushItem(MessageItem item) {
        adapter.getData().add(item);
        adapter.notifyItemInserted(adapter.getItemCount());
        chat_view.smoothScrollToPosition(adapter.getItemCount());
    }

    public void popItem() {
        removeItem(adapter.getItemCount());
    }

    public void removeItem(int index) {
        adapter.getData().remove(index);
        chat_view.smoothScrollToPosition(index);
        adapter.notifyItemRemoved(index);
    }

    public MessageItem getItemData(int position) {
        return adapter.getData().get(position);
    }

    public ChatRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return chat_view;
    }

}
