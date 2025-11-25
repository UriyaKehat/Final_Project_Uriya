package com.example.final_project_uriya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    Button btnGridSize, btnSnakeSpeed, btnSnakeColor, btnBgColor, btnBackToMainMenu;
    public static String size = "small";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGridSize = findViewById(R.id.btnGridSize);
        //
        btnSnakeSpeed = findViewById(R.id.btnSnakeSpeed);
        //
        btnSnakeColor = findViewById(R.id.btnSnakeColor);
        //
        btnBgColor = findViewById(R.id.btnBgColor);
        //
        btnBackToMainMenu = findViewById(R.id.btnBackToMainMenu);
        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}