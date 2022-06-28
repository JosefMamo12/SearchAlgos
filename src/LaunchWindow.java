import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchWindow implements ActionListener {
    JFrame frame = new JFrame();
    JButton button = new JButton("Next");

   public LaunchWindow() {

        button.setBounds(700,700,200,40);
        button.setFocusable(false);
        button.addActionListener(this);


        frame.add(button);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setTitle("Bars");
        frame.add(new LaunchPanel());


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
//            new MatrixDrawer();
        }
    }
}

