package com.example.admin.appdemo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appdemo3.activity.DemoActivity;
import com.example.admin.appdemo3.activity.ListActivity;
import com.example.admin.appdemo3.okhttp.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;

//import com.example.admin.appdemo3.activity.Demo2Activity;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setfloorLayout(this);

//        mTextMessage = findViewById(R.id.message);
//        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                Toast.makeText(MainActivity.this, "大大大大", Toast.LENGTH_SHORT).show();
            }
        });

        Button bt_tiao = findViewById(R.id.bt_tiao);
        bt_tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        });

        //使用异步请求获取数据
        RequestManager instance = RequestManager.getInstance(MainActivity.this);
        instance.setBASE_URL("http://47.101.180.178");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "get");
        Call call = instance.requestAsyn("android.php", 1, hashMap,new TestReqCallBack());

    }

    public class TestReqCallBack implements RequestManager.ReqCallBack {

        @Override
        public void onReqSuccess(Object result) {
            try {
                JSONObject resultData = new JSONObject((String) result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("成功");
        }

        @Override
        public void onReqFailed(String errorMsg) {
            System.out.println("失败");
        }
    }

    public void showList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}

