package gameOfLife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CombinedProgram {
    static final int GRID_WIDTH = 10;
    private boolean[][] grid;
    private Color[] COLOR_MAP = {Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE};

    private GameFrame frame;
    private Animation animation;

    public CombinedProgram() {
        grid = new boolean[GRID_WIDTH][GRID_WIDTH];
        frame = new GameFrame(this);
        animation = new Animation(this);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / GamePanel.CELL_SIZE;
                int y = e.getY() / GamePanel.CELL_SIZE;
                animation.startAnimation(x, y);
            }
        });

        frame.setVisible(true);
    }

    public void setActive(int x, int y) {
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_WIDTH) {
            grid[x][y] = true;
        }
    }

    public void setInactive(int x, int y) {
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_WIDTH) {
            grid[x][y] = false;
        }
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public Color[] getColorMap() {
        return COLOR_MAP;
    }

    public int getAnimationStepForRowCol(int row, int col) {
        // Calculate the animation step based on row and column
        // For example, you can use row and column values to determine the step
        // and return the appropriate animation step value for the cell

        // Calculate the distance from the center of the grid
        int centerRow = GRID_WIDTH / 2;
        int centerCol = GRID_WIDTH / 2;
        int distance = Math.max(Math.abs(row - centerRow), Math.abs(col - centerCol));

        // Adjust the distance to fit within the available animation steps (0 to 3)
        int adjustedDistance = Math.min(distance, 3);

        // Return the animation step based on the adjusted distance
        return adjustedDistance;
    }

    public void setInactiveAllCells() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                grid[i][j] = false;
            }
        }
    }

    public void repaintGamePanel() {
        frame.repaintGamePanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CombinedProgram::new);
    }
}

class GameFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;

    private GamePanel panel;

    public GameFrame(CombinedProgram program) {
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);

        panel = new GamePanel(program);
        add(panel);
    }

    public void repaintGamePanel() {
        panel.repaint();
    }
}

class GamePanel extends JPanel {
    public static final int CELL_SIZE = 40;

    private CombinedProgram program;

    public GamePanel(CombinedProgram program) {
        this.program = program;
        setPreferredSize(new Dimension(CELL_SIZE * CombinedProgram.GRID_WIDTH, CELL_SIZE * CombinedProgram.GRID_WIDTH));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        boolean[][] grid = program.getGrid();
        Color[] colorMap = program.getColorMap();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int animationStep = program.getAnimationStepForRowCol(i, j);
                Color cellColor = colorMap[animationStep];
                g.setColor(cellColor);
                g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);

            }
        }
    }
}

class Animation {
    private static final int ANIMATION_DELAY = 1000; // Delay between animation steps in milliseconds

    private CombinedProgram program;
    private Timer timer;
    private int animationStep;

    public Animation(CombinedProgram program) {
        this.program = program;
        animationStep = 0;

        // Create a timer to update the animation at regular intervals
        timer = new Timer(ANIMATION_DELAY, e -> animateColorTransition());
    }

    public void startAnimation(int x, int y) {
        if (x >= 0 && x < CombinedProgram.GRID_WIDTH && y >= 0 && y < CombinedProgram.GRID_WIDTH && !program.getGrid()[x][y]) {
            program.setActive(x, y);
            System.out.println("active: " + x + ", " + y);
            animationStep = 0;
            timer.start();
        }
    }


    private void animateColorTransition() {
        if (animationStep < 3) {
            program.setInactiveAllCells();
        } else {
            timer.stop();
        }

        animationStep++;
        program.repaintGamePanel();
    }
}
