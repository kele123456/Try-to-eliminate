package com.example.videoplayer;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private LinearLayout controllerLayout;
    private ImageView play_controller_img,screen_img;
    private TextView current_text_tv,time_total_tv;
    private SeekBar play_seek,volume_seek;
    public static final int UPDATE_UI=1;
    private AudioManager mAudioManager;
    //定义音量控制
    /**
     * 定义videoview
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  mAudioManager=(AudioManager) getSystemService(AUDIO_SERVICE);//绑定，获取系统的音频服务
        initUI();
        setPlayerEvent();

        String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.mp4";
        /**
         * 本地视频播放
         */
        videoView.setVideoPath(path);
        videoView.start();
        UIHandler.sendEmptyMessage(UPDATE_UI);

        /**
         *使MediaController控制视频播放
         */
        // MediaController controller=new MediaController(this);
        /**
         * 设置ViedoView与MediaController建立关联
         */
        // videoView.setMediaController(controller);
        /**
         * 设置MedioController与VideoView建立关联
         */
        // controller.setMediaPlayer(videoView);
    }
    private void updateTextViewWithTimeFormat(TextView textview, int millisecond)
    {
        /**
         * 时间格式换化方法
         */

        int second=millisecond/1000;
        int hh=second/3600;
        int mm=second/3600/60;
        int ss=second%60;
        String str=null;
        if(hh!=0)
        {
            //format("")格式化字符变量
            str=String.format("%02d:%02d:%02d",hh,mm,ss);
        }
        else{
            str=String.format("%02d:%02d",mm,ss);
        }
        textview.setText(str);
    }
    private Handler UIHandler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==UPDATE_UI){
                //获取视频当前的播放时间
                int currentPosition=videoView.getCurrentPosition();
                //获取视频播放的总时间
                int totalduration=videoView.getDuration();
                //格式化视频播放时间
                updateTextViewWithTimeFormat(current_text_tv, currentPosition);
                updateTextViewWithTimeFormat(time_total_tv,totalduration);
                play_seek.setMax(totalduration);
                play_seek.setProgress(currentPosition);
                UIHandler.sendEmptyMessageDelayed(UPDATE_UI,500);
                //每隔0.5秒就更新UI
            }
        }
    };
    protected void onPause() {
        super.onPause();
        UIHandler.removeMessages(UPDATE_UI);
    }
    @Override
    protected void onDestroy() {
        // TODO 自动生成的方法存根
        super.onDestroy();
    }
    private void setPlayerEvent() {
        /**
         * 控制视频的播放和暂停的监听
         */
        play_controller_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if(videoView.isPlaying()){
                    play_controller_img.setImageResource(R.drawable.pause_btn_style);
                    //暂停播放
                    videoView.pause();
                    UIHandler.removeMessages(UPDATE_UI);

                }
                else
                {
                    play_controller_img.setImageResource(R.drawable.pause_btn_style);
                    //继续播放
                    videoView.start();
                    UIHandler.sendEmptyMessage(UPDATE_UI);
                }
            }
        });
        play_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO 自动生成的方法存根
                //根据当前进度刷新TextView的值
                updateTextViewWithTimeFormat(current_text_tv,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO 自动生成的方法存根
                UIHandler.removeMessages(UPDATE_UI);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO 自动生成的方法存根
                int progress=seekBar.getProgress();
                //令视频播放季度遵循seekBar停止拖动的这一刻进度
                videoView.seekTo(progress);
                //让进度重新显示
                UIHandler.sendEmptyMessage(UPDATE_UI);
                //
            }


        });


        volume_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO 自动生成的方法存根
                //设置当前设备的音量
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO 自动生成的方法存根

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO 自动生成的方法存根

            }


        });

    }
    /**
     * 初始化UI布局
     */
    private void initUI() {
        // TODO 自动生成的方法存根
        videoView=(VideoView) findViewById(R.id.videoView);
        controllerLayout=(LinearLayout) findViewById(R.id.controllerbar_layout);
        play_controller_img=(ImageView) findViewById(R.id.pause_img);
        screen_img=(ImageView) findViewById(R.id.screen_img);
        current_text_tv=(TextView) findViewById(R.id.time_current_tv);
        time_total_tv=(TextView) findViewById(R.id.time_total_tv);
        play_seek=(SeekBar) findViewById(R.id.play_seek);
        volume_seek=(SeekBar) findViewById(R.id.volume_seek);
        /**
         * 当前设备的最大音量
         */

        int streamMaxVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        /**
         * 获取设备的最大音量
         */
        int streamVolum=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume_seek.setMax(streamMaxVolume);
        volume_seek.setProgress(streamVolum);
    }
    /**
     *监听屏幕方向的改变
     */



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO 自动生成的方法存根
        super.onConfigurationChanged(newConfig);
    }
}
