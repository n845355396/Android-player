package com.example.admin.appdemo3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.activity.ListActivity;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter{

    private final static int HEAD_COUNT = 1;
    private final static int FOOT_COUNT = 1;

    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;
    private final ArrayList<String> list;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public RecyclerAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.list = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getContentSize(){
        return list.size();
    }

    public boolean isHead(int position){
        return HEAD_COUNT != 0 && position == 0;
    }

    public boolean isFoot(int position){
        return FOOT_COUNT != 0 && position == getContentSize() + HEAD_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        int contentSize = getContentSize();
        if (HEAD_COUNT != 0 && position == 0){ // 头部
            return TYPE_HEAD;
        }else if(FOOT_COUNT != 0 && position == HEAD_COUNT + contentSize){ // 尾部
            return TYPE_FOOTER;
        }else{
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head,parent,false);
            return new RecyclerAdapter.HeadHolder(itemView);
        }else if(viewType == TYPE_CONTENT){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            return new RecyclerAdapter.ContentHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            return new RecyclerAdapter.FootHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerAdapter.HeadHolder){ // 头部

        }else if(holder instanceof RecyclerAdapter.ContentHolder){ // 内容
            RecyclerAdapter.ContentHolder myHolder = (RecyclerAdapter.ContentHolder) holder;
            myHolder.itemText.setText(list.get(position - 1));
        }else{ // 尾部

        }
    }

    @Override
    public int getItemCount() {
        return list.size() + HEAD_COUNT + FOOT_COUNT;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder{
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    // 内容
    private class ContentHolder extends RecyclerView.ViewHolder{
        TextView itemText;
        public ContentHolder(View itemView) {
            super(itemView);
            itemText = (TextView)itemView.findViewById(android.R.id.text1);
        }
    }

    // 尾部
    private class FootHolder extends RecyclerView.ViewHolder{
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

}