package com.mizudev.balleater;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private TextView gameOver;
    private TextView highScoreGameOver;
    private TextView finalScore;

    private Button newGame;
    private Button mainMenu;

    public static long finalScoreLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen);

        gameOver = findViewById(R.id.textViewGO);
        gameOver.setTextSize(30);
        gameOver.setTextColor(Color.WHITE);
        gameOver.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        gameOver.setText("Game Over");

        highScoreGameOver = findViewById(R.id.HighScoreGO);
        highScoreGameOver.setTextSize(20);
        highScoreGameOver.setTextColor(Color.WHITE);
        highScoreGameOver.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        highScoreGameOver.setText("Highscore:\n" + MainActivity.highScore);

        finalScore = findViewById(R.id.finalScoreGO);
        finalScore.setTextSize(20);
        finalScore.setTextColor(Color.WHITE);
        finalScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        finalScore.setText("Your Score:\n" + finalScoreLong);

        newGame = findViewById(R.id.replayGO);
        newGame.setText("Replay");
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, Game_Endless_NoJump.class);
                startActivity(intent);
            }
        });

        mainMenu = findViewById(R.id.backToMainMenuGO);
        mainMenu.setText("Main Menu");
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
