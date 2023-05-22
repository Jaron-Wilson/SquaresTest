import java.awt.*;

public class Board {
    private final boolean[][] grid;  // 2D array to represent the grid
    private final GamePanel panel = new GamePanel(this);


    public Board() {
        grid = new boolean[10][10];  // Initializing the grid
        initializeGrid();  // Method to set all elements in the grid to false
        grid[0][0] = true;  // Setting element at position [0][0] to true
    }


    private void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;  // Setting all elements in the grid to false
            }
        }
    }

    void drawGrid(Graphics g) {
        int cellSize = 20;  // Size of each box in the grid
        int numRows = grid.length;
        int numCols = grid[0].length;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * cellSize;  // X-coordinate of the box
                int y = row * cellSize;  // Y-coordinate of the box

                if (grid[row][col]) {
                    g.setColor(Color.BLACK);  // Set color to black if the box is true
                } else {
                    g.setColor(Color.WHITE);  // Set color to white if the box is false
                }

                g.fillRect(x, y, cellSize, cellSize);  // Fill the box with the specified color
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);  // Draw the outline of the box
            }
        }
    }

    public void setActive(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = true;
            panel.repaint();  // Repaint the panel to reflect the changes
        }
    }

    public void setInactive(int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y] = false;
            panel.repaint();  // Repaint the panel to reflect the changes
        }
    }


    public boolean[][] getGrid() {
        return grid;
    }
}
