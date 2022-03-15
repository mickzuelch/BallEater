package com.mizudev.balleater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;

public class Game_Endless_NoJump extends AppCompatActivity {
    private GameEndlesNoJumpView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameEndlesNoJumpView(this, point.x ,point.y );
        setContentView(gameView);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }


    public void endView()
    {
        Intent intent = new Intent(Game_Endless_NoJump.this, GameOver.class);
        startActivity(intent);
    }
}