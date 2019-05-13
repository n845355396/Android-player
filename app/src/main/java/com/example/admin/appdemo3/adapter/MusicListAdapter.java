package com.example.admin.appdemo3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.adapter.interfaces.OnRecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final HashMap<Integer, JSONObject> musicList;
    static JSONObject position;
    private OnRecyclerItemClickListener onRecyclerItemClickListener = null;

    public MusicListAdapter(Context context, HashMap<Integer, JSONObject> musicList) {
        this.context = context;
        this.musicList = musicList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.music_list, viewGroup, false);
//        MusicListAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(musicList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.musicList.size();
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener) {
        this.onRecyclerItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final TextView create_time;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.content = itemView.findViewById(R.id.content);
            this.create_time = itemView.findViewById(R.id.create_time);
        }

        public void bindData(final JSONObject data) {
            MusicListAdapter.position = data;
            System.out.println("适配器：" + data);
            //将创建的View注册点击事件
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerItemClickListener.onItemClick(v, data);
                }
            });

            try {
                this.content.setText((String) data.get("title"));
                this.create_time.setText((String) data.get("author"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
