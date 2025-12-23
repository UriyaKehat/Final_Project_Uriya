package com.example.final_project_uriya;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameLogic {

    public static final int EMPTY = 0, APPLE = 1;
    public static final int TAIL_MARK_UP = 2, TAIL_MARK_DOWN = 3, TAIL_MARK_LEFT = 4, TAIL_MARK_RIGHT = 5;

    public static int rows = 11, columns = 11;
    public static int[][] matGameGrid = new int[rows][columns];

    public static LinkedList<Point> snake = new LinkedList<>();
    public static String currentDirection = "Right";

    private static Random random = new Random();

    static {
        // התחלת הנחש
        snake.add(new Point(5, 3));
        snake.add(new Point(5, 4));
        snake.add(new Point(5, 5));
        for (Point p : snake) matGameGrid[p.row][p.col] = TAIL_MARK_RIGHT;

        // תפוחים התחלתיים
        matGameGrid[2][8] = APPLE;
        matGameGrid[5][8] = APPLE;
        matGameGrid[8][8] = APPLE;
    }

    public static boolean MoveSnake(String direction) {
        currentDirection = direction;
        Point head = snake.getLast();
        int newRow = head.row;
        int newCol = head.col;

        if (direction.equals("Up")) newRow--;
        else if (direction.equals("Down")) newRow++;
        else if (direction.equals("Left")) newCol--;
        else if (direction.equals("Right")) newCol++;

        // בדיקת גבולות
        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= columns) return false;

        // בדיקת התנגשות בעצמי
        if (matGameGrid[newRow][newCol] != EMPTY && matGameGrid[newRow][newCol] != APPLE)
            return false;

        boolean ateApple = (matGameGrid[newRow][newCol] == APPLE);

        // הוספת ראש חדש
        snake.addLast(new Point(newRow, newCol));

        if (!ateApple) {
            // הסרת זנב
            Point tail = snake.removeFirst();
            matGameGrid[tail.row][tail.col] = EMPTY;
        } else {
            // אם אכל תפוח, צור תפוח חדש רנדומלי
            createRandomApple();
        }

        // צביעת ראש הנחש החדש
        if (direction.equals("Up")) matGameGrid[newRow][newCol] = TAIL_MARK_UP;
        else if (direction.equals("Down")) matGameGrid[newRow][newCol] = TAIL_MARK_DOWN;
        else if (direction.equals("Left")) matGameGrid[newRow][newCol] = TAIL_MARK_LEFT;
        else if (direction.equals("Right")) matGameGrid[newRow][newCol] = TAIL_MARK_RIGHT;

        return true;
    }

    // יצירת תפוח חדש רנדומלי
    private static void createRandomApple() {
        ArrayList<Point> emptyCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matGameGrid[i][j] == EMPTY) {
                    emptyCells.add(new Point(i, j));
                }
            }
        }

        if (emptyCells.size() == 0) return; // אין מקום להוסיף תפוח

        Point chosen = emptyCells.get(random.nextInt(emptyCells.size()));
        matGameGrid[chosen.row][chosen.col] = APPLE;
    }

    public static class Point {
        public int row, col;
        public Point(int r, int c) { row = r; col = c; }
    }
}
