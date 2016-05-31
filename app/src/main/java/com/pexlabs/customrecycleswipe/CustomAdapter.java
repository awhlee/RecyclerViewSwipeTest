package com.pexlabs.customrecycleswipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
        implements View.OnClickListener {
    private ArrayList<String> mItems;
    private Context mContext;

    public CustomAdapter(ArrayList<String> items, Context context) {
        this.mItems = items;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout,
                viewGroup, false);
        TextView tv = (TextView)view.findViewById(R.id.tv_row_item);
        tv.setOnClickListener(this);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tv_row_item.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItem(String newitem) {
        mItems.add(newitem);
        notifyItemInserted(mItems.size());
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tv_row_item;
        public CustomViewHolder(View view) {
            super(view);
            tv_row_item = (TextView)view.findViewById(R.id.tv_row_item);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "Clicked it!!", Toast.LENGTH_SHORT).show();
        return;
    }
}