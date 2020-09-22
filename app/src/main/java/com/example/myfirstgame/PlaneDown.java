package com.example.myfirstgame;

import android.content.Context;

import static com.example.myfirstgame.GameView.dHeight;
import static com.example.myfirstgame.GameView.dWidth;

public class PlaneDown extends Plane {
    public PlaneDown(Context context) {
        super(context);
    }

    @Override
    public void reset() {
        planeX= 1000+random.nextInt(dWidth-300);
        planeY=dHeight+300+random.nextInt(1000);
        velocity=4+random.nextInt(10);
        velocityY=-(5+random.nextInt(15));
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
            if(planeY<-planeHeight||(planeY>dHeight && velocityY>0))
            {
                reset();
            }
        }
    }
}
