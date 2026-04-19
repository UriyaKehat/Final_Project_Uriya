package com.example.final_project_uriya;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        tvUpTop = findViewById(R.id.tvUpTop);
        btnDeleteDB = findViewById(R.id.btnDeleteDB);
        btnBackToMainMenu = findViewById(R.id.btnBackToMainMenuFromLeaderBoard);
        //
        btnDeleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderBoardDAO.deleteAll();
            }
        });
        btnBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        leaderBoardDAO = LeaderBoardDB.getInstance(this).leaderBoardDao();
        scoresData = leaderBoardDAO.getAllLeaderBoards();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Adapter(LeaderBoardActivity.this, this.scoresData);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LeaderBoardActivity.this));
    }
}