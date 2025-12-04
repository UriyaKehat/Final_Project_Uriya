
package com.example.final_project_uriya;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameGrid {

    private GridLayout gridLayout;
    private int rows, columns, tileSize;
    private Context context;

    public GameGrid(Context context, GridLayout gridLayout, int rows, int columns, int tileSize) {
        this.context = context;
        this.gridLayout = gridLayout;
        this.rows = rows;
        this.columns = columns;
        this.tileSize = tileSize;

        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(columns);
    }

    public void buildGrid(int[][] matGameGrid) {
        gridLayout.removeAllViews();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                ImageView img = new ImageView(context);

                //נחש
                if (matGameGrid[i][j] == GameLogic.TAIL_MARK_UP ||
                    matGameGrid[i][j] == GameLogic.TAIL_MARK_DOWN ||
                    matGameGrid[i][j] == GameLogic.TAIL_MARK_RIGHT ||
                    matGameGrid[i][j] == GameLogic.TAIL_MARK_LEFT)
                        img.setImageResource(R.drawable.lime_square);
                //ריק
                else if (matGameGrid[i][j] == GameLogic.EMPTY)
                    img.setImageResource(R.drawable.gray_square);
                //תפוח
                else if (matGameGrid[i][j] == GameLogic.APPLE)
                    img.setImageResource(R.drawable.red_square);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = tileSize;
                params.height = tileSize;
                params.setMargins(1, 1, 1, 1);

                gridLayout.addView(img, params);
            }
        }
    }
}