import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LaunchPanel extends JPanel implements MouseListener, ActionListener {
    GameManager gameManager;
    static final int BOX_WIDTH = 80;
    static final int BOX_HEIGHT = 80;
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 800;
    private String algo;
    char[][] init = new char[3][3];
    char[][] goal = new char[3][3];
    Stack<Character> stInit;
    Stack<Character> stGoal;


    JButton bfs = new JButton("BFS");
    JButton dfid = new JButton("DFID");
    JButton aStar = new JButton("A*");
    JButton idaStar = new JButton("IDA*");
    JButton dfbnb = new JButton("DFBnB");

    public LaunchPanel() {
        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        bfs.setFocusable(false);
        bfs.setBounds(100,100,60,50);
        bfs.addActionListener(this);

        dfid.setFocusable(false);
        dfid.setBounds(100,170,100,100);
        dfid.addActionListener(this);
        aStar.setFocusable(false);
        aStar.setBounds(300,400,100,100);
        aStar.addActionListener(this);
        idaStar.setFocusable(false);
        idaStar.setBounds(300,500,100,100);
        idaStar.addActionListener(this);
        dfbnb.setFocusable(false);
        dfbnb.setBounds(300,700,100,100);
        dfbnb.addActionListener(this);

        this.setBackground(Color.white);
        this.addMouseListener(this);


        stInit = new Stack<>();
        stGoal = new Stack<>();
        fillStackInValidWay(stInit);
        fillStackInValidWay(stGoal);
        for (char[] chars : init) {
            Arrays.fill(chars, '-');
        }
        for (char[] chars : goal) {
            Arrays.fill(chars, '-');
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (!stInit.isEmpty())
            drawMatrix(g2d, init);
        else if(!stGoal.isEmpty()){
            drawMatrix(g2d,goal);
        }else{
            this.add(dfbnb);
            this.add(aStar);
            this.add(idaStar);
            this.add(dfid);
            this.add(bfs);
        }

    }

    private void drawMatrix(Graphics2D g2d,char[][] mat) {
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
            fromStackToMat(e, init, stInit);
        } else {
            if (!stGoal.isEmpty()) {
                fromStackToMat(e, goal, stGoal);
            }

        }
    }

    private void fromStackToMat(MouseEvent e, char[][] mat, Stack<Character> st) {
        if (e.getX() >= 50 && e.getY() >= 100 && e.getX() <= 290 && e.getY() <= 340) {
            int x = (e.getX() - 50) / BOX_WIDTH;
            int y = (e.getY() - 100) / BOX_HEIGHT;
            if (mat[x][y] == '-')
                mat[x][y] = st.pop();
            repaint();
        }
    }


    private void fillStackInValidWay(Stack<Character> st) {
        Character[] arr = new Character[]{'G', 'G', 'B', 'B', 'R', 'R'};
        st.addAll(List.of(arr));

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

    }
}