
package com.example.final_project_uriya;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    private Button btnUp, btnDown, btnLeft, btnRight;
    private GridLayout gridLayout;
    private GameGrid gameGrid;
    private Handler handler = new Handler();
    private Runnable tickRunnable;
    private String direction = "Left";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        gridLayout = findViewById(R.id.gridLayout);
        gameGrid = new GameGrid(this, gridLayout, GameLogic.rows, GameLogic.columns, 70);

        gameGrid.buildGrid(GameLogic.matGameGrid);

        btnUp.setOnClickListener(v -> direction = "Up");
        btnDown.setOnClickListener(v -> direction = "Down");
        btnLeft.setOnClickListener(v -> direction = "Left");
        btnRight.setOnClickListener(v -> direction = "Right");

        startTimer();
    }

    private void startTimer() {
        tickRunnable = new Runnable() {
            @Override
            public void run() {
                boolean alive = GameLogic.MoveSnake(direction);

                //תנאי עצירה
                if (!alive) {
                    handler.removeCallbacks(tickRunnable);
                    return;
                }

                gameGrid.buildGrid(GameLogic.matGameGrid);
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