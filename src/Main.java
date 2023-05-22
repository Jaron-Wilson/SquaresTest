import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        Board board = new Board();
        GameFrame frame = new GameFrame();
        GamePanel panel = new GamePanel(board);
        GameMain game = new GameMain(board,panel,frame,main);

        frame.add(panel);
        frame.pack();
//        Call After so it can show it better.
        frame.setUpWindow();
//        Example to call.
        //board.setActive(2,2);
        //board.setInactive(2,2);






    }
}