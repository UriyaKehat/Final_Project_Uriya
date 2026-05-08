package com.example.final_project_uriya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    private Button btnBackToMainMenu, btnDeleteDB;
    private TextView tvUpTop;
    private RecyclerView recyclerView;
    private List<GameScore> scoresData;
    private Adapter adapter;
    private LeaderBoardDAO leaderBoardDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leader_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //נועל רוטציית מסך

        tvUpTop = findViewById(R.id.tvUpTop);
        btnDeleteDB = findViewById(R.id.btnDeleteDB);
        btnBackToMainMenu = findViewById(R.id.btnBackToMainMenuFromLeaderBoard);
        //
        btnDeleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeleteAllConfirmation();
            }
        });
        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SetRecyclerView();
    }
    private void ShowDeleteAllConfirmation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete all data?");

        // YES
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                leaderBoardDAO.deleteAll();
                SetRecyclerView();

                Toast.makeText(LeaderBoardActivity.this,
                        "All data deleted",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // NO
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // פשוט סוגר את החלון
            }
        });

        builder.show();
    }
    private void SetRecyclerView()
    {
        leaderBoardDAO = LeaderBoardDB.getInstance(this).leaderBoardDao();
        scoresData = leaderBoardDAO.getAllLeaderBoards();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Adapter(LeaderBoardActivity.this, this.scoresData);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
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