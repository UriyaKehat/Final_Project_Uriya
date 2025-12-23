package com.example.final_project_uriya;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private Button btnRestart, btnUp, btnDown, btnLeft, btnRight;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handler = new Handler();
    private Runnable tickRunnable;

    // כיוון הנוכחי של המשחק
    private String nextDirection = "Right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // כפתורים
        btnRestart = findViewById(R.id.btnRestart);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        // Grid
        gridLayout = findViewById(R.id.gridLayout);
        gameGrid = new GameGrid(this, gridLayout, GameLogic.rows, GameLogic.columns, 85);

        // הצגת לוח התחלתי
        gameGrid.buildGrid(GameLogic.matGameGrid);

        // מאזיני כפתורים
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nextDirection.equals("Down"))
                    nextDirection = "Up";
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nextDirection.equals("Up"))
                    nextDirection = "Down";
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nextDirection.equals("Left"))
                    nextDirection = "Right";
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nextDirection.equals("Right"))
                    nextDirection = "Left";
            }
        });

        startTimer();
    }

    private void startTimer() {
        tickRunnable = new Runnable() {
            @Override
            public void run() {

                // הזזת הסנייק
                boolean alive = GameLogic.MoveSnake(nextDirection);

                // עצירה אם מת
                if (!alive) {
                    handler.removeCallbacks(tickRunnable);
                    return;
                }

                // עדכון לוח
                gameGrid.buildGrid(GameLogic.matGameGrid);

                // לולאת זמן מחדש
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(tickRunnable, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(tickRunnable);
    }
}
