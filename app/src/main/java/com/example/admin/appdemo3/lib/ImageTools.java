package com.example.admin.appdemo3.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageTools {


    /**
     * 生成圆形图片
     *
     * @param bitmap
     * @param pix
     * @return
     */
    public static Bitmap toOvalBitmap(Bitmap bitmap, float pix) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getHeight(),
                    bitmap.getWidth(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
            RectF rectF = new RectF(rect);
            float roundPx = pix;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            int color = 0xff424242;
            paint.setColor(color);
            canvas.drawOval(rectF, paint);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     *   * 合并两张bitmap为一张
     *   * @param background
     *   * @param foreground
     *   * @return Bitmap
     *  
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2, null);
        canvas.save();
        canvas.restore();
        return newmap;
    }


    public static Bitmap blackBottomBitmap(Bitmap foreground, int bgWidth, int bgHeight) {
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawColor(Color.rgb(0, 0, 0));
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2, null);
        canvas.save();
        canvas.restore();
        return newmap;
    }

    /**
     * 调整图片大小
     *
     * @param bitmap 源
     * @param dst_w  输出宽度
     * @param dst_h  输出高度
     * @return
     */
    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
        return dstbmp;
    }

    /**
     * 压缩图片
     *
     * @param bitmap    被压缩的图片
     * @param sizeLimit 大小限制
     * @return 压缩后的图片
     */
    private static Bitmap compressBitmap(Bitmap bitmap, long sizeLimit) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 20;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

//        // 循环判断压缩后图片是否超过限制大小
////        while (baos.toByteArray().length / 1024 > sizeLimit) {
////            // 清空baos
////            baos.reset();
////            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
////            if (quality <= 0) {
////                quality = 0;
////            } else
////                quality -= 10;
////        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        Bitmap newBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null);

        return newBitmap;
    }

    /**
     * 网络图片转压缩bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap UrlToCompressBitmap(String url) {
        InputStream is = null;
        try {
            URL imageurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressBitmap(BitmapFactory.decodeStream(is), 10);
    }

    /**
     * 网络图片转bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap UrlToBitmap(String url) {
        InputStream is = null;
        try {
            URL imageurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

}
