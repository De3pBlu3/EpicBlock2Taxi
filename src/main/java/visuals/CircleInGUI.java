package visuals;

import java.awt.Graphics;
import javax.swing.JFrame;
import network.Network;

public class CircleInGUI extends JFrame{

    public CircleInGUI(Network network)
    {

//
//        super("Circle In GUI");
//
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        setSize(400,400);
//
//        setVisible(true);

        // visualise the given network
        // draw a circle for each node
        // draw a line between each node
        // draw the name of each node in the circle
        // draw the distance between each node
        // draw the time between each node


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
        Network network = new Network();
        CircleInGUI  obj = new CircleInGUI(network);
    }
}


