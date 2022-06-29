import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

public class LaunchPanel extends JPanel implements MouseListener, ActionListener {
    static int i = 0;
    GameManager gameManager;
    GameBoard gameBoard;
    JFrame launchWindow;
    long start = 0, end = 0;
    static final int BOX_WIDTH = 80;
    static final int BOX_HEIGHT = 80;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 800;
    private String algo;

    private boolean chooseAlgorithm;
    char[][] init = new char[3][3];
    char[][] goal = new char[3][3];
    Stack<Character> stInit;
    Stack<Character> stGoal;
    Font font = new Font("David", Font.BOLD, 24);
    Vector<Character> alc;
    Vector<Character> alcGoal;

    JLabel algorithm = new JLabel("Please choose an algorithm");
    JButton bfs = new JButton("BFS");
    JButton dfid = new JButton("DFID");
    JButton aStar = new JButton("A*");
    JButton idaStar = new JButton("IDA*");
    JButton dfbnb = new JButton("DFBnB");
    JButton next = new JButton("Next");

    public LaunchPanel(JFrame frame) {
        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        chooseAlgorithm = false;
        launchWindow = frame;

        algorithm.setBounds(200, 50,200, 50);

        bfs.setFocusable(false);
        bfs.setBounds(200, 100, 80, 50);
        bfs.addActionListener(this);

        dfid.setFocusable(false);
        dfid.setBounds(200, 155, 80, 50);
        dfid.addActionListener(this);

        aStar.setFocusable(false);
        aStar.setBounds(200, 210, 80, 50);
        aStar.addActionListener(this);

        idaStar.setFocusable(false);
        idaStar.setBounds(200, 265, 80, 50);
        idaStar.addActionListener(this);

        dfbnb.setFocusable(false);
        dfbnb.setBounds(200, 320, 80, 50);
        dfbnb.addActionListener(this);

        next.setFocusable(false);
        next.setBounds(350, 250, 150, 75);
        next.addActionListener(this);

        this.setBackground(Color.white);
        this.addMouseListener(this);


        stInit = new Stack<>();
        stGoal = new Stack<>();
        fillStackInValidWayInit(stInit);
        fillStackInValidWayGoal(stGoal);
        for (char[] chars : init) {
            Arrays.fill(chars, '_');
        }
        for (char[] chars : goal) {
            Arrays.fill(chars, '_');
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (!stInit.isEmpty()) {
            g2d.setFont(font);
            g2d.drawString("Initial State", 50, 90);
            Collections.reverse(alc);
            drawStack(g2d, alc);
            drawMatrix(g2d, init);
        } else if (!stGoal.isEmpty()) {
            MatrixDrawer.printInit(g2d, init, font, 3);
            g2d.setFont(font);
            g2d.drawString("Goal State", 50, 90);
            Collections.reverse(alcGoal);
            drawStack(g2d, alcGoal);
            drawMatrix(g2d, goal);
        } else if (!chooseAlgorithm) {
            MatrixDrawer.printInit(g2d, init, new Font("David", Font.BOLD, 10), 3);
            MatrixDrawer.printGoal(g2d, goal, new Font("David", Font.BOLD, 10), 3);
            this.add(algorithm);
            this.add(dfbnb);
            this.add(aStar);
            this.add(idaStar);
            this.add(dfid);
            this.add(bfs);

        } else {
            algorithm.setVisible(false);
            dfbnb.setVisible(false);
            aStar.setVisible(false);
            dfid.setVisible(false);
            bfs.setVisible(false);
            idaStar.setVisible(false);

            next.setBounds(350, 250, 150, 75);
            this.add(next);
            MatrixDrawer.printInit(g2d, init, new Font("David", Font.BOLD, 10), 3);
            MatrixDrawer.printGoal(g2d, goal, new Font("David", Font.BOLD, 10), 3);
        }
    }

    private void drawStack(Graphics2D g2d, Vector<Character> alc) {
        int x = 400, y = 100;
        g2d.setFont(font);
        g2d.drawString("Stack", 400, 90);
        Collections.reverse(alc);
        for (Character ch : alc) {
            MatrixDrawer.drawRectangleByString(ch, g2d, x, y, 100, 25);
            y += 25;
            g2d.setFont(font);
            g2d.setColor(Color.black);
            g2d.drawString(String.valueOf(ch), x + 5, y - 5);
        }
        for (int i = 100; i < 100 + 6 * 25; i += 25) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.black);
            g2d.drawRect(x, i, 100, 25);
        }

    }


    private void drawMatrix(Graphics2D g2d, char[][] mat) {
        int indexI = 0, indexJ = 0;
        Font font = new Font("MV Boli", Font.BOLD, 26);
        for (int i = 0; i < 3 * BOX_WIDTH; i += BOX_WIDTH) {
            for (int j = 0; j < 3 * BOX_HEIGHT; j += BOX_HEIGHT) {
                int currX = i + 50;
                int currY = j + 100;
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRect(currX, currY, BOX_WIDTH, BOX_HEIGHT);
                MatrixDrawer.drawRectangleByString(mat[indexI][indexJ], g2d, currX, currY, BOX_WIDTH, BOX_HEIGHT);
                g2d.setFont(font);
                g2d.setColor(Color.black);
                g2d.drawString(String.valueOf(mat[indexI][indexJ++]), currX + 30, currY + 45);
            }
            indexJ = 0;
            indexI++;

        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!stInit.isEmpty()) {
            fromStackToMat(e, init, stInit, alc);

        } else {
            if (!stGoal.isEmpty()) {
                fromStackToMat(e, goal, stGoal, alcGoal);
            }
        }
    }

    private void fromStackToMat(MouseEvent e, char[][] mat, Stack<Character> st, Vector<Character> alc) {
        if (e.getX() >= 50 && e.getY() >= 100 && e.getX() <= 290 && e.getY() <= 340) {
            int x = (e.getX() - 50) / BOX_WIDTH;
            int y = (e.getY() - 100) / BOX_HEIGHT;
            if (mat[x][y] == '_') {
                mat[x][y] = st.pop();
                alc.remove(0);

            }
            repaint();
        }
    }

    private void fillStackInValidWayGoal(Stack<Character> st) {
        alcGoal = new Vector<>();
        alcGoal.add('R');
        alcGoal.add('R');

        alcGoal.add('B');
        alcGoal.add('B');

        alcGoal.add('G');
        alcGoal.add('G');

        st.addAll(alcGoal);
        Collections.reverse(st);
    }

    private void fillStackInValidWayInit(Stack<Character> st) {
        alc = new Vector<>();
        alc.add('R');
        alc.add('R');

        alc.add('B');
        alc.add('B');

        alc.add('G');
        alc.add('G');


        st.addAll(alc);
        Collections.reverse(st);

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bfs) {
            algo = "BFS";
            chooseAlgorithm = true;
            repaint();
        }
        if (e.getSource() == dfid) {
            algo = "DFID";
            chooseAlgorithm = true;
            repaint();
        }
        if (e.getSource() == aStar) {
            algo = "A*";
            chooseAlgorithm = true;
            repaint();
        }
        if (e.getSource() == idaStar) {
            algo = "IDA*";
            chooseAlgorithm = true;
            repaint();
        }
        if (e.getSource() == dfbnb) {
            algo = "DFBnB";
            chooseAlgorithm = true;
            repaint();
        }
        /*
        To Implement a new thread for the second screen after dispose!!!
        Bad bug has been fixed :]
         */
        if (e.getSource() == next) {
            launchWindow.dispose();
            init();
            Thread thread = new Thread(new MyFrame(gameManager, gameBoard));
            thread.start();
        }
    }


    private void init() {
        gameBoard = new GameBoard(init, goal);
        gameManager = new GameManager(init, algo, goal);
//        a = new Algorithms(frame, gameBoard, gameManager);
    }
}
