package com.example.app1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class service extends Service {
MediaPlayer m1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        m1 = MediaPlayer.create(this, R.raw.alert); // alarm_sound.mp3 in res/raw
        m1.setLooping(true);
        m1.start();
        return START_NOT_STICKY;
    }
    public void onDestroy() {
        if (m1 != null) {
            m1.stop();
            m1.release();
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
