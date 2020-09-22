package com.example.myfirstgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import static com.example.myfirstgame.GameView.dHeight;
import static com.example.myfirstgame.GameView.dWidth;

public class Plane {
    Bitmap plane,plane1;
    Random random;
    int planeX,planeY,planeWidth,velocity,planeHeight,downvelocity,velocityY;
    boolean destroyed;

    public Plane(Context context)
    {
        plane= BitmapFactory.decodeResource(context.getResources(),R.drawable.mosq);
        plane1= BitmapFactory.decodeResource(context.getResources(),R.drawable.moqx);
        random=new Random();
        planeWidth=plane.getWidth();
        planeHeight=plane.getHeight();
        reset();
    }
    public void reset()
    {   destroyed=false;
        planeX= dWidth+random.nextInt(1200);
        planeY=random.nextInt(dHeight-150);
        velocity=3+random.nextInt(10);
        velocityY=-10+random.nextInt(24);
        downvelocity=30;
    }
    public void update()
    {   if(destroyed==true) {
            planeY+=downvelocity;
            if(planeY>dHeight)
                reset();
        }
        else
        {
            planeX -= velocity;
            planeY+=velocityY;
            if (planeX < -planeWidth) {
                reset();
            }
            if(planeY<-planeHeight||planeY>dHeight)
            {
                reset();
            }
        }
    }
    public boolean isCollison(float x,float y)
    {
        if(x>=planeX && y>=planeY && x<=planeX+planeWidth && y<=planeY+planeHeight)
        {
            return true;}
        return  false;
    }
}
