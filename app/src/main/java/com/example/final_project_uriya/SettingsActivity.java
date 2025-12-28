package com.example.final_project_uriya;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    Button btnGridSize, btnSnakeSpeed, appleAmount, btnBgColor, btnBackToMainMenu;
    public static String size = "small";
    public static int snakeSpeed = 400;
    public static int appleAmountValue = 1;


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
        appleAmount = findViewById(R.id.btnAppleAmount);
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

        btnSnakeSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Speed options shown to the user
                final String[] speedNames = {
                        "Very Slow",
                        "Slow",
                        "Normal",
                        "Fast",
                        "Very Fast"
                };

                // Actual speed values (milliseconds)
                final int[] speedValues = {
                        600,   // Very Slow
                        500,   // Slow
                        400,   // Normal
                        300,   // Fast
                        200    // Very Fast
                };

                final int[] selectedIndex = {-1};

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(SettingsActivity.this);

                builder.setTitle("Choose Snake Speed");

                builder.setSingleChoiceItems(speedNames, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        });

                builder.setPositiveButton("Set",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectedIndex[0] != -1) {
                                    snakeSpeed = speedValues[selectedIndex[0]];

                                    Toast.makeText(SettingsActivity.this,
                                            "Snake speed set to: " + snakeSpeed + " ms",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                builder.setNegativeButton("Cancel", null);

                builder.create().show();
            }
        });

        appleAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] appleOptions = {
                        "1 Apple",
                        "2 Apples",
                        "3 Apples",
                        "4 Apples",
                        "5 Apples"
                };

                final int[] appleValues = {1, 2, 3, 4, 5};

                final int[] selectedIndex = {-1};

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(SettingsActivity.this);

                builder.setTitle("Choose Apple Amount");

                builder.setSingleChoiceItems(appleOptions, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        });

                builder.setPositiveButton("Set",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectedIndex[0] != -1) {
                                    appleAmountValue = appleValues[selectedIndex[0]];

                                    Toast.makeText(SettingsActivity.this,
                                            "Apple amount set to: " + appleAmountValue,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                builder.setNegativeButton("Cancel", null);

                builder.create().show();
            }
        });

    }
}