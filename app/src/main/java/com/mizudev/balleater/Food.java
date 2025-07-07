package com.mizudev.balleater;

import java.util.Random;

public class Food {
    private float ballSize;
    private float foodValue;
    private float x,y;

    public Food(float eatSize)
    {
        ballSize = eatSize;
        foodValue = 10.0F;
    }

    public float getFoodSize()
    {
        return ballSize;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getFoodValue()
    {
        return foodValue;
    }

    public boolean checkEat(float playerSize, float playerX, float playerY)
    {
        float distance = (playerX - this.x) * (playerX - this.x) + (playerY - this.y) * (playerY - this.y);
        float radSumSq = (playerSize + this.ballSize) * (playerSize + this.ballSize);
        if( distance > radSumSq )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void newPosition(float playerSize, float playerX, float playerY, int screenX, int screenY)
    {
        Random rnd = new Random();
        float pointX = (rnd.nextFloat() * screenX) + ballSize;
        float pointY = (rnd.nextFloat() * screenY) + ballSize;
        /*if(checkEat(playerSize, playerX, playerY)) {
            int counter = 0;
            while (checkEat(playerSize, playerX, playerY) && counter < 10) {
                rnd = new Random();
                pointX = (rnd.nextFloat() * screenX) + ballSize;
                pointY = (rnd.nextFloat() * screenY) + ballSize;
            }
            counter++;
        }*/
        x = pointX;
        y = pointY;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
