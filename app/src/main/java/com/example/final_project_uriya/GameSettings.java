package com.example.final_project_uriya;

public class GameSettings {
    private int snakeSpeed;
    private int appleAmount;

    public GameSettings(int appleAmountValue, int snakeSpeed) {
        this.appleAmount = appleAmountValue;
        this.snakeSpeed = snakeSpeed;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public void setSnakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
    }


    public int getAppleAmount() {
        return appleAmount;
    }

    public void setAppleAmount(int appleAmountValue) {
        this.appleAmount = appleAmountValue;
    }
}
