package com.example.final_project_uriya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    ImageView[][] imageMat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GridLayout layout = findViewById(R.id.main);
        layout.setColumnCount(10);
        layout.setRowCount(11);
        imageMat = new ImageView[10][11];
        for (int i = 0; i < imageMat.length; i ++){
            for (int k = 0; k < imageMat[0].length; k ++){
                ImageView img = new ImageView(this);
                if (GameLogic.matGameGrid[i][k] == 0) {
                    img.setImageResource(R.drawable.gray_square);
                    imageMat[i][k] = img;
                    layout.addView(img);
                }
                if (GameLogic.matGameGrid[i][k] == 1) {
                    img.setImageResource(R.drawable.lime_square);
                    imageMat[i][k] = img;
                    layout.addView(img);
                }
                if (GameLogic.matGameGrid[i][k] == 2) {
                    img.setImageResource(R.drawable.red_square);
                    imageMat[i][k] = img;
                    layout.addView(img);
                }
            }
        }
    }
}