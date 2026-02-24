package com.example.final_project_uriya;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GameScore.class}, version = 1)
public abstract class LeaderBoardDB extends RoomDatabase {
    private static LeaderBoardDB instance;

    public abstract LeaderBoardDAO leaderBoardDao();

    public static synchronized LeaderBoardDB getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LeaderBoardDB.class,
                            "leaderboard_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}