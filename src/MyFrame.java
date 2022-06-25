import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame{
    /**
     *Frame of the screen.
     */

        public MyFrame(GameManager gameManager) {
            MatrixDrawer matrixDrawer = new MatrixDrawer(gameManager);
//            velocityButton = new JButton("Run Faster");
//            velocityButton.setBounds(800,400, 70,70);
//            velocityButton.addActionListener(this);

            this.setResizable(false);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new BorderLayout());
            this.setSize(1000, 800);
            this.setTitle("Ball Game");
            this.add(matrixDrawer);

//        this.pack();


        }


}

