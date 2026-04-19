package com.example.final_project_uriya;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LeaderBoardDAO {
    @Insert
    void insert(GameScore gameScore);

    @Update
    void update(GameScore gameScore);

    @Delete
    void delete(GameScore gameScore);

    @Query("SELECT * FROM GameScore ORDER BY appleAmount ASC, time ASC")
    List<GameScore> getAllLeaderBoards();

    @Query("SELECT * FROM GameScore WHERE id = :id")
    GameScore getGameScoreByID(int id);

    @Query("SELECT * FROM GameScore WHERE userName = :name LIMIT 1")
    GameScore getGameScoreByName(String name);
}