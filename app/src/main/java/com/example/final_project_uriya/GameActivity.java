package com.example.final_project_uriya;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity
        implements GameLogic.GameEventsListener { // ★ NEW


    private Button btnRestart, btnUp, btnDown, btnLeft, btnRight;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handler = new Handler();
    private Runnable tickRunnable;
    private boolean gameStopped = false;

    private GameLogic gamelogic; // ★ CHANGED
    private String nextDirection = "Right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // ★ CHANGED – יצירה נכונה עם listener
        gamelogic = new GameLogic(this);

        btnRestart = findViewById(R.id.btnRestart);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        gridLayout = findViewById(R.id.gridLayout);
        gameGrid = new GameGrid(this, gridLayout,
                gamelogic.rows, gamelogic.columns, 85);

        gameGrid.buildGrid(gamelogic.matGameGrid);

        btnRestart.setOnClickListener(v -> restartGame());

        btnUp.setOnClickListener(v -> {
            if (!nextDirection.equals("Down"))
                nextDirection = "Up";
        });

        btnDown.setOnClickListener(v -> {
            if (!nextDirection.equals("Up"))
                nextDirection = "Down";
        });

        btnLeft.setOnClickListener(v -> {
            if (!nextDirection.equals("Right"))
                nextDirection = "Left";
        });

        btnRight.setOnClickListener(v -> {
            if (!nextDirection.equals("Left"))
                nextDirection = "Right";
        });

        restartGame();
    }

    private void startTimer() {
        tickRunnable = new Runnable() {
            @Override
            public void run() {

                if (gameStopped) return;

                boolean alive = gamelogic.MoveSnake(nextDirection);

                if (!alive) {
                    gameStopped = true;
                    stopTimer();
                    showLargeToast("You Lose");
                    return;
                }

                gameGrid.buildGrid(gamelogic.matGameGrid);
                if (!gameStopped) {         // ★ NEW GUARD
                    handler.postDelayed(this, SettingsActivity.snakeSpeed);
                }
            }
        };

        handler.postDelayed(tickRunnable, SettingsActivity.snakeSpeed);
    }

    public void stopTimer() {
        if (tickRunnable != null) {
            handler.removeCallbacks(tickRunnable);
        }
    }


    private void restartGame() {
        stopTimer();
        gameStopped = false;
        gamelogic.resetGame();
        nextDirection = "Right";
        gameGrid.buildGrid(gamelogic.matGameGrid);
        startTimer();
        showLargeToast("Start");
    }

    // ★ NEW – callback מה־GameLogic
    @Override
    public void onGameWon() {
        gameStopped = true;
        stopTimer();
        showLargeToast("You Won!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void showLargeToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        TextView tv = new TextView(this);
        tv.setText(message);
        tv.setTextSize(50);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setPadding(20, 20, 20, 20);
        tv.setBackgroundColor(0x88000000);

        toast.setView(tv);
        toast.show();
    }
}
