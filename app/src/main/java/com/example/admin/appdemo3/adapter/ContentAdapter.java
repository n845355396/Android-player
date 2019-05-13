package com.example.admin.appdemo3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appdemo3.R;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String[]> arrayList;
    private final LayoutInflater layoutInflater;

    public ContentAdapter(Context context, ArrayList<String[]> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(arrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final TextView create_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.content);
            this.create_time = itemView.findViewById(R.id.create_time);
        }

        public void bindData(String[] i) {
            this.content.setText(i[0]);
            this.create_time.setText(i[1]);
        }
    }



}
