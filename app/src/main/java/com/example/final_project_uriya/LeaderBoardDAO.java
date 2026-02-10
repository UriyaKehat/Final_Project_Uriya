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
    void insert(LeaderBoard leaderBoard);

    @Update
    void update(LeaderBoard leaderBoard);

    @Delete
    void delete(LeaderBoard leaderBoard);

    @Query("SELECT * FROM leaderboard")
    List<LeaderBoard>getAllLeaderBoards();

    @Query("SELECT * FROM leaderboard WHERE id = :id")
    LeaderBoard getLeaderBoardByID(int id);
}