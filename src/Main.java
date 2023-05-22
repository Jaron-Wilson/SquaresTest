import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        GameFrame frame = new GameFrame();
        Board board = new Board(frame);
        GamePanel panel = new GamePanel(board);
        GameMain game = new GameMain(board,panel,frame,main);
        Animation animation = new Animation(board, frame);

        frame.add(panel);
        frame.pack();
//        Call After so it can show it better.
        frame.setUpWindow();

        board.setActive(0,0);


        animation.startAnimation(2, 2, true); // Start animation for the square at position (2, 2)

//        Example to call.
        //board.setActive(2,2);
        //board.setInactive(2,2);






    }
}