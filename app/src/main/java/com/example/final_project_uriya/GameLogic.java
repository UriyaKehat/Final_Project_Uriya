
package com.example.final_project_uriya;

public class GameLogic {
    public static final int EMPTY = 0, APPLE = 1,
                            TAIL_MARK_UP = 2,
                            TAIL_MARK_DOWN = 3,
                            TAIL_MARK_RIGHT = 4,
                            TAIL_MARK_LEFT = 5;
    public static int snakeHeadRow = 5, snakeHeadColumn = 3, snakeTailRow = 5, snakeTailColumn = 1;
    public static int[][] matGameGrid = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 5, 5, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public static int rows = matGameGrid.length;
    public static int columns = matGameGrid[0].length;
    public static boolean MoveSnake(String direction) {
        //בדיקת כיוון
        if (direction.equals("Up")) {
            snakeHeadRow--;
            matGameGrid[snakeHeadRow][snakeHeadColumn] = TAIL_MARK_UP;
        }
        else if (direction.equals("Down")) {
            snakeHeadRow++;
            matGameGrid[snakeHeadRow][snakeHeadColumn] = TAIL_MARK_DOWN;
        }
        else if (direction.equals("Left")) {
            snakeHeadColumn++;
            matGameGrid[snakeHeadRow][snakeHeadColumn] = TAIL_MARK_LEFT;
        }
        else if (direction.equals("Right")) {
            snakeHeadColumn--;
            matGameGrid[snakeHeadRow][snakeHeadColumn] = TAIL_MARK_RIGHT;
        }

        //כשהנחש לא אוכל תפוח (לא צריך לגדול)
        if (direction.equals("Up") && matGameGrid[snakeHeadRow - 1][snakeHeadColumn] != APPLE ||
                direction.equals("Down") && matGameGrid[snakeHeadRow + 1][snakeHeadColumn] != APPLE ||
                direction.equals("Right") && matGameGrid[snakeHeadRow][snakeHeadColumn - 1] != APPLE ||
                direction.equals("Left") && matGameGrid[snakeHeadRow][snakeHeadColumn + 1] != APPLE) {
            matGameGrid[snakeTailRow][snakeTailColumn] = 0;
            //הזזת זנב הנחש
            if(matGameGrid[snakeTailRow - 1][snakeTailColumn] == TAIL_MARK_UP) snakeTailRow--;
            else if(matGameGrid[snakeTailRow + 1][snakeTailColumn] == TAIL_MARK_DOWN) snakeTailRow++;
            else if(matGameGrid[snakeTailRow][snakeTailColumn - 1] == TAIL_MARK_RIGHT) snakeTailColumn--;
            else if(matGameGrid[snakeTailRow][snakeTailColumn + 1] == TAIL_MARK_LEFT) snakeTailColumn++;
        }
        //תנאי עצירה יציאה מהגבולות
        if (snakeHeadRow < 0 || snakeHeadRow >= rows ||
                snakeHeadColumn < 0 || snakeHeadColumn >= columns) return false;

        //תנאי עצירה נחש נכנסס בעצמו
        if (direction.equals("Up") && matGameGrid[snakeHeadRow - 1][snakeHeadColumn] == TAIL_MARK_UP) return false;
        if (direction.equals("Down") && matGameGrid[snakeHeadRow + 1][snakeHeadColumn] == TAIL_MARK_DOWN) return false;
        if (direction.equals("Right") && matGameGrid[snakeHeadRow][snakeHeadColumn - 1] == TAIL_MARK_RIGHT) return false;
        if (direction.equals("Left") && matGameGrid[snakeHeadRow][snakeHeadColumn + 1] == TAIL_MARK_LEFT) return false;

        return true;
    }
}