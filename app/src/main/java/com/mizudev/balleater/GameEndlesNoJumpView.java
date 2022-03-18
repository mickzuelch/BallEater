package com.mizudev.balleater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class GameEndlesNoJumpView extends SurfaceView implements Runnable{

    private Thread gameThread;
    private boolean gameActive;
    private boolean gameOver;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private PlayerBall player;
    private Food food;
    private long count;
    private int level;
    private long levelBarier;
    private long score;
    private Game_Endless_NoJump gameActivity;
    private SharedPreferences prefs;

    private InterstitialAd mInterstitialAd;


    public static long finalScore;

    public GameEndlesNoJumpView(Game_Endless_NoJump context, int x, int y){
        super(context);
        gameActivity = context;
        prefs = context.getSharedPreferences("BallEaterValues", Context.MODE_PRIVATE);
        this.screenX = x;
        this.screenY = y;

        count = 0;

        gameOver = false;
        paint = new Paint();
        player = new PlayerBall(200f,(float) screenX,(float) screenY);
        food = new Food(20.0F);
        food.newPosition(player.getPlayerSize(), player.getX(), player.getY(), screenX - 2*(int)food.getFoodSize(), screenY - 2*(int)food.getFoodSize());
        levelBarier = 25;
        score = 0;
        level = 1;

        finalScore = 0;

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();


        InterstitialAd.load(this.getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", "laodAdd Failed");
                        mInterstitialAd = null;
                    }
                });


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
    public void save()
    {
        finalScore = score;
        MainActivity.addAfterAttemps += 1;
        //if (mInterstitialAd != null && MainActivity.addAfterAttemps >= 3) {
        if (MainActivity.addAfterAttemps >= 3) {
            //mInterstitialAd.show(gameActivity);
            MainActivity.addAfterAttemps = 0;
        }
        if(MainActivity.highScore < score)
            MainActivity.highScore = score;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("highScore", MainActivity.highScore);
        editor.putInt("addAfterAttemps", MainActivity.addAfterAttemps);

        // Commit the edits!
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
            waitBeforeExtiting();
            return;
        }
        if(player.shrinkPlayer())
        {
            gameOver = true;
        }
        if (food.checkEat(player.getPlayerSize(), player.getX(), player.getY()))
        {
            player.increasePlayer(food.getFoodValue());
            count++;
            score++;

            food.newPosition(player.getPlayerSize(), player.getX(), player.getY(), screenX - 2*(int)food.getFoodSize(), screenY - 2*(int)food.getFoodSize());
            if(count % levelBarier == 0)
            {
                count = 0;
                player.increaseDecreaser(0.5F);
                levelBarier += 5;
                level++;
            }
        }
    }

    private void draw()
    {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            //start drawing
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
            //Last from drawing method
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
        /*switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                player.setLocation(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                player.setLocation(event.getX(), event.getY());
                break;
        }*/
        return true;
    }

    private void waitBeforeExtiting()
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
