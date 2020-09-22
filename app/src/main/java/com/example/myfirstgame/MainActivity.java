package com.example.myfirstgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {
    private GameView gameView;
    long backpressedtime;
    private Toast backToast;
    private SoundPool soundPool;
    private int sound;
    SharedPreferences prefs;
    String Sharedpref="LEVEL1";
    boolean ismute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gameView=new GameView(this);
        setContentView(gameView);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool=new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }
        else
        {
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        sound=soundPool.load(this,R.raw.game_start,1);
        prefs=getSharedPreferences(Sharedpref,MODE_PRIVATE);
        ismute=prefs.getBoolean("muted",false);
        if(!ismute) {
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    soundPool.play(sound,1,1,1,0,1);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
       //
        if(backpressedtime+2000>System.currentTimeMillis())
        {backToast.cancel();
            super.onBackPressed();
            return;
        }
        else
        {
            backToast=Toast.makeText(getBaseContext(),"Press back agin to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressedtime=System.currentTimeMillis();

    }
}