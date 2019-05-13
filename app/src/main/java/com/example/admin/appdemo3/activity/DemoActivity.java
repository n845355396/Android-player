package com.example.admin.appdemo3.activity;


import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.appdemo3.BaseActivity;
import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.adapter.ContentAdapter;
import com.example.admin.appdemo3.okhttp.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        this.setfloorLayout(this);

        RecyclerView rl_content = findViewById(R.id.rl_content);
        rl_content.setHasFixedSize(true);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rl_content.setLayoutManager(manager);

        //获取数据
        final DemoActivity demoActivity = this;
        final ArrayList<String[]> content = new ArrayList<>();
        new Thread() {
            public void run() {
                RequestManager instance = RequestManager.getInstance(demoActivity);

                String url = "android.php";
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "get");

                instance.requestSyn(url, 1, hashMap);

                JSONObject result = (JSONObject) instance.getResult("json");

                System.out.println("pc" + result);
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject data = new JSONObject(result.get(String.valueOf(i)).toString());
                        content.add(new String[]{(String) data.get("content"), (String) data.get("create_time")});
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        ContentAdapter contentAdapter = new ContentAdapter(this, content);
        rl_content.setAdapter(contentAdapter);
    }

    public void saveData(View view) {
        final DemoActivity demoActivity = this;
//        final String msg = "保存失败";
        new Thread() {
            public void run() {
                Looper.prepare();
                RequestManager instance = RequestManager.getInstance(demoActivity);
                instance.setBASE_URL("http://47.101.180.178");

                String url = "android.php";
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "add");

                EditText et_content = findViewById(R.id.et_message);
                String content = et_content.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(demoActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                }
                hashMap.put("content", content);

                instance.requestSyn(url, 1, hashMap);

                JSONObject result = (JSONObject) instance.getResult("json");
//                String result = (String) instance.getResult("");

                try {
//                    int code = (int) result.get("code");
                    String msg = (String) result.get("msg");

                    Toast.makeText(demoActivity, msg, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(demoActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                Looper.loop();

            }
        }.start();

    }
}
