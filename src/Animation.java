import java.awt.*;
import javax.swing.Timer;

public class Animation {
    private static final int ANIMATION_DELAY = 1000; // Delay between animation steps in milliseconds
    private final Color[] COLOR_MAP = {Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE};

    private boolean[][] grid;
    private Timer timer;
    private int animationStep;
    private Board board;
    private GameFrame panel; // Reference to the GUI panel

    public Animation(Board board, GameFrame panel) {
        this.board = board;
        this.panel = panel;
        grid = board.getGrid();
        animationStep = 0;

        // Create a timer to update the animation at regular intervals
        timer = new Timer(ANIMATION_DELAY, e -> animateColorTransition());
    }

    public void startAnimation(int x, int y, boolean active) {
        if (x >= 0 && x < StaticVars.GRID_WIDTH && y >= 0 && y < StaticVars.GRID_WIDTH) {
            if (active) {
                board.setActive(x, y);
                System.out.println("active: " + x + ", " + y);
            } else {
                board.setInactive(x, y);
                System.out.println("inactive: " + x + ", " + y);
            }
            animationStep = 0;
            timer.start();
        }
    }

    private void animateColorTransition() {
        if (animationStep < 3) {
            for (int i = 0; i < StaticVars.GRID_WIDTH; i++) {
                for (int j = 0; j < StaticVars.GRID_WIDTH; j++) {
                    if (grid[i][j]) {
                        grid[i][j] = false;
                    }
                }
            }
        } else {
            timer.stop();
        }

        animationStep++;
        panel.repaint();
    }

    public int getAnimationStepForRowCol(int row, int col) {
        // Calculate the animation step based on row and column
        // For example, you can use row and column values to determine the step
        // and return the appropriate animation step value for the cell

        // Calculate the distance from the center of the grid
        int centerRow = StaticVars.GRID_WIDTH / 2;
        int centerCol = StaticVars.GRID_WIDTH / 2;
        int distance = Math.max(Math.abs(row - centerRow), Math.abs(col - centerCol));

        // Adjust the distance to fit within the available animation steps (0 to 3)
        int adjustedDistance = Math.min(distance, 3);

        // Return the animation step based on the adjusted distance
        return adjustedDistance;
    }


    public Color[] getCOLOR_MAP() {
        return COLOR_MAP;
    }
}
