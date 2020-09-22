package com.example.myfirstgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity3 extends Activity {
    TextView scoreDis;
    TextView highscore;
    String score;
    SharedPreferences prefs;
    String Sharedpref="LEVEL1";
    int Highscore;
    private SoundPool soundPool;
    private int sound;
    //SharedPreferences preferences;
  //  String Sharedpref="LEVEL1";
    ImageView img;
    boolean ismute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        score=getIntent().getExtras().get("score").toString();
        setContentView(R.layout.activity_main2);
        scoreDis=(TextView)findViewById(R.id.scoreview);
        scoreDis.setText("Your Score : "+score);
        prefs=getSharedPreferences(Sharedpref,MODE_PRIVATE);
        Highscore=prefs.getInt("HighScore",0);
        highscore=(TextView)findViewById(R.id.Highsco);
        highscore.setText(String.format("High Score : %d", Highscore));
        prefs=getSharedPreferences(Sharedpref,MODE_PRIVATE);
        ismute=prefs.getBoolean("muted",false);
        img=(ImageView)findViewById(R.id.vlctrl);
        if(ismute)
        {
            img.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }
        else
        {
            img.setImageResource(R.drawable.ic_baseline_volume_up_24);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool=new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }
        else
        {
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        sound=soundPool.load(this,R.raw.game_end,1);

        if(!ismute) {
           soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
               @Override
               public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                   soundPool.play(sound,1,1,1,0,1);
               }
           });
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ismute=!ismute;
                if(ismute)
                {
                    img.setImageResource(R.drawable.ic_baseline_volume_off_24);
                }
                else
                {
                    img.setImageResource(R.drawable.ic_baseline_volume_up_24);
                }
                SharedPreferences.Editor editor=prefs.edit();
                editor.putBoolean("muted",ismute);
                editor.apply();
            }
        });
    }
    public void startover(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}