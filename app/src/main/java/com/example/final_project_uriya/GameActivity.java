package com.example.final_project_uriya;

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
    private Button btnRestart, btnUp, btnDown, btnLeft, btnRight;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handlerGame = new Handler();
    private Runnable gameTickRunnable;
    private boolean gameStopped = false;
    private GameLogic gamelogic;
    private String username, nextDirection = "Right";
    private LeaderBoardDAO leaderBoardDAO;
    private GameScore gameScore;
    private LeaderBoardDB leaderBoardDB;
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
        btnRestart = findViewById(R.id.btnRestart);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        leaderBoardDAO = LeaderBoardDB.getInstance(this).leaderBoardDao();

        gridLayout = findViewById(R.id.gridLayout);
        gameGrid = new GameGrid(this, gridLayout,
                gamelogic.rows, gamelogic.columns, 85);

        gameGrid.buildGrid(gamelogic.matGameGrid);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

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
        gameScore = new GameScore(formatTime(getElapsedMillis()), GameSettings.appleAmount, username);

        if(leaderBoardDAO.getGameScoreByUserName(gameScore.getUserName()) == null){
            leaderBoardDAO.insert(gameScore);
        }
        else{
            leaderBoardDAO.update(gameScore);
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

    public String formatTime(int millis) {
        int totalSeconds = millis / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}