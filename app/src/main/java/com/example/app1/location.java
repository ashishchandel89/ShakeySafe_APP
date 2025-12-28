package com.example.app1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class location extends AppCompatActivity {
    LocationManager lm1;
    String uri;
    Double lat1,long1;
    String maplink="https://maps.google.com/?q="+lat1+","+long1;
    String message="Emergency! Here's my location: \n"+maplink;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

    }

    public void f1(View v){
        lm1=(LocationManager) getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            Location obj=lm1.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(obj!=null){
                Double lat1=obj.getLatitude();
                Double long1=obj.getLongitude();
                Toast.makeText(this, "latitude:"+lat1+"longitude"+long1, Toast.LENGTH_SHORT).show();
                uri="geo:"+lat1+","+long1+"?q="+lat1+","+long1+"()My+Location)";
                Intent obj2=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                obj2.setPackage("com.google.android.apps.maps");
                startActivity(obj2);
            }
            else{

                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
            String num="9729076090";
            Intent obj3=new Intent(Intent.ACTION_VIEW);
            obj3.setData(Uri.parse("http://wa.me/"+num+"?text="+Uri.encode(message)));

            startActivity(obj3);

        }

    }
}
