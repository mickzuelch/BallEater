package com.mizudev.balleater;

public class PlayerBall {

    private float ballSize;
    private float decrease;
    private float x,y;
    private float ballSizeLimit;
    private float ballSizeScale;

    public PlayerBall(float size, float screenX, float screenY)
    {
        ballSize = size;
        ballSizeLimit = 300.0F;
        ballSizeScale = 1.0F;
        x = screenX/2.0F;
        y = screenY/2.0F;
        decrease = 1.5F;
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }

    public float getPlayerSize()
    {
        return ballSize / ballSizeScale;
    }

    public void setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean shrinkPlayer()
    {
        ballSize -= decrease;
        if(ballSize > 0.0f)
            return false;
        else
            return true;
    }

    public void increasePlayer(float size)
    {
        if(ballSize < ballSizeLimit)
            ballSize += size;
        if(ballSize > ballSizeLimit)
            ballSize = ballSizeLimit;
    }

    public void increaseDecreaser(float decreaser)
    {
        decrease += decreaser;
        ballSize = ballSizeLimit;
        ballSizeLimit += 25;
        ballSizeScale = ballSizeLimit / 300.0F;
    }

    public boolean increasePlayer()
    {
        ballSize += 2.5;
        if(ballSize > 1000)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
