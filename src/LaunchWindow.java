import javax.swing.*;

public class LaunchWindow extends JFrame {
//    JFrame frame = new JFrame();


    public LaunchWindow()  {


        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1000, 800);
        this.setVisible(true);
        this.setTitle("Bars");
        this.add(new LaunchPanel(this));

    }
}


