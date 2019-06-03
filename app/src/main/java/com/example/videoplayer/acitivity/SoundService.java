package com.example.videoplayer.acitivity;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  �������ֵ�service	 
 * @author:	Vongvia  ��ӭ��λͯЬ������ ��441256563
 * @date:	2015.11.17
 * @version	V1.0   
 */
import com.example.videoplayer.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
 
public class SoundService extends Service {
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.playing);
       
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
     
        stopSelf();
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean playing = intent.getBooleanExtra("playing", false);
        if (playing) {
            mp.start();
        } else {
        	onDestroy();
        }
        return super.onStartCommand(intent, flags, startId);
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
}
 