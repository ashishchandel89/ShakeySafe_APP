package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class number extends Activity {
    EditText et1;
    CheckBox c1;
    Boolean bool=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number);
        et1=findViewById(R.id.et1);
        c1=findViewById(R.id.c1);

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
        String s1=et1.getText().toString().trim();
        if(s1.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number.", Toast.LENGTH_SHORT).show();
        }
        else if(s1.length() != 10 || !s1.matches("\\d{10}")) {
            Toast.makeText(this, "Please enter a valid 10-digit phone number.", Toast.LENGTH_SHORT).show();
        }
        else if(!c1.isChecked()) {
            Toast.makeText(this, "Please accept the terms to continue.", Toast.LENGTH_SHORT).show();
        }
    else{
        Intent obj=new Intent(this,shaking1.class);
        obj.putExtra("text",s1);
        startActivity(obj);
    }

    }
    public void f2(View v){
    Intent obj=new Intent(this,shaking.class);
    startActivity(obj);
    }
}
