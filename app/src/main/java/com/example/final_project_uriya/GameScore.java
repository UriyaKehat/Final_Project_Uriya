package com.example.final_project_uriya;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameScore")
public class GameScore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int timeInMilliseconds;
    private int appleAmount;
    private String userName;

    public GameScore() {}

    public GameScore(int time, int appleAmount, String userName){
        this.timeInMilliseconds = time;
        this.appleAmount = appleAmount;
        this.userName = userName;//לטפל דרך JAVA שלא יהיו כפולות שמות
        id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(int timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
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
