package com.example.myfirstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity2 extends Activity {
    SharedPreferences preferences;
    String Sharedpref="LEVEL1";
    ImageView img;
    boolean ismute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences(Sharedpref,MODE_PRIVATE);
        ismute=preferences.getBoolean("muted",false);
        img=(ImageView)findViewById(R.id.vlctrl);
        if(ismute)
        {
            img.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }
        else
        {
            img.setImageResource(R.drawable.ic_baseline_volume_up_24);
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
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("muted",ismute);
                editor.apply();
            }
        });
    }
    public void Gms(View view)
    {
        Intent intent=new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}