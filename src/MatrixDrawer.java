import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixDrawer extends JPanel {
    Border border = BorderFactory.createLineBorder(Color.green);
    GameManager gameManager;
    static int boxWidth = 120;
    static int boxHeight = 120;
    int rowColumnLength;
    JButton sButton;
    JButton vButton;




    public MatrixDrawer(GameManager gameManager) {
        this.gameManager = gameManager;
        rowColumnLength = gameManager.getGameMat().length;
        sButton = new JButton();
        vButton = new JButton();
        this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        this.setBorder(border);


    }

    @Override
    public void paintComponent(Graphics g) {
        vButton.setBounds(800, 150, 80, 80);
        vButton.setText("Faster");
        vButton.addActionListener(new myActionListener());
        sButton.setBounds(800, 240, 80, 80);
        sButton.setText("Slower");
        sButton.addActionListener(new myActionListener());
        this.add(vButton);
        this.add(sButton);
        if (gameManager.running()) {
            printMatrix(g);
            printScore(g);
        }
    }

    private void printScore(Graphics g) {
        System.out.println(gameManager.getVelocity());
        g.setColor(Color.BLACK);
        g.setFont(new Font("David", Font.BOLD, 26));
        g.drawString("Score: " + gameManager.getScore(), 50, 100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("David", Font.BOLD, 60));
        g.drawString(gameManager.getGameInfo().getAlgo(), 50,  50);
        if(gameManager.getGameInfo().getAlgo().equals("DFBnB") || gameManager.getGameInfo().getAlgo().equals("IDA*")){
            g.setColor(Color.BLACK);
            g.setFont(new Font("David", Font.BOLD, 26));
            if(gameManager.getThreshold() != Integer.MAX_VALUE) {
                g.drawString("Threshold: " + gameManager.getThreshold(), 300, 100);
            }else{
                g.drawString("Threshold: Infinity" , 300, 100);
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
                int currY = j + 150;

                switch (gameManager.getGameMat()[indexJ][indexI]) {
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
                        g2d.fillRect(currX,currY,boxWidth,boxHeight);
                }
                BasicStroke stroke = new BasicStroke(3);
                g2d.setStroke(stroke);
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawRect(currX, currY, boxWidth, boxHeight);
                g2d.setFont(font);
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawString(String.valueOf(gameManager.getGameMat()[indexJ++][indexI]), currX + 45, currY + 60);
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

    private class myActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()  == vButton){
                System.out.println("Pepe");
                gameManager.setVelocityUp();
            }
            if(e.getSource() == sButton){
                System.out.println("Popo");
                gameManager.setVelocityDown();
            }
        }
    }
}
