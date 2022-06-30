import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class MyFrame extends JFrame implements Runnable {
    /**
     * Frame of the screen.
     */
    GameManager gameManager;
    GameBoard gameBoard;
    Algorithms algorithms;
    long end = 0 , start = 0;


    public MyFrame(GameManager gameManager)  {
        MatrixDrawer matrixDrawer = new MatrixDrawer(gameManager);
//            velocityButton = new JButton("Run Faster");
//            velocityButton.setBounds(800,400, 70,70);
//            velocityButton.addActionListener(this);
        this.gameManager = gameManager;
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(LaunchPanel.SCREEN_WIDTH, LaunchPanel.SCREEN_HEIGHT);
        this.setTitle("Ball Game");
        this.setVisible(true);
        this.setBackground(Color.white);
        this.add(matrixDrawer);
    }


    public MyFrame(GameManager gameManager, GameBoard gameBoard) {
        this.gameManager = gameManager;
        this.gameBoard = gameBoard;
        this.algorithms = new Algorithms(this,gameBoard,gameManager);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(LaunchPanel.SCREEN_WIDTH, LaunchPanel.SCREEN_HEIGHT);
        this.setTitle("Ball Game");
        this.setVisible(true);
        this.setBackground(Color.white);
        this.add(new MatrixDrawer(gameManager));
    }

    @Override
    public void run() {
        switch (gameManager.getInfo()) {
            case "BFS":
                start = System.currentTimeMillis();
                algorithms.bfs(gameBoard);
                end = System.currentTimeMillis();
                break;
            case "DFID":
                start = System.currentTimeMillis();
                algorithms.dfid(gameBoard);
                end = System.currentTimeMillis();
                break;
            case ("A*"):
                start = System.currentTimeMillis();
                algorithms.aStar(gameBoard);
                end = System.currentTimeMillis();
                break;
            case ("IDA*"):
                start = System.currentTimeMillis();
                algorithms.idaStar(gameBoard);
                end = System.currentTimeMillis();
                break;
            case ("DFBnB"):
                start = System.currentTimeMillis();
                algorithms.dfbnb(gameBoard);
                end = System.currentTimeMillis();
                break;
        }
        double elapsedTime = (end - start);
        elapsedTime /= 1000;

        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write(algorithms.getOutputPath() + "\nNum: " + algorithms.getNum() + "\nCost: " + algorithms.getCost() + "\n" + elapsedTime + " seconds");
            fw.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}

