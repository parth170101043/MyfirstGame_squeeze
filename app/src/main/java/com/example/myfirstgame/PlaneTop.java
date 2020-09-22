package com.example.myfirstgame;

import android.content.Context;
import android.graphics.BitmapFactory;

import static com.example.myfirstgame.GameView.dHeight;
import static com.example.myfirstgame.GameView.dWidth;

public class PlaneTop extends Plane {
    public PlaneTop(Context context) {
        super(context);
//        plane= BitmapFactory.decodeResource(context.getResources(),R.drawable.mosq);
//        plane1= BitmapFactory.decodeResource(context.getResources(),R.drawable.moqx);
    }

    @Override
    public void reset() {
        planeX= 1000+random.nextInt(dWidth);
        planeY=-1500+random.nextInt(400);
        velocity=3+random.nextInt(10);
        velocityY=5+random.nextInt(10);
        downvelocity=30;
        destroyed=false;

    }

    @Override
    public void update() {
        if(destroyed==true) {
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
            if((planeY<-planeHeight && velocityY<0)||planeY>dHeight)
            {
                reset();
            }
        }
    }
}
