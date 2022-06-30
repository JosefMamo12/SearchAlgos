import javax.swing.*;

public class LaunchWindow extends JFrame {
//    JFrame frame = new JFrame();


    public LaunchWindow()  {


        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(LaunchPanel.SCREEN_WIDTH, LaunchPanel.SCREEN_HEIGHT);
        this.setVisible(true);
        this.setTitle("Bars");
        this.add(new LaunchPanel(this));

    }
}


