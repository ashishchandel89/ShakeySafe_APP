package com.example.app1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class shaking1 extends Activity implements SensorEventListener {

    Sensor s1;
    SensorManager sm1;
    ImageView i1;
    Double lat1,long1;
    SharedPreferences sp1;
    LocationManager lm1;
    String s2;
    Boolean bool=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shaking1);


        sm1 = (SensorManager) getSystemService(SENSOR_SERVICE);
        s1 = sm1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm1.registerListener(this, s1, SensorManager.SENSOR_DELAY_NORMAL);

        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Alert!");
        obj.setMessage("Now put your phone in your pocket without moving, or your own movement will trigger the alert!");
        obj.setCancelable(false);
        obj.setPositiveButton("OK", null);
        obj.show();

        s2=getIntent().getStringExtra("text");
        sp1=getSharedPreferences("abc",0);
        SharedPreferences.Editor ed1=sp1.edit();
        ed1.putString("xyz",s2);
        ed1.apply();

        s2=sp1.getString("xyz","phone_no");
        Log.e("s2",s2);
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
        },1200);
    }

        long last_update=0;
        float last_x, last_y, last_z;
        int temp = 350;
        long current_time, difference;
        float speed;
        boolean alreadyTriggered=false;         //iska matlab pahle already ho chuka hai. alreadytrigrred

        @Override
        public void onSensorChanged (SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                current_time = System.currentTimeMillis();
                if ((current_time - last_update) > 100) {

                    difference = current_time - last_update;
                    last_update = current_time;
                    speed = Math.abs((x + y + z - last_x - last_y - last_z) / difference * 1000);
                    if (speed > temp && !alreadyTriggered) {        // agr alreadytrigerred false hai to ye method chalne ke bad ye alreadytriggered true ho jaye matlab ab chal gya hai.
                        alreadyTriggered=true;

                        //-------image and ring alert-------

                        i1 = findViewById(R.id.i1);
                        i1.setVisibility(View.VISIBLE);
                        Animation obj2 = AnimationUtils.loadAnimation(this, R.anim.zoom2);
                        i1.startAnimation(obj2);
                        Intent obj = new Intent(this, service.class);
                        startService(obj);

                        //---------------GOOGLE MAP-----------

                        lm1=(LocationManager) getSystemService(LOCATION_SERVICE);


                        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                        }else{
                            Location obj4=lm1.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(obj4!=null){
                                 lat1=obj4.getLatitude();
                                 long1=obj4.getLongitude();

                                String maplink="https://maps.google.com/?q="+lat1+","+long1;
                                String message="Emergency! Here's my location: \n"+maplink;


                                // ----------------SMS----------------


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                         if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(shaking1.this,new String[]{Manifest.permission.SEND_SMS},2);
                            return;
                        }
                                        SmsManager obj2= SmsManager.getDefault();
                                obj2.sendTextMessage(""+s2,null,message,null,null);


                                    }
                                },5000);
                            }else {
                                Toast.makeText(this, "location_not_found", Toast.LENGTH_SHORT).show();
                            }
                        }


                        //-------------------CALL-------------------



                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {

                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},3);
                return;
                }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                        Intent obj3 = new Intent(Intent.ACTION_CALL);
                                        obj3.setData(Uri.parse("tel:"+s2));
                                        startActivity(obj3);

                                    finish();
                                }
                            },4000);


                        }
                        last_x = x;
                        last_y = y;
                        last_z = z;
                    }

                }
            }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
