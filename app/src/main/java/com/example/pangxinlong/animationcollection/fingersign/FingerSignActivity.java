package com.example.pangxinlong.animationcollection.fingersign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.pangxinlong.animationcollection.BaseActivity;
import com.example.pangxinlong.animationcollection.R;
import com.example.pangxinlong.animationcollection.carddrag.CardDragActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Date: 2020-03-02
 * author: pangxinlong
 * Description:
 */
public class FingerSignActivity extends BaseActivity {


    FingerSignView fingerSignView;

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_finger_sign);
        fingerSignView = (FingerSignView) findViewById(R.id.fingerSignView);
        imageView = (ImageView) findViewById(R.id.iv_bitmap);
    }

    public void save(View view) {
        Bitmap bitmap = fingerSignView.getBitmap();
        String str = imgToBase64(bitmap);
        Log.e("str", str);
        imageView.setImageBitmap(bitmap);
    }

    public void clear(View view) {
        fingerSignView.clear();
    }


    public String imgToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            //bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return "data:image/jpeg;base64," + Base64.encodeToString(imgBytes, Base64.DEFAULT).trim();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imgToBase64_2(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        //Base64算法加密，当字符串过长（一般超过76）时会自动在中间加一个换行符，字符串最后也会加一个换行符。
        // 导致和其他模块对接时结果不一致。所以不能用默认Base64.DEFAULT，而是Base64.NO_WRAP不换行
        return "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FingerSignActivity.class);
        context.startActivity(intent);
    }
}
