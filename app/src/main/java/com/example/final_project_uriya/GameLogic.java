package com.example.final_project_uriya;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameLogic {

    // ★ NEW – Interface במקום Activity
    public interface GameEventsListener {
        void onGameWon();
    }

    private GameEventsListener listener; // ★ NEW

    public static final int EMPTY = 0, APPLE = 1;
    public static final int TAIL_MARK_UP = 2, TAIL_MARK_DOWN = 3,
            TAIL_MARK_LEFT = 4, TAIL_MARK_RIGHT = 5;

    public int rows = 11, columns = 10;
    public int[][] matGameGrid = new int[rows][columns];

    public LinkedList<Point> snake = new LinkedList<>();
    public String currentDirection = "Right";

    private Random random = new Random();

    public GameLogic(GameEventsListener listener) {
        this.listener = listener;

        snake.add(new Point(5, 2));
        snake.add(new Point(5, 3));
        snake.add(new Point(5, 4));

        for (Point p : snake)
            matGameGrid[p.row][p.col] = TAIL_MARK_RIGHT;

        LayApples();
    }

    public boolean MoveSnake(String direction) {
        currentDirection = direction;

        Point head = snake.getLast();
        int newRow = head.row;
        int newCol = head.col;

        if (direction.equals("Up")) newRow--;
        else if (direction.equals("Down")) newRow++;
        else if (direction.equals("Left")) newCol--;
        else if (direction.equals("Right")) newCol++;

        // גבולות
        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= columns)
            return false;

        // התנגשות בעצמי
        if (matGameGrid[newRow][newCol] != EMPTY &&
                matGameGrid[newRow][newCol] != APPLE)
            return false;

        boolean ateApple = (matGameGrid[newRow][newCol] == APPLE);

        snake.addLast(new Point(newRow, newCol));

        if (!ateApple) {
            Point tail = snake.removeFirst();
            matGameGrid[tail.row][tail.col] = EMPTY;
        } else {
            createRandomApple();
        }

        if (direction.equals("Up")) matGameGrid[newRow][newCol] = TAIL_MARK_UP;
        else if (direction.equals("Down")) matGameGrid[newRow][newCol] = TAIL_MARK_DOWN;
        else if (direction.equals("Left")) matGameGrid[newRow][newCol] = TAIL_MARK_LEFT;
        else if (direction.equals("Right")) matGameGrid[newRow][newCol] = TAIL_MARK_RIGHT;

        return true;
    }

    private void createRandomApple() {
        ArrayList<Point> emptyCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matGameGrid[i][j] == EMPTY)
                    emptyCells.add(new Point(i, j));
            }
        }

        if (emptyCells.size() == 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (matGameGrid[i][j] == APPLE)
                        emptyCells.add(new Point(i, j));
                }
            }
            if(emptyCells.size() == 1)
            {
            if (listener != null)
                listener.onGameWon();}
        } else {
            Point chosen = emptyCells.get(random.nextInt(emptyCells.size()));
            matGameGrid[chosen.row][chosen.col] = APPLE;
        }
    }

    public void resetGame() {
        matGameGrid = new int[rows][columns];

        snake.clear();
        snake.add(new Point(5, 2));
        snake.add(new Point(5, 3));
        snake.add(new Point(5, 4));

        for (Point p : snake)
            matGameGrid[p.row][p.col] = TAIL_MARK_RIGHT;

        LayApples();
        currentDirection = "Right";
    }

    public void LayApples() {
        if (SettingsActivity.appleAmountValue == 1)
            matGameGrid[5][8] = APPLE;
        if (SettingsActivity.appleAmountValue == 2) {
            matGameGrid[4][8] = APPLE;
            matGameGrid[6][8] = APPLE;
        }
        if (SettingsActivity.appleAmountValue == 3) {
            matGameGrid[2][8] = APPLE;
            matGameGrid[5][8] = APPLE;
            matGameGrid[8][8] = APPLE;
        }
        if (SettingsActivity.appleAmountValue == 4) {
            matGameGrid[4][8] = APPLE;
            matGameGrid[4][6] = APPLE;
            matGameGrid[6][6] = APPLE;
            matGameGrid[6][8] = APPLE;
        }
        if (SettingsActivity.appleAmountValue == 5) {
            matGameGrid[3][8] = APPLE;
            matGameGrid[5][8] = APPLE;
            matGameGrid[7][8] = APPLE;
            matGameGrid[6][6] = APPLE;
            matGameGrid[4][6] = APPLE;

            //
            matGameGrid[0][0] = APPLE;
            matGameGrid[0][1] = APPLE;
            matGameGrid[0][2] = APPLE;
            matGameGrid[0][3] = APPLE;
            matGameGrid[0][4] = APPLE;
            matGameGrid[0][5] = APPLE;
            matGameGrid[0][6] = APPLE;
            matGameGrid[0][7] = APPLE;
            matGameGrid[0][8] = APPLE;
            matGameGrid[0][9] = APPLE;

            matGameGrid[1][0] = APPLE;
            matGameGrid[1][1] = APPLE;
            matGameGrid[1][2] = APPLE;
            matGameGrid[1][3] = APPLE;
            matGameGrid[1][4] = APPLE;
            matGameGrid[1][5] = APPLE;
            matGameGrid[1][6] = APPLE;
            matGameGrid[1][7] = APPLE;
            matGameGrid[1][8] = APPLE;
            matGameGrid[1][9] = APPLE;

            matGameGrid[2][0] = APPLE;
            matGameGrid[2][1] = APPLE;
            matGameGrid[2][2] = APPLE;
            matGameGrid[2][3] = APPLE;
            matGameGrid[2][4] = APPLE;
            matGameGrid[2][5] = APPLE;
            matGameGrid[2][6] = APPLE;
            matGameGrid[2][7] = APPLE;
            matGameGrid[2][8] = APPLE;
            matGameGrid[2][9] = APPLE;

            matGameGrid[3][0] = APPLE;
            matGameGrid[3][1] = APPLE;
            matGameGrid[3][2] = APPLE;
            matGameGrid[3][3] = APPLE;
            matGameGrid[3][4] = APPLE;
            matGameGrid[3][5] = APPLE;
            matGameGrid[3][6] = APPLE;
            matGameGrid[3][7] = APPLE;
            matGameGrid[3][9] = APPLE;
        }
    }

    public static class Point {
        public int row, col;
        public Point(int r, int c) {
            row = r;
            col = c;
        }
    }
}
