package cn.c4code.robotchat.widget.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.c4code.robotchat.R;
import cn.c4code.robotchat.widget.item.BiaoQingItem;

/**
 * Created by Administrator on 2017/1/17.
 */

public class BiaoQingRecyclerViewAdapter extends RecyclerView.Adapter<BiaoQingRecyclerViewAdapter.ViewHolder>  {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<BiaoQingItem> mData = new ArrayList<>();
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemLongClickListener mItemLongClickListener;

    public BiaoQingRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.biaoqing_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null)
                    mItemClickListener.onItemClick(null, v, holder.getAdapterPosition(), holder.getAdapterPosition());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mItemLongClickListener != null)
                    return mItemLongClickListener.onItemLongClick(null, v, holder.getAdapterPosition(), holder.getAdapterPosition());
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bmp = mData.get(position).getImage();
        //drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //drawable.setBounds(0, 0, 100, 100);
        holder.view.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = (ImageView) itemView.findViewById(R.id.biaoqing_view);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public List<BiaoQingItem> getData() {
        return mData;
    }
}
