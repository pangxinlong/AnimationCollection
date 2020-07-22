package com.example.pangxinlong.animationcollection.carddrag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pangxinlong.animationcollection.R;

/**
 * Date: 2020-02-11
 * author: pangxinlong
 * Description:
 */
public class CardDragActivity extends Activity {

    private CustomCardView customCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_drag);
        customCardView = findViewById(R.id.card_view);

        init();
    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            customCardView.addCard(imageView);
        }

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CardDragActivity.class);
        context.startActivity(intent);
    }
}
