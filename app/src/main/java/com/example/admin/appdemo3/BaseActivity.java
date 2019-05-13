package com.example.admin.appdemo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.admin.appdemo3.activity.DemoActivity;
import com.example.admin.appdemo3.activity.UiActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setfloorLayout(this);
        getSupportActionBar().hide();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    public void setfloorLayout(AppCompatActivity activity) {

        // 导入一个布局文件，这是底部控件
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.floor, null);
        activity.addContentView(view, lp);

        //为控件加事件
        View navigation_home = findViewById(R.id.navigation_home);
        navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        View navigation_dashboard = findViewById(R.id.navigation_dashboard);
        navigation_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        });

        View navigation_notifications = findViewById(R.id.navigation_notifications);
        navigation_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(BaseActivity.this, UiActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                startActivity(intent);
            }
        });
    }
}
