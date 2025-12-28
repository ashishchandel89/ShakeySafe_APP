package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class first extends Activity {
    ImageView i1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        i1=findViewById(R.id.i1);
        Animation obj = AnimationUtils.loadAnimation(this, R.anim.zoom3);
        i1.startAnimation(obj);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent obj2=new Intent(first.this,shaking.class);
                startActivity(obj2);

                finish();
            }
        },3000);


    }
    }




