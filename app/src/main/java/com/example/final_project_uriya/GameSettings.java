package com.example.final_project_uriya;

public class GameSettings {
    private int snakeSpeed;
    private int appleAmountValue;

    public GameSettings(int appleAmountValue, int snakeSpeed) {
        this.appleAmountValue = appleAmountValue;
        this.snakeSpeed = snakeSpeed;
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public void setSnakeSpeed(int snakeSpeed) {
        this.snakeSpeed = snakeSpeed;
    }

    public int getAppleAmountValue() {
        return appleAmountValue;
    }

    public void setAppleAmountValue(int appleAmountValue) {
        this.appleAmountValue = appleAmountValue;
    }
}
