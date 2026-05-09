package com.example.final_project_uriya;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameScore")
public class GameScore {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int time;
    private int appleAmount;
    private String userName;

    public GameScore() {}

    public GameScore(int time, int appleAmount, String userName){
        this.time = time;
        this.appleAmount = appleAmount;
        this.userName = userName;
        id = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAppleAmount() {
        return appleAmount;
    }

    public void setAppleAmount(int appleAmount) {
        this.appleAmount = appleAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
