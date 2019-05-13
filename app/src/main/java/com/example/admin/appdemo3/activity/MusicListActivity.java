package com.example.admin.appdemo3.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.adapter.MusicListAdapter;
import com.example.admin.appdemo3.adapter.interfaces.OnRecyclerItemClickListener;
import com.example.admin.appdemo3.lib.SeekbarRotate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MusicListActivity extends AppCompatActivity {

    RecyclerView music_list;
    ImageView yingcan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        music_list = findViewById(R.id.music_list);

        //让列表宽度占一半
//        Display display = getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
//        Window window = getWindow();
//        android.view.WindowManager.LayoutParams windowLayoutParams = window.getAttributes(); // 获取对话框当前的参数值
////        windowLayoutParams.width = (int) (display.getWidth() * 0.5); // 宽度设置为屏幕的0.95
//        windowLayoutParams.height = (display.getHeight() / 2); // 高度设置为屏幕的0.6
//        windowLayoutParams.verticalMargin = 1;
//        windowLayoutParams.alpha = 0.5f;// 设置透明度

        yingcan = findViewById(R.id.yingcan);
        Display display = getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
        Window window = getWindow();
        yingcan.setMinimumHeight(display.getHeight() / 2);// 高度设置为屏幕的0.6

        yingcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //使用列表适配器
//        music_list.setHasFixedSize(true);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        music_list.setLayoutManager(manager);

        HashMap<Integer, JSONObject> musicList = new HashMap<>();

        try {
            JSONObject musicInfo;
            for (int i = 0; i < UiActivity.musicListJson.length(); i++) {
                musicInfo = ((JSONObject) (UiActivity.musicListJson.get(i)));
                musicList.put(i, musicInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MusicListAdapter adapter = new MusicListAdapter(this, musicList);

        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, JSONObject data) {
                System.out.println("MusicListActivity下:" + data);
                UiActivity.mp3.stop();
                UiActivity.mp3.release();//释放资源
                UiActivity.mp3 = new MediaPlayer();
                try {
//                    JSONObject data = (JSONObject) UiActivity.musicListJson.get(position);
                    String url = (String) data.get("url");
                    System.out.println("列表点击歌曲链接：" + url);
                    UiActivity.mp3.setDataSource(url);
                    UiActivity.mp3.prepare();
                    UiActivity.mp3.start();

                    int total_time = UiActivity.mp3.getDuration();
                    UiActivity.listen_jindutiao_sb.setMax(total_time);
                    UiActivity.seekbarRotate.cancel();
                    UiActivity.seekbarRotate = new SeekbarRotate(total_time,1000);
                    UiActivity.seekbarRotate.start();

                    UiActivity.musicInfo(data, UiActivity.uiActivity);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        music_list.setAdapter(adapter);
    }
}
