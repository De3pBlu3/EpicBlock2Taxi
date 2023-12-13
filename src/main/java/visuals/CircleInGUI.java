package visuals;

import java.awt.Graphics;
import javax.swing.JFrame;

public class CircleInGUI extends JFrame{

    public CircleInGUI()
    {

        super("Circle In GUI");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400,400);

        setVisible(true);
    }

    public void paint(Graphics gh)
    {
        super.paint(gh);
        //draw circle outline
        gh.drawOval(80,80,150,150);
        gh.fillOval(80,80,150,150);

        // put white text in the circle
        gh.setColor(java.awt.Color.WHITE);
        gh.drawString("node 1", 150, 150);
    }

    public static void main(String[]args)
    {
        CircleInGUI  obj = new CircleInGUI();
    }
}


