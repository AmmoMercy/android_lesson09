package com.example.a1725121013_lichuanjian_lesson09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.MyViewHolder> {
    private List<DataItem> mDataList;

    public DataItemAdapter(List<DataItem> mDataList) {
        this.mDataList = mDataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
        }
    }

    @Override
    public DataItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder vh = new MyViewHolder((View) LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_view, parent, false));
        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                DataItem item = mDataList.get(position);
                item.setFavorate(!item.isFavorate());
                mDataList.set(position, item);
                notifyItemChanged(position);

            }
        });

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataItem dataItem = mDataList.get(position);
        holder.textView.setText(dataItem.getName());
        if (!dataItem.isFavorate()) {
            holder.imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
