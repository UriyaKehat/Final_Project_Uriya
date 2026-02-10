package com.example.final_project_uriya;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {


    private TextView tvTimer;
    private Button btnRestart, btnUp, btnDown, btnLeft, btnRight;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handlerGame = new Handler();
    private Handler handlerTimer = new Handler();
    private Runnable gameTickRunnable, timerRunnable;
    private int totalSeconds = 0;
    private boolean gameStopped = false, timerIsRunning = false;
    private GameLogic gamelogic;
    private String nextDirection = "Right";
    private TimeData stoppedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvTimer = findViewById(R.id.tvTimer);
        gamelogic = new GameLogic();
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
            if (!gamelogic.currentDirection.equals("Down"))
                nextDirection = "Up";
        });

        btnDown.setOnClickListener(v -> {
            if (!gamelogic.currentDirection.equals("Up"))
                nextDirection = "Down";
        });

        btnLeft.setOnClickListener(v -> {
            if (!gamelogic.currentDirection.equals("Right"))
                nextDirection = "Left";
        });

        btnRight.setOnClickListener(v -> {
            if (!gamelogic.currentDirection.equals("Left"))
                nextDirection = "Right";
        });

        restartGame();
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                totalSeconds++;

                int hours = totalSeconds / 3600;
                int minutes = (totalSeconds % 3600) / 60;
                int seconds = totalSeconds % 60;

                tvTimer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                handlerTimer.postDelayed(this, 1000);
            }
        };
        if (!timerIsRunning) {
            handlerTimer.post(timerRunnable);
            timerIsRunning = true;
        }
    }

    private void startGameTick() {
        gameTickRunnable = new Runnable() {
            @Override
            public void run() {

                if (gameStopped) return;

                int alive = gamelogic.MoveSnake(nextDirection);

                if (alive == 0) {
                    gameStopped = true;
                    stopGame();
                    showLargeToast("You Lose");
                    return;
                }
                 else if (alive == 2) {
                    onGameWon();
                }
                gameGrid.buildGrid(gamelogic.matGameGrid);
                if (!gameStopped) {
                    handlerGame.postDelayed(this, GameSettings.snakeSpeed);
                }
            }
        };

        handlerGame.postDelayed(gameTickRunnable, GameSettings.snakeSpeed);
    }

    private void stopGameTick() {
        if (gameTickRunnable != null) {
            handlerGame.removeCallbacks(gameTickRunnable);
        }
    }
    private void stopTimer() {
        if (timerIsRunning) {
            handlerTimer.removeCallbacks(timerRunnable);
            timerIsRunning = false;

            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;

            stoppedTime = new TimeData(hours, minutes, seconds);
        }
    }

    private void startGame() {
        startGameTick();
        startTimer();
    }

    private void stopGame() {
        stopGameTick();
        stopTimer();
    }

    private void restartGame() {
        stopGame();
        gameStopped = false;
        gamelogic.resetGame();
        totalSeconds = 0;
        nextDirection = "Right";
        gameGrid.buildGrid(gamelogic.matGameGrid);
        startGame();
        showLargeToast("Start");
    }

    private void onGameWon() {
        gameStopped = true;
        stopGame();
        showLargeToast("You Won!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopGame();
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