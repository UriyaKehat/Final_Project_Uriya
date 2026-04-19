package com.example.final_project_uriya;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnNewGame, btnLeaderBoard, btnSetting;
    private EditText etUserName;
    private ImageView imageViewSnake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageViewSnake = findViewById(R.id.imageViewSnake);
        etUserName = findViewById(R.id.etUserName);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnLeaderBoard = findViewById(R.id.btnLeaderBoard);
        btnSetting = findViewById(R.id.btnSettings);
        imageViewSnake.setImageResource(R.drawable.snake_text);
        //

        btnNewGame.setBackgroundColor(0xFF27AE60);
        btnNewGame.setTextColor(Color.WHITE);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString().trim();
                if (username.isEmpty())
                    showToast("put in a name");
                else
                {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                }
            }
        });
        //
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        //
        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainMenuActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });
    }
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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