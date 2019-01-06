package com.example.nick0.bucketlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class BucketListViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public CheckBox bucketListCheckBox;
    public View view;

    public BucketListViewHolder(View itemView) {
        super(itemView);
        description = itemView.findViewById(R.id.description_textview);
        bucketListCheckBox = itemView.findViewById(R.id.bucketlist_checkbox);
        view = itemView;
    }
}
