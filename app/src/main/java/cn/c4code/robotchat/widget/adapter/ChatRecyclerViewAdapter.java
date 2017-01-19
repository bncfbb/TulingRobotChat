package cn.c4code.robotchat.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.c4code.robotchat.R;
import cn.c4code.robotchat.widget.item.MessageItem;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {
    private static final int TYPE_SEND = 0x0;
    private static final int TYPE_RECV = 0x1;

    private LayoutInflater layoutInflater;
    private Context context;
    private List<MessageItem> mData = new ArrayList<>();
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemLongClickListener mItemLongClickListener;

    public ChatRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder;


        switch(viewType) {
            case TYPE_SEND:
                view = layoutInflater.inflate(R.layout.chat_list_right, parent, false);
                break;
            case TYPE_RECV:
                view = layoutInflater.inflate(R.layout.chat_list_left, parent, false);
                break;
        }

        holder = new ViewHolder(view, viewType);

        final ViewHolder temp_holder = holder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(null, v, temp_holder.getAdapterPosition(), temp_holder.getAdapterPosition());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mItemLongClickListener != null)
                    return mItemLongClickListener.onItemLongClick(null, v, temp_holder.getAdapterPosition(), temp_holder.getAdapterPosition());
                return false;
            }
        });

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return (mData.get(position).type == MessageItem.MsgType.SEND) ? TYPE_SEND : TYPE_RECV;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nick.setText(mData.get(position).nick);
        holder.touxian.setText(mData.get(position).touxian);
        holder.message.setText(mData.get(position).message);
        holder.head.setImageDrawable(mData.get(position).head);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nick;
        public TextView touxian;
        public CircleImageView head;
        public TextView message;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public ViewHolder(View itemView, int type) {
            this(itemView);

            switch(type) {
                case TYPE_SEND:
                    nick = (TextView) itemView.findViewById(R.id.chat_list_nick_right);
                    touxian = (TextView) itemView.findViewById(R.id.chat_list_touxian_right);
                    head = (CircleImageView) itemView.findViewById(R.id.chat_list_head_right);
                    message = (TextView) itemView.findViewById(R.id.chat_list_message_right);
                    break;
                case TYPE_RECV:
                    nick = (TextView) itemView.findViewById(R.id.chat_list_nick_left);
                    touxian = (TextView) itemView.findViewById(R.id.chat_list_touxian_left);
                    head = (CircleImageView) itemView.findViewById(R.id.chat_list_head_left);
                    message = (TextView) itemView.findViewById(R.id.chat_list_message_left);
                    break;
            }
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public List<MessageItem> getData() {
        return mData;
    }
}
