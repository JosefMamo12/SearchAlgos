import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixDrawer extends JPanel {
    boolean flag = false;
    Font font = new Font("MV Boli", Font.BOLD, 14);
    GameManager gameManager;
    static int boxWidth = 85;
    static int boxHeight = 85;
    int rowColumnLength;
    JButton sButton;
    JButton vButton;


    public MatrixDrawer(GameManager gameManager) {
        this.gameManager = gameManager;
        rowColumnLength = gameManager.getGameMat().length;
        sButton = new JButton();
        vButton = new JButton();
        this.setLayout(null);
        this.setBounds(0, 0, LaunchPanel.SCREEN_WIDTH, LaunchPanel.SCREEN_HEIGHT);
//        this.setBorder(border);
        this.add(vButton);
        this.add(sButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        vButton.setBounds(LaunchPanel.SCREEN_WIDTH - 200, 100, 80, 80);
        vButton.setText("Faster");
        vButton.addActionListener(new myActionListener());
        sButton.setBounds(LaunchPanel.SCREEN_WIDTH - 200, 200, 80, 80);
        sButton.setText("Slower");
        sButton.addActionListener(new myActionListener());

        printInit(g, gameManager.getInitState(), font, rowColumnLength);
        printGoal(g, gameManager.getGoalState(), font, rowColumnLength);
        printMatrix(g);
        printScore(g);
        g.setFont(new Font("David", Font.BOLD, 14));
        g.drawString(Algorithms.outputPath, 0, 700);

    }

    public static void printGoal(Graphics g, char[][] goalState, Font font, int rowColumnLength) {
        int indexI = 0, indexJ = 0;
        int paddleX, paddleY;
        int smallBoxHeight = boxHeight / 3;
        int smallBoxWidth = boxHeight / 3;
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < rowColumnLength * smallBoxWidth; i += smallBoxWidth) {
            for (int j = 0; j < rowColumnLength * smallBoxWidth; j += smallBoxHeight) {
                if (rowColumnLength == 3) {
                    paddleX = 150 + i;
                    paddleY = 500 + j;
                    g2d.setFont(new Font("David", Font.BOLD, 20));
                    g2d.drawString("Goal", 150, 480);
                } else {
                    paddleX = 200 + i;
                    paddleY = 600 + j;
                    g2d.setFont(new Font("David", Font.BOLD, 20));
                    g2d.drawString("Goal", 200, 580);
                }

                matrixCreator(goalState[indexJ][indexI], font, paddleX, paddleY, smallBoxWidth, smallBoxHeight, g2d);
                g2d.drawString(String.valueOf(goalState[indexJ++][indexI]), paddleX + 10, paddleY + 20);
            }
            indexJ = 0;
            indexI++;
        }
    }

    public static void printInit(Graphics g, char[][] initState, Font font, int rowColumnLength) {
        int indexI = 0, indexJ = 0;
        int paddleX, paddleY;
        int smallBoxHeight = boxHeight / 3;
        int smallBoxWidth = boxHeight / 3;
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < rowColumnLength * smallBoxWidth; i += smallBoxWidth) {
            for (int j = 0; j < rowColumnLength * smallBoxWidth; j += smallBoxHeight) {
                if (rowColumnLength == 3) {
                    paddleX = 50 + i;
                    paddleY = 500 + j;
                    g2d.setFont(new Font("David", Font.BOLD, 20));
                    g2d.drawString("Initial", 50, 480);
                } else {
                    paddleX = 50 + i;
                    paddleY = 600 + j;
                    g2d.setFont(new Font("David", Font.BOLD, 20));
                    g2d.drawString("Initial", 50, 580);
                }

                matrixCreator(initState[indexI][indexJ], font, paddleX, paddleY, smallBoxWidth, smallBoxHeight, g2d);
                g2d.drawString(String.valueOf(initState[indexI][indexJ++]), paddleX + 10, paddleY + 20);
            }
            indexJ = 0;
            indexI++;
        }
    }

    public static void matrixCreator(char c, Font font, int paddleX, int paddleY, int boxWidth, int boxHeight, Graphics2D g2d) {
        drawRectangleByString(c, g2d, paddleX, paddleY, boxWidth, boxHeight);

        BasicStroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        g2d.setColor(new Color(0, 0, 0));
        g2d.drawRect(paddleX, paddleY, boxWidth, boxHeight);
        g2d.setFont(font);
        g2d.setColor(new Color(0, 0, 0));
    }

    private void printScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("David", Font.BOLD, 26));
        g.drawString("Score: " + gameManager.getScore(), 50, 80);
        g.setColor(Color.BLACK);
        g.setFont(new Font("David", Font.BOLD, 60));
        g.drawString(gameManager.getInfo(), 50, 50);
        if (gameManager.getInfo().equals("DFBnB") || gameManager.getInfo().equals("IDA*")) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("David", Font.BOLD, 26));
            if (gameManager.getThreshold() != Integer.MAX_VALUE) {
                g.drawString("Threshold: " + gameManager.getThreshold(), 250, 80);
            } else {
                g.drawString("Threshold: Infinity", 250, 80);
            }
        }

    }

    private void printMatrix(Graphics g) {
        int indexI = 0, indexJ = 0;
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("MV Boli", Font.BOLD, 26);
        for (int i = 0; i < rowColumnLength * boxWidth; i += boxWidth) {
            for (int j = 0; j < rowColumnLength * boxHeight; j += boxHeight) {
                int currX = i + 50;
                int currY = j + 100;

                matrixCreator(gameManager.getGameMat()[indexJ][indexI], font, currX, currY, boxWidth, boxHeight, g2d);
                g2d.drawString(String.valueOf(gameManager.getGameMat()[indexJ++][indexI]), currX + 30, currY + 60);
            }
            indexJ = 0;
            indexI++;
        }
    }

    private void printMat() {
        for (int i = 0; i < rowColumnLength; i++) {
            for (int j = 0; j < rowColumnLength; j++) {
                System.out.print(gameManager.getGameMat()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void drawRectangleByString(char c, Graphics2D g2d, int currX, int currY, int boxWidth, int boxHeight) {
        switch (c) {
            case 'G':
                g2d.setColor(new Color(0, 255, 0));
                g2d.fillRect(currX, currY, boxWidth, boxHeight);
                break;
            case 'R':
                g2d.setColor(new Color(255, 0, 0));
                g2d.fillRect(currX, currY, boxWidth, boxHeight);
                break;
            case 'B':
                g2d.setColor(new Color(0, 0, 255));
                g2d.fillRect(currX, currY, boxWidth, boxHeight);
                break;
            case 'Y':
                g2d.setColor(Color.yellow);
                g2d.fillRect(currX, currY, boxWidth, boxHeight);
                break;
            default:
                g2d.setColor(Color.white);
                g2d.fillRect(currX, currY, boxWidth, boxHeight);
        }
    }

    private class myActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == vButton) {
                gameManager.setVelocityUp();
            }
            if (e.getSource() == sButton) {
                gameManager.setVelocityDown();
            }
        }

    }
}
