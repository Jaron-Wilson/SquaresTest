import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 5;
    private static final int CELL_SIZE = 40;
    private static final int FRAME_WIDTH = GRID_WIDTH * CELL_SIZE;
    private static final int FRAME_HEIGHT = GRID_HEIGHT * CELL_SIZE;
    private boolean[][] grid;
    private boolean isRunning;
    private GamePanel panel;
    private Timer timer;

    public GameOfLife() {
        grid = new boolean[GRID_WIDTH][GRID_HEIGHT];
        isRunning = false;
        panel = new GamePanel();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGeneration();
                panel.repaint();
            }
        });

        initializeGrid();

        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.add(panel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    isRunning = true;
                    startButton.setText("Stop");
                    timer.start();
                } else {
                    isRunning = false;
                    startButton.setText("Start");
                    timer.stop();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void initializeGrid() {
        grid = new boolean[GRID_WIDTH][GRID_HEIGHT];

        // Set the initial live cells
        grid[4][1] = true;
        grid[5][1] = true;
        grid[6][1] = true;
        grid[4][2] = true;

        panel.repaint();
    }

    private void updateGeneration() {
        boolean[][] newGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];

        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (grid[i][j]) {
                    // Any live cell with fewer than two live neighbors dies (underpopulation)
                    // Any live cell with two or three live neighbors lives on to the next generation
                    // Any live cell with more than three live neighbors dies (overpopulation)
                    newGrid[i][j] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    // Any dead cell with exactly three live neighbors becomes a live cell (reproduction)
                    newGrid[i][j] = liveNeighbors == 3;
                }
            }
        }

        grid = newGrid;
    }

    private int countLiveNeighbors(int x, int y) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < GRID_WIDTH && neighborY >= 0 && neighborY < GRID_HEIGHT) {
                    if (grid[neighborX][neighborY]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < GRID_WIDTH; i++) {
                for (int j = 0; j < GRID_HEIGHT; j++) {
                    if (grid[i][j]) {
                        g.setColor(Color.BLACK);
                        g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameOfLife();
            }
        });
    }
}