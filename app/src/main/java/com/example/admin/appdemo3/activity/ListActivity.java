package com.example.admin.appdemo3.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.appdemo3.BaseActivity;
import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.adapter.RecyclerAdapter;

import java.util.ArrayList;

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.setfloorLayout(this);

        RecyclerView recyclerView = findViewById(R.id.rl_list);
        recyclerView.setHasFixedSize(true);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        //设置布局管理器
//        recyclerView.setLayoutManager(layoutManager);
//
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//        //设置分隔线
////        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//        //设置增加或删除条目的动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        recyclerView.setMinimumWidth(10);
//
//        ArrayList<String> arrayList = new ArrayList();
//
//        for (int i = 0; i < 100; i++) {
//            arrayList.add("数据：" + i);
//        }
//        ListAdapter listAdapter = new ListAdapter(this, arrayList);
//        recyclerView.setAdapter(listAdapter);


        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        ArrayList<String> arrayList = new ArrayList();

        for (int i = 0; i < 100; i++) {
            arrayList.add("数据：" + i);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

    }
}
