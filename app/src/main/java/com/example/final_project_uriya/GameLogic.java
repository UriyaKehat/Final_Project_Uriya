
package com.example.final_project_uriya;

public class GameLogic {
    public static final int EMPTY = 0, SNAKE = 1 ,APPLE = 2,
                            TAIL_MARK_UP = 3,
                            TAIL_MARK_DOWN = 4,
                            TAIL_MARK_RIGHT = 5,
                            TAIL_MARK_LEFT = 6;
    public static int snakeHeadRow = 5, snakeHeadColumn = 2, snakeTailRow = 5, snakeTailColumn = 5;
    public static int[][] matGameGrid = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public static int rows = matGameGrid.length;
    public static int columns = matGameGrid[0].length;
    public static boolean MoveSnake(String direction) {
        //ציור נחש
        matGameGrid[snakeHeadRow][snakeHeadColumn] = 1;

        //בדיקת כיוון
        if (direction.equals("Up")) snakeHeadRow--;
        else if (direction.equals("Down")) snakeHeadRow++;
        else if (direction.equals("Left")) snakeHeadColumn++;
        else if (direction.equals("Right")) snakeHeadColumn--;

        //תנאי עצירה
        if (snakeHeadRow < 0 || snakeHeadRow >= rows ||
                snakeHeadColumn < 0 || snakeHeadColumn >= columns) return false;
        if (direction.equals("Up") && matGameGrid[snakeHeadRow - 1][snakeHeadColumn] == SNAKE) return false;
        if (direction.equals("Down") && matGameGrid[snakeHeadRow + 1][snakeHeadColumn] == SNAKE) return false;
        if (direction.equals("Right") && matGameGrid[snakeHeadRow][snakeHeadColumn - 1] == SNAKE) return false;
        if (direction.equals("Left") && matGameGrid[snakeHeadRow][snakeHeadColumn + 1] == SNAKE) return false;
        return true;
    }
}