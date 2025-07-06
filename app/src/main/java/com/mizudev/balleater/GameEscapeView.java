package com.mizudev.balleater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

public class GameEscapeView extends SurfaceView implements Runnable{

    private Thread gameThread;
    private boolean gameActive;
    private boolean gameOver;
    private int screenX, screenY;
    private Paint paint;
    private PlayerBall player;
    private Food food;
    private boolean foodMoving;
    private float foodVX, foodVY;
    private int waitCounter;
    private float foodSpeed;
    private int level;
    private long score;
    private GameEscapeBalls gameActivity;
    private SharedPreferences prefs;

    public GameEscapeView(GameEscapeBalls context, int x, int y){
        super(context);
        gameActivity = context;
        prefs = context.getSharedPreferences("BallEaterValues", Context.MODE_PRIVATE);
        this.screenX = x;
        this.screenY = y;

        gameOver = false;
        paint = new Paint();
        player = new PlayerBall(200f,(float) screenX,(float) screenY);
        food = new Food(20.0F);
        foodSpeed = 5f;
        level = 1;
        score = 0;
        spawnFood();
    }

    @Override
    public void run() {
        while(gameActive)
        {
            update();
            draw();
            sleep();
        }
    }

    private void spawnFood(){
        Random rnd = new Random();
        int edge = rnd.nextInt(4);
        float posX = 0, posY = 0;
        float size = food.getFoodSize();
        switch(edge){
            case 0: // top
                posX = rnd.nextFloat() * (screenX - 2*size) + size;
                posY = size;
                break;
            case 1: // right
                posX = screenX - size;
                posY = rnd.nextFloat() * (screenY - 2*size) + size;
                break;
            case 2: // bottom
                posX = rnd.nextFloat() * (screenX - 2*size) + size;
                posY = screenY - size;
                break;
            case 3: // left
                posX = size;
                posY = rnd.nextFloat() * (screenY - 2*size) + size;
                break;
        }
        food.setPosition(posX, posY);
        foodMoving = false;
        waitCounter = 20; // frames to show before moving
    }

    public void save()
    {
        GameOver.finalScoreLong = score;
        MainActivity.addAfterAttemps += 1;
        if(MainActivity.highScore < score)
            MainActivity.highScore = score;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("highScore", MainActivity.highScore);
        editor.putInt("addAfterAttemps", MainActivity.addAfterAttemps);
        editor.commit();
    }

    public  void resume()
    {
        gameActive = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause()
    {
        try {
            gameActive = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update()
    {
        if(gameOver)
        {
            save();
            waitBeforeExiting();
            return;
        }

        if(!foodMoving){
            waitCounter--;
            if(waitCounter <= 0){
                float dx = player.getX() - food.getX();
                float dy = player.getY() - food.getY();
                float length = (float)Math.sqrt(dx*dx + dy*dy);
                if(length == 0) length = 1;
                foodVX = foodSpeed * dx / length;
                foodVY = foodSpeed * dy / length;
                foodMoving = true;
            }
        }else{
            food.setPosition(food.getX() + foodVX, food.getY() + foodVY);
            if(food.getX() < 0 || food.getX() > screenX || food.getY() < 0 || food.getY() > screenY){
                score++;
                if(score % 5 == 0){
                    level++;
                    foodSpeed += 1.5f;
                }
                spawnFood();
            }
        }

        if (food.checkEat(player.getPlayerSize(), player.getX(), player.getY()))
        {
            gameOver = true;
        }
    }

    private void draw()
    {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            Rect background = new Rect(0,0, screenX, screenY);
            paint.setColor(Color.BLACK);
            canvas.drawRect(background, paint);

            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
            textPaint.setTextAlign(Paint.Align.LEFT);
            Paint.FontMetrics metric = textPaint.getFontMetrics();
            int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
            int y = (int)(textHeight - metric.descent);
            canvas.drawText("Score: " + score,screenX/2 - 30, y + 25, textPaint);
            canvas.drawText("Level: " + level, screenX/2 - 30, y*2 + 25, textPaint);

            paint.setColor(Color.RED);
            canvas.drawCircle(player.getX(), player.getY(), player.getPlayerSize(), paint);

            paint.setColor(Color.GREEN);
            canvas.drawCircle(food.getX(), food.getY(), food.getFoodSize(), paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep()
    {
        try {
            Thread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        player.setLocation(event.getX(), event.getY());
        return true;
    }

    private void waitBeforeExiting()
    {
        try {
            Thread.sleep(300);
            Intent intent = new Intent(gameActivity, GameOver.class);
            gameActivity.startActivity(intent);
        } catch (Exception e)
        {
        }
    }
}
