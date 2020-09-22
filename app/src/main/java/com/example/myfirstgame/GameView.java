package com.example.myfirstgame;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
//import android.os.Handler;
import java.util.ArrayList;
import java.util.Random;
//import java.util.logging.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

public class GameView extends View {
      Bitmap door1;
      Bitmap background;
      private SharedPreferences prefs;
      public ArrayList<Plane> Planes;

      public ArrayList<PlaneTop> Planestop;
      public ArrayList<PlaneDown> Planesdown;
      String Sharedpref="LEVEL1";
      private SoundPool soundPool;
      private int sound;
      Rect rect,rect1,rectime;
      Point point;
      int kill;
      Paint scorepaint,lifepaint,timepaint;
      int score;
      Bitmap gamelife;
      int lifewidth,lifecount,maxlife;
      Bitmap blood;
      static int dWidth,dHeight;
      Random random;
      private Handler handler;
      Runnable runnable;
      final long UPDATE_MILLIS=25;
        int isblood;
        float bloodx,bloody;
        boolean isgmaeover,ismute;

        Vibrator v;
        int countr,minutes,seconds;
    // int planeX,planeY,velocity,planeFrame,planeWidth;
    public GameView(Context context) {
        super(context);
        Display display=((Activity)getContext()).getWindowManager().getDefaultDisplay();

        background=BitmapFactory.decodeResource(getResources(),R.drawable.g_play);
        blood=BitmapFactory.decodeResource(getResources(),R.drawable.bloods);
       // bgcounter=-1;
        door1=BitmapFactory.decodeResource(getResources(),R.drawable.the_home);

        point=new Point();
        display.getSize(point);
        kill=0;
        score=0;
        dWidth=point.x;
        dHeight=point.y;
        rect=new Rect(0,0,dWidth,dHeight);
        rect1=new Rect(0,200,200,dHeight);
        Planes=new ArrayList<>();
        Planestop=new ArrayList<>();
        Planesdown=new ArrayList<>();
        isblood=0;


        for(int i=0;i<11;i++)
        {
            Plane plane=new Plane(context);
            Planes.add(plane);
            PlaneTop planeTop=new PlaneTop(context);
            Planestop.add(planeTop);

        }
        for(int i=0;i<6;i++)
        {
            PlaneDown planedown=new PlaneDown(context);
            Planesdown.add(planedown);
        }
        scorepaint=new Paint();
        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(65);
        scorepaint.setTypeface(Typeface.DEFAULT);
        scorepaint.setAntiAlias(true);
       // gamelife=new Bitmap[10];
        lifepaint=new Paint();
        lifepaint.setColor(Color.WHITE);
        lifepaint.setTextSize(80);
        lifepaint.setTypeface(Typeface.DEFAULT);
        lifepaint.setAntiAlias(true);
        timepaint=new Paint();
        timepaint.setColor(Color.WHITE);
        timepaint.setTextSize(65);
        timepaint.setTypeface(Typeface.DEFAULT);
        timepaint.setAntiAlias(true);
        gamelife=BitmapFactory.decodeResource(getResources(),R.drawable.life);
        lifewidth=gamelife.getWidth();

        maxlife=50;
        lifecount=0;

       handler=new Handler();
       runnable=new Runnable() {
           @Override
           public void run() {
               invalidate();
           }
       };
       //handler.post();
        isgmaeover=false;
        prefs=context.getSharedPreferences(Sharedpref,Context.MODE_PRIVATE);
        ismute=prefs.getBoolean("muted",false);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool=new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }
        else
        {
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }


        sound=soundPool.load(context,R.raw.mos_killed,1);

        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        countr=0;
        minutes=1;
        seconds=30;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        countr+=25;
        if(countr==1000)
        {countr=0;
            seconds--;
            if(seconds==-1)
            {

                if(minutes==0)
                    seconds=0;
                else
                {
                    seconds=59;
                    minutes--;
                }

            }
        }
        int totallife=maxlife-lifecount;
        int sec2=seconds%10;
        int sec1=seconds/10;
        String timestr=minutes+":"+sec1+sec2;
        if(totallife<0)
            totallife=0;
        canvas.drawColor(Color.BLUE);
       // bgcounter++;
        canvas.drawBitmap(background,null,rect,null);

        int texheight=dHeight/10;
        canvas.drawBitmap(door1,null,rect1,null);
        canvas.drawText("Score : "+score,20,texheight,scorepaint);
        canvas.drawBitmap(gamelife,dWidth-dWidth/5,10,null);
        canvas.drawText(""+totallife,dWidth-dWidth/8,texheight,lifepaint);
        //canvas.drawText(timestr,rectime,timepaint);
        canvas.drawText(timestr,dWidth/2-70,texheight,timepaint);

        if(isblood>0)
        {
            canvas.drawBitmap(blood,bloodx,bloody,null);
            isblood--;
        }
         for(int i=0;i<11;i++)
        {
            if(Planes.get(i).destroyed)
            canvas.drawBitmap(Planes.get(i).plane1,Planes.get(i).planeX,Planes.get(i).planeY,null);
            else
            {
                if(Planes.get(i).planeX>=-25 && Planes.get(i).planeX<160 && Planes.get(i).planeY>=160 && Planes.get(i).planeY<dWidth)
                {
                    if(!ismute)
                    v.vibrate(40);
                    Planes.get(i).reset();
                    lifecount++;
                }
                else
                canvas.drawBitmap(Planes.get(i).plane,Planes.get(i).planeX,Planes.get(i).planeY,null);
            }
            Planes.get(i).update();

            if(Planestop.get(i).destroyed)
                canvas.drawBitmap(Planestop.get(i).plane1,Planestop.get(i).planeX,Planestop.get(i).planeY,null);
            else
            {
                if(Planestop.get(i).planeX>=-25 &&Planestop.get(i).planeX<160 && Planestop.get(i).planeY>=160 && Planestop.get(i).planeY<dWidth)
                {
                    //soundPool.play(sound[2],1,1,1,0,1);
                    if(!ismute)
                    v.vibrate(40);
                    Planestop.get(i).reset();
                    lifecount++;
                }
                else
                    canvas.drawBitmap(Planestop.get(i).plane,Planestop.get(i).planeX,Planestop.get(i).planeY,null);
            }
            Planestop.get(i).update();
            

//            if(Planes1.get(i).destroyed)
//            canvas.drawBitmap(Planes1.get(i).plane1,Planes1.get(i).planeX,Planes1.get(i).planeY,null);
//            else
//                canvas.drawBitmap(Planes1.get(i).plane,Planes1.get(i).planeX,Planes1.get(i).planeY,null);
//            Planes1.get(i).update();
        }
         for(int i=0;i<6;i++)
         {
             if(Planesdown.get(i).destroyed)
                 canvas.drawBitmap(Planesdown.get(i).plane1,Planesdown.get(i).planeX,Planesdown.get(i).planeY,null);
             else
             {
                 if(Planesdown.get(i).planeX>=-25 && Planesdown.get(i).planeX<160 && Planesdown.get(i).planeY>=160 && Planesdown.get(i).planeY<dWidth) {
                     //  soundPool.play(sound[2],1,1,1,0,1);
                     if (!ismute)
                         v.vibrate(40);
                     Planesdown.get(i).reset();
                     lifecount++;
                 }
                 else
                     canvas.drawBitmap(Planesdown.get(i).plane,Planesdown.get(i).planeX,Planesdown.get(i).planeY,null);
             }
             Planesdown.get(i).update();
         }
         if(isgmaeover)
             gameover();
        if(lifecount>=maxlife||(seconds==0 && minutes==0))
        {
           isgmaeover=true;
        }
        handler.postDelayed(runnable,UPDATE_MILLIS);
       // canvas.drawBitmap(background,0,200,null);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float touchX=event.getX();
        float touchY=event.getY();
        boolean killed=false;

           for(int i=0;i<11;i++)
               if (Planes.get(i).isCollison(touchX, touchY)==true && !Planes.get(i).destroyed) {
                   Planes.get(i).destroyed=true;
                   kill++;
                   score++;
                   bloodx=touchX-25;
                   bloody=touchY+20;
                   if(!ismute)
                   soundPool.play(sound,1,1,1,0,2);

                   killed=true;
                   isblood=6;
                   break;
               }
           else if (Planestop.get(i).isCollison(touchX, touchY)==true&& !Planestop.get(i).destroyed) {
                   Planestop.get(i).destroyed=true;
                   kill++;
                   score++;
                   killed=true;
                   if(!ismute)
                   soundPool.play(sound,1,1,1,0,2);

                   isblood=6;
                   bloodx=touchX-25;
                   bloody=touchY+20;
                   break;
                }
           for(int i=0;i<6;i++)
           {
               if(!killed)
               {
                   if (Planesdown.get(i).isCollison(touchX, touchY)==true && !Planesdown.get(i).destroyed) {
                       Planesdown.get(i).destroyed=true;
                       kill++;
                       score++;
                       if(!ismute)
                       soundPool.play(sound,1,1,1,0,2);

                       bloodx=touchX-25;
                       bloody=touchY+20;
                       isblood=6;
                       killed=true;
                       break;

                   }
               }
           }


            if(killed)
            if(kill%4==0)
            {
                for(int i=3;i<8;i++)
                {
                    Planestop.get(i).velocityY*=-1;
                }
                for(int i=0;i<=2;i++)
                {
                    Planes.get(i).velocityY*=-1;
                }
            }
            else if(kill%3==0)
            {
                for(int i=6;i<10;i++)
                {
                    Planes.get(i).velocityY*=-1;
                }
                for(int i=0;i<=2;i++)
                {
                    Planesdown.get(i).velocityY*=-1;
                }
            }
        //soundPool.play(sound[0],1,1,1,0,3);
        return true;
    }

    public void  gameover()
    {
        if(prefs.getInt("HighScore",0)<score)
        {
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("HighScore",score);
            editor.apply();
        }

        soundPool.pause(sound);

        Intent intent=new Intent(getContext(),MainActivity3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("score",score);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();


    }
}
