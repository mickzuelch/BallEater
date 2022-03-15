package com.mizudev.balleater;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private TextView gameOver;
    private TextView highScoreGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen);

        gameOver = findViewById(R.id.textViewGO);
        gameOver.setTextSize(30);
        gameOver.setTextColor(Color.WHITE);
        gameOver.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        gameOver.setText("Game Over");

        highScoreGameOver = findViewById(R.id.textViewGO);
        highScoreGameOver.setTextSize(20);
        highScoreGameOver.setTextColor(Color.WHITE);
        highScoreGameOver.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        highScoreGameOver.setText("Highscore:\n" + MainActivity.highScore);
    }
}
