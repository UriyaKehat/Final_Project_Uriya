package com.example.final_project_uriya;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
                showLargeToast("Restart");
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextDirection = "Up";
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextDirection = "Down";
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextDirection = "Right";
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    showLargeToast("You Lose");
                    return;
                }

                // עדכון לוח
                gameGrid.buildGrid(GameLogic.matGameGrid);

                // לולאת זמן מחדש
                handler.postDelayed(this, SettingsActivity.speed);
            }
        };

        handler.postDelayed(tickRunnable, SettingsActivity.speed);
    }
    private void restartGame() {
        // עצירת הטיימר
        handler.removeCallbacks(tickRunnable);

        // איפוס הלוגיקה
        GameLogic.resetGame();

        // איפוס כיוון
        nextDirection = "Right";

        // בניית המסך מחדש
        gameGrid.buildGrid(GameLogic.matGameGrid);

        // הפעלת הטיימר מחדש
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(tickRunnable);
    }
    private void showLargeToast(String message) {
        Toast toast = Toast.makeText(GameActivity.this, message, Toast.LENGTH_SHORT);

        // יצירת TextView מותאם
        TextView textView = new TextView(GameActivity.this);
        textView.setText(message);
        textView.setTextSize(50); // גודל גדול יותר
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(20, 20, 20, 20);
        textView.setBackgroundColor(0x88000000); // חצי שקוף רקע

        toast.setView(textView);
        toast.show();
    }
}
