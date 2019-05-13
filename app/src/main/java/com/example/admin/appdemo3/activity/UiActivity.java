package com.example.admin.appdemo3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.appdemo3.BaseActivity;
import com.example.admin.appdemo3.R;
import com.example.admin.appdemo3.lib.ImageRotate;
import com.example.admin.appdemo3.lib.ImageTools;
import com.example.admin.appdemo3.lib.SeekbarRotate;
import com.example.admin.appdemo3.okhttp.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class UiActivity extends BaseActivity {

    //    ArrayList<String> musicList;
    public static MediaPlayer mp3 = new MediaPlayer();
    ImageButton bt_play;
    ImageView ic_needle;
    public static TextView music_name;
    public static TextView music_singer;
    public static ImageView image_bg;
    public static ImageView ic_circle;
    public static UiActivity uiActivity;
    ImageButton bt_music_list;
    public static SeekBar listen_jindutiao_sb;
    static TextView listen_length_tv;
    public static TextView listen_current_tv;

    int status = 1;
    int pause_ratotion = 320;
    int play_ratotion = 0;
    ImageRotate imageRotate;

    public static Bitmap urlBitMap = null;
    public static JSONArray musicListJson = null;
    public static Boolean apiStatus = true;
    public static SeekbarRotate seekbarRotate;
    public int current_music_sub = 0;

    String music_url = "https://api.hibai.cn/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        uiActivity = this;

        ic_needle = findViewById(R.id.ic_needle);

        music_name = findViewById(R.id.music_name);
        music_singer = findViewById(R.id.music_singer);

        image_bg = findViewById(R.id.image_bg);
        bt_music_list = findViewById(R.id.bt_music_list);
        listen_jindutiao_sb = findViewById(R.id.listen_jindutiao_sb);
        listen_length_tv = findViewById(R.id.listen_length_tv);
        listen_current_tv = findViewById(R.id.listen_current_tv);

        //音乐列表事件
        bt_music_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UiActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });

        //设置模糊背景
//        RelativeLayout layout_bg = findViewById(R.id.layout_bg);
//        Bitmap bm_bg = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg)).getBitmap();
//        bm_bg = GsdFastBlur.fastblur(this, bm_bg, 12);
//        Drawable drawable = new BitmapDrawable(bm_bg);
//        layout_bg.setBackground(drawable);

        //设置模糊背景2
//        ImageView imageView = findViewById(R.id.image_bg);
//        imageView.setMinimumWidth(getDeviceWidth(this));
//        imageView.setMinimumHeight(getDeviceHeight(this));

        Glide.with(this).load(R.drawable.bg)
                .apply(bitmapTransform(new BlurTransformation(30)))
                .into((ImageView) findViewById(R.id.image_bg));

        //圆形图片
        ic_circle = findViewById(R.id.ic_circle);
        Bitmap firstImage;
        //获取一个图片转bitmap，并设置大小
        firstImage = ImageTools.imageScale(((BitmapDrawable) getResources().getDrawable(R.drawable.bg)).getBitmap(), 200, 200);
        //将图片变圆
        firstImage = ImageTools.toOvalBitmap(firstImage, 1500);
        //使用一张黑底的画布，将图片放上去
        firstImage = ImageTools.blackBottomBitmap(firstImage, 300, 300);
        //再把返回的带黑布的图片变圆
        firstImage = ImageTools.toOvalBitmap(firstImage, 1500);
        ic_circle.setImageBitmap(firstImage);

        //唱盘旋转跳跃闭着眼
        imageRotate = new ImageRotate(100, 1000, ic_circle);
        imageRotate.start();

        //导入一些歌曲
//        musicList = new ArrayList<>();
//        musicList.add("http://sc1.111ttt.cn/2018/1/03/13/396131232171.mp3");
//        musicList.add("http://sc1.111ttt.cn/2017/1/11/11/304112003137.mp3");
//        musicList.add("http://sc1.111ttt.cn/2017/1/11/11/304112003368.mp3");
//        musicList.add("http://sc1.111ttt.cn/2018/1/03/13/396131155339.mp3");
//        musicList.add("http://sc1.111ttt.cn/2017/1/05/09/298092041086.mp3");
//        musicList.add("http://sc1.111ttt.cn/2017/1/05/09/298092036247.mp3");
//        musicList.add("http://sc1.111ttt.cn/2016/1/12/10/205101753237.mp3");
//        musicList.add("http://sc1.111ttt.cn/2016/1/12/10/205100120468.mp3");
//        musicList.add("http://sc1.111ttt.cn/2016/1/12/10/205101224190.mp3");

        //请求网易云的不知道啥破接口
        new Thread() {
            public void run() {
                HashMap<String, String> apiParams = new HashMap();
                Looper.prepare();
                int num = 1;
                int randNum = 90;
                for (; num < 10; num++) {

                    Random random = new Random();
                    randNum = random.nextInt(99) + 10;

                    apiParams.put("TransCode", "020112");
                    apiParams.put("OpenId", "123456789");
                    apiParams.put("Body[SongListId]", "1419982" + randNum);

                    RequestManager instance = RequestManager.getInstance(UiActivity.this);
                    instance.setBASE_URL(music_url);
                    instance.requestSyn("index/index", 1, apiParams);

                    String jresult = instance.getResult();
                    System.out.println(jresult);
                    if (jresult == null) {
                        continue;
                    }
                    JSONObject result = (JSONObject) instance.getResult("json");
                    try {
                        if (result.get("ErrCode").equals("OK")) {
                            UiActivity.musicListJson = (JSONArray) result.get("Body");
                            if (UiActivity.musicListJson.length() > 10) num = 100;
                            else UiActivity.musicListJson = null;
                            System.out.println(UiActivity.musicListJson);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (UiActivity.musicListJson == null) {
                    Random random = new Random();
                    randNum = random.nextInt(99) + 10;

                    apiParams.put("TransCode", "020112");
                    apiParams.put("OpenId", "123456789");
                    apiParams.put("Body[SongListId]", "141998290");
                    RequestManager instance = RequestManager.getInstance(UiActivity.this);
                    instance.setBASE_URL(music_url);
                    instance.requestSyn("index/index", 1, apiParams);
                    String jresult = instance.getResult();
                    if (jresult == null) {
                        Toast.makeText(UiActivity.this, "数据获取异常！", Toast.LENGTH_SHORT).show();
                        apiStatus = false;
                    } else {
                        JSONObject result = (JSONObject) instance.getResult("json");
                        try {
                            if (result.get("ErrCode").equals("OK")) {
                                UiActivity.musicListJson = (JSONArray) result.get("Body");
                                System.out.println(UiActivity.musicListJson);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Looper.loop();
            }
        }.start();

        while (UiActivity.musicListJson == null && UiActivity.apiStatus) ;

        if (UiActivity.apiStatus) {
            bt_play = findViewById(R.id.bt_play);
            bt_play.setBackgroundResource(R.drawable.ic_pause);
            this.music("play");
            status = 0;
        }


    }

    public void musicLast(View view) {
        this.music("last");
        bt_play.setBackgroundResource(R.drawable.ic_pause);

        if (status == 2) {
            //转转乐
            imageRotate.start();
            ratotionImage(ic_needle, play_ratotion);
        }

        status = 0;
    }


    public void musicPlay(View view) {
        //status  0表示首次播放，1表示(没用了)，2表示等待播放
        if (status == 1) {

            bt_play.setBackgroundResource(R.drawable.ic_pause);
            this.music("play");
            status = 0;

            ratotionImage(ic_needle, play_ratotion);
        } else if (status == 0) {

            bt_play.setBackgroundResource(R.drawable.ic_play);
            this.music("pause");

            //停止唱盘旋转跳跃
            imageRotate.cancel();

            status = 2;
            ratotionImage(ic_needle, pause_ratotion);

        } else if (status == 2) {

            bt_play.setBackgroundResource(R.drawable.ic_pause);
            this.music("play");

            //转转乐
            imageRotate.start();

            status = 0;

            ratotionImage(ic_needle, play_ratotion);
        }
    }

    public void musicNext(View view) {
        this.music("next");
        bt_play.setBackgroundResource(R.drawable.ic_pause);

        if (status == 2) {
            //转转乐
            imageRotate.start();
            ratotionImage(ic_needle, play_ratotion);
        }

        status = 0;
    }

    public void music(String type) {

        try {
            Random random = new Random();
            int xiaobiao = random.nextInt(UiActivity.musicListJson.length());

            //配置音乐信息
            JSONObject musicInfo = ((JSONObject) (UiActivity.musicListJson.get(xiaobiao)));
            String url = (String) musicInfo.get("url");


            if (status != 2 && (type == "play" || type == "last" || type == "next")) {
                musicInfo(musicInfo, this);
            }

            try {
                if (type == "play") {
                    if (status == 2) {
                        mp3.start();
                        return;
                    }
                    url = (String) musicInfo.get("url");
                } else if (type == "last" || type == "next") {
                    mp3.stop();
                    mp3 = new MediaPlayer();
                    seekbarRotate.cancel();
                    url = (String) musicInfo.get("url");
                } else if (type == "pause") {
                    mp3.pause();
                    return;
                }
                mp3.setDataSource(url);
//
//                System.out.println(mp3.getDuration());

                mp3.prepare();
                mp3.start();
                System.out.println(mp3.getDuration());
                int total_time = mp3.getDuration();
                listen_jindutiao_sb.setMax(total_time);
                seekbarRotate = new SeekbarRotate(total_time, 1000);
                seekbarRotate.start();
                String alltime = millisToStringShort(mp3.getDuration());
                listen_length_tv.setText(alltime);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 毫秒转时分秒
     *
     * @param millis
     * @return
     */
    public static String millisToStringShort(long millis) {
        StringBuilder strBuilder = new StringBuilder();
        long temp = millis;
        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;
        if (temp / hper > 0) {
            strBuilder.append(temp / hper).append(":");
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            long minute = temp / mper;
            if (minute < 10) {
                strBuilder.append("0" + minute).append(":");
            } else {
                strBuilder.append(minute).append(":");
            }
        } else {
            strBuilder.append("00:");
        }
        temp = temp % mper;
        if (temp / sper > 0) {
            long second = temp / sper;
            if (second < 10) {
                strBuilder.append("0" + second);
            } else {
                strBuilder.append(second);
            }
        } else {
            strBuilder.append("00");
        }
        return strBuilder.toString();
    }

    static Bitmap compressBitmap = null;
    static String musicInfoUrl = null;

    public static void musicInfo(JSONObject musicInfo, UiActivity uiActivity) {
        try {
            music_name.setText((String) musicInfo.get("title"));
            music_singer.setText((String) musicInfo.get("author"));

            String alltime = millisToStringShort(mp3.getDuration());
            listen_length_tv.setText(alltime);

            //压缩背景
            UiActivity.musicInfoUrl = (String) musicInfo.get("pic");
            new Thread() {
                @Override
                public void run() {
                    UiActivity.compressBitmap = ImageTools.UrlToCompressBitmap(UiActivity.musicInfoUrl);
                }
            }.start();
            while (UiActivity.compressBitmap == null) ;

            Glide.with(uiActivity).load(UiActivity.compressBitmap)
                    .apply(bitmapTransform(new BlurTransformation(80)))
                    .into((ImageView) uiActivity.findViewById(R.id.image_bg));

            returnBitMap((String) musicInfo.get("pic"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void returnBitMap(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    //网络图片转bitmap
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    UiActivity.urlBitMap = BitmapFactory.decodeStream(is);

                    Bitmap firstImage;
                    //获取一个图片转bitmap，并设置大小
                    firstImage = ImageTools.imageScale(UiActivity.urlBitMap, 200, 200);
                    //将图片变圆
                    firstImage = ImageTools.toOvalBitmap(firstImage, 1500);
                    //使用一张黑底的画布，将图片放上去
                    firstImage = ImageTools.blackBottomBitmap(firstImage, 300, 300);
                    //再把返回的带黑布的图片变圆
                    firstImage = ImageTools.toOvalBitmap(firstImage, 1500);
                    ic_circle.setImageBitmap(firstImage);

                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if (mp3.isPlaying()) {
            mp3.stop();//停止播放
            mp3.release();//释放资源
            mp3 = new MediaPlayer();
        }
        super.onDestroy();
    }

    /**
     * 控制指针角度
     *
     * @param imageView
     * @param ratotion
     */
    public void ratotionImage(ImageView imageView, int ratotion) {
        imageView.setPivotX(imageView.getWidth() / 2);
        imageView.setPivotY(0);//支点在图片中心
        imageView.setRotation(ratotion);
    }
}
