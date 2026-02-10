package com.example.final_project_uriya;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "leaderboard")
public class LeaderBoard {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private TimeData time;

    public LeaderBoard leaderBoard()// לסיים כתיבת פעולה בונה
}
