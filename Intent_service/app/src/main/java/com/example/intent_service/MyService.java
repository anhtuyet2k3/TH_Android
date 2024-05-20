package com.example.intent_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;

public class MyService extends Service {
    // Khai báo đối tượng mà service quản lý
    MediaPlayer mymusic;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // hàm dùng khởi tạo đối tượng mà service quản lý
    @Override
    public void onCreate() {
        super.onCreate();
        mymusic = MediaPlayer.create(MyService.this, R.raw.trentinhbanduoitinhyeu);
        // setting lặp đi lại lại
        mymusic.setLooping(true);
    }
    // Hàm dùng để khởi động Service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mymusic.isPlaying()){
            mymusic.pause();
        }
        else{
            mymusic.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    // Hàm để dừng đối tượng mà Service quản lý

    @Override
    public void onDestroy() {
        super.onDestroy();
        mymusic.stop();
    }
}