import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Board board;

    public GamePanel(Board board) {
        this.board = board;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.drawGrid(g);  // Method to draw the grid on the panel
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(StaticVars.WINDOW_WIDTH, StaticVars.WINDOW_HEIGHT);  // Setting the preferred size of the panel
    }
}
