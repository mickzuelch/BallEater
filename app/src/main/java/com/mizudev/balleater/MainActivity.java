package com.mizudev.balleater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startGameButton;
    private Button startEscapeGame;
    private TextView highScoreTextView;

    public static int addAfterAttemps;
    public static long highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addAfterAttemps = 0;
        highScore = 0;

        try {
            SharedPreferences prefs = this.getSharedPreferences("BallEaterValues", Context.MODE_PRIVATE);
            highScore = prefs.getLong("highScore", 0); //0 is the default value
            addAfterAttemps = prefs.getInt("addAfterAttemps", 0); //0 is the default value
        } catch(Exception e)
        {

        }

        startGameButton = findViewById(R.id.startGame);
        startGameButton.setText("BallEater");
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Game_Endless_NoJump.class);
                startActivity(intent);
            }
        });

        startEscapeGame = findViewById(R.id.startGameEscape);
        startEscapeGame.setText("Food Escape");
        startEscapeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameEscapeBalls.class);
                startActivity(intent);
            }
        });

        highScoreTextView = findViewById(R.id.highScoreMAText);
        highScoreTextView.setTextSize(20F);
        highScoreTextView.setTextColor(Color.BLACK);
        highScoreTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        highScoreTextView.setText("Highscore: " + highScore);
    }
}