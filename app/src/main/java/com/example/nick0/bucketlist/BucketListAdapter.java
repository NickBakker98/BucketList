package com.example.nick0.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListViewHolder> {

    private Context context;
    public List<BucketListObject> listBucketList;

    public BucketListAdapter(Context context, List<BucketListObject> listBucketList) {
        this.context = context;
        this.listBucketList = listBucketList;
    }

    @Override

    public BucketListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell, parent, false);
        return new BucketListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final BucketListViewHolder holder, final int position) {
        // Gets a single item in the list from its position
        final BucketListObject bucketListObject = listBucketList.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list
        holder.bucketListCheckBox.setText(bucketListObject.getBucketListTitle());
        holder.description.setText(bucketListObject.getBucketListDescription());

        holder.bucketListCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int paintFlag = isChecked ? Paint.STRIKE_THRU_TEXT_FLAG : 0;
                holder.bucketListCheckBox.setPaintFlags(paintFlag);
                holder.description.setPaintFlags(paintFlag);
            }
        });
    }

    @Override
    public int getItemCount(){
        return listBucketList.size();
    }

    public void swapList (List<BucketListObject> newList) {

        listBucketList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}

