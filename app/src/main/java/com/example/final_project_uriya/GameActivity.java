package com.example.final_project_uriya;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {


    private TextView tvTimer;
    private Button btnRestart, btnUp, btnDown, btnLeft, btnRight, btnBackToMainMenu;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handlerGame = new Handler();
    private Runnable gameTickRunnable;
    private boolean gameStopped = false;
    private GameLogic gamelogic;
    private String username, nextDirection = "Right";
    private LeaderBoardDAO leaderBoardDAO;
    private GameScore gameScore, tempGameScore;
    private CountDownTimer timer;
    private long startTime;
    private long elapsedMillis;
    private boolean isTimerRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        username = getIntent().getStringExtra("username");
        tvTimer = findViewById(R.id.tvTimer);
        gamelogic = new GameLogic();
        btnBackToMainMenu = findViewById(R.id.btnBackToMainMenuFromGame);
        btnRestart = findViewById(R.id.btnRestart);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        leaderBoardDAO = LeaderBoardDB.getInstance(this).leaderBoardDao();

        gridLayout = findViewById(R.id.gridLayout);
        gameGrid = new GameGrid(this, gridLayout, gamelogic.rows, gamelogic.columns, 85);

        gameGrid.buildGrid(gamelogic.matGameGrid);

        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
        btnRestart.setBackgroundColor(0xFFE0A800);
        btnRestart.setTextColor(Color.WHITE);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
        //

        btnUp.setBackgroundColor(0xFF2D6CDF);
        btnUp.setTextColor(Color.WHITE);
        btnDown.setBackgroundColor(0xFF2D6CDF);
        btnDown.setTextColor(Color.WHITE);
        btnRight.setBackgroundColor(0xFF2D6CDF);
        btnRight.setTextColor(Color.WHITE);
        btnLeft.setBackgroundColor(0xFF2D6CDF);
        btnLeft.setTextColor(Color.WHITE);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gamelogic.currentDirection.equals("Down"))
                    nextDirection = "Up";
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gamelogic.currentDirection.equals("Up"))
                    nextDirection = "Down";
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gamelogic.currentDirection.equals("Right"))
                    nextDirection = "Left";
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gamelogic.currentDirection.equals("Left"))
                    nextDirection = "Right";
            }
        });

        restartGame();
    }

    public void startTimer() {
        if (isTimerRunning) return;

        startTime = System.currentTimeMillis() - elapsedMillis; // שמירה במקרה של הפעלה מחדש
        isTimerRunning = true;

        timer = new CountDownTimer(3600000, 1000) { // שעה אחת
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedMillis = System.currentTimeMillis() - startTime;

                int seconds = (int) (elapsedMillis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // לא יקרה
            }
        };

        timer.start();
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
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            isTimerRunning = false;
        }
    }
    public void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        elapsedMillis = 0;
        isTimerRunning = false;
        tvTimer.setText("00:00");
    }
    public int getElapsedMillis() {
        return (int) elapsedMillis;
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
        resetTimer();
        nextDirection = "Right";
        gameGrid.buildGrid(gamelogic.matGameGrid);
        startGame();
        showLargeToast("Start");
    }

    private void onGameWon() {
        gameStopped = true;
        stopGame();
        showLargeToast("You Won!");
        UpdateDB();
    }

    private void UpdateDB() {

        GameScore newScore = new GameScore(
                getElapsedMillis(),
                GameSettings.appleAmount,
                username
        );

        GameScore existing = leaderBoardDAO.getGameScoreByName(username);

        // אם אין רשומה קיימת
        if (existing == null) {
            leaderBoardDAO.insert(newScore);
            return;
        }

        boolean isBetter = false;

        // 🔥 פחות תפוחים = יותר טוב
        if (newScore.getAppleAmount() < existing.getAppleAmount()) {
            isBetter = true;
        }

        // אם אותה כמות תפוחים → זמן קובע
        else if (newScore.getAppleAmount() == existing.getAppleAmount()) {

            if (newScore.getTime() < existing.getTime()) {
                isBetter = true;
            }
        }

        // עדכון רק אם טוב יותר
        if (isBetter) {
            newScore.setId(existing.getId());
            leaderBoardDAO.update(newScore);
        }
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

    @Override
    protected void onResume() {
        super.onResume();

        View root = findViewById(android.R.id.content);

        if (GameSettings.bgColor == -1) {
            root.setBackgroundColor(GameSettings.DEFAULT_BG_COLOR);
        } else {
            root.setBackgroundColor(GameSettings.bgColor);
        }
    }
}