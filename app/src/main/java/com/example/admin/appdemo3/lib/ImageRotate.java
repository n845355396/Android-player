package com.example.admin.appdemo3.lib;

import android.os.CountDownTimer;
import android.widget.ImageView;

/**
 * 自动旋转图片
 */
public class ImageRotate extends CountDownTimer {
    private final ImageView imageView;

    public ImageRotate(long millisInFuture, long countDownInterval, ImageView imageView) {
        super(millisInFuture, countDownInterval);
        this.imageView = imageView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }
    int i = 1;

    @Override
    public void onFinish() {
        imageView.setPivotX(imageView.getWidth() / 2);
        imageView.setPivotY(imageView.getHeight() / 2);//图片中心为支点  
        imageView.setRotation(i);
        i = i + 1;
        if (i == 361) {
            i = 1;
        }
        start();
    }
}
