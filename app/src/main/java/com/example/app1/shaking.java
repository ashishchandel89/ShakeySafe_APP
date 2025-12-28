package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;

public class shaking extends Activity {
    Button b1;
    Boolean bool=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shaking);
        b1 = findViewById(R.id.b1);


        Animation obj = AnimationUtils.loadAnimation(this, R.anim.zoom);
        b1.startAnimation(obj);
    }

    public void onBackPressed(){
        if(bool){
            super.onBackPressed();
            return;
        }
        this.bool=true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bool=false;
            }
        },2000);
    }
    public void f1(View v){
        Intent obj=new Intent(this,number.class);
        startActivity(obj);
    }
}
