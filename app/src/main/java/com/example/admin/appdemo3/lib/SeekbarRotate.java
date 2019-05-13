package com.example.admin.appdemo3.lib;

import android.media.MediaPlayer;
import android.os.CountDownTimer;

import com.example.admin.appdemo3.activity.UiActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * 自动旋转图片
 */
public class SeekbarRotate extends CountDownTimer {

    private long millisInFuture;
    private long countDownInterval;

    public SeekbarRotate(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        System.out.println("歌曲时间："+(millisInFuture-millisUntilFinished));
        int time = (int) (millisInFuture-millisUntilFinished);
        UiActivity.listen_jindutiao_sb.setProgress(time);
        String current_time = UiActivity.millisToStringShort(time);
        UiActivity.listen_current_tv.setText(current_time);
    }

    @Override
    public void onFinish() {
        UiActivity.mp3.stop();
        UiActivity.mp3.release();//释放资源
        UiActivity.mp3 = new MediaPlayer();
        try {
            Random random = new Random();
            int xiaobiao = random.nextInt(UiActivity.musicListJson.length());

            //配置音乐信息
            JSONObject musicInfo = ((JSONObject) (UiActivity.musicListJson.get(xiaobiao)));
            String url = (String) musicInfo.get("url");

            UiActivity.mp3.setDataSource(url);
            UiActivity.mp3.prepare();
            UiActivity.mp3.start();

            int total_time = UiActivity.mp3.getDuration();
            UiActivity.listen_jindutiao_sb.setMax(total_time);
            UiActivity.seekbarRotate.cancel();
            UiActivity.seekbarRotate = new SeekbarRotate(total_time,1000);
            UiActivity.seekbarRotate.start();

            UiActivity.musicInfo(musicInfo, UiActivity.uiActivity);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
