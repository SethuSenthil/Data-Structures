import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.awt.image.BufferedImage;

public class JuliaSetProgram extends JPanel implements AdjustmentListener{

    JFrame frame;
    JPanel scrollPanel, labelPanel, bigPanel;
    int red,green,blue;

    JScrollBar redBar, greenBar, blueBar;
    JLabel redLabel, greenLabel, blueLabel;

    public JuliaSetProgram(){
        //init frame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        redBar = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,255);
        greenBar = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,255);
        blueBar = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,255);
        GridLayout grid = new GridLayout(3,2);

        redLabel = new JLabel("Red");
        greenLabel = new JLabel("Green");
        blueLabel = new JLabel("Blue");

        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(redLabel);
        labelPanel.add(greenLabel);
        labelPanel.add(blueLabel);

        red = redBar.getValue();
        blue = blueBar.getValue();
        green = greenBar.getValue();

        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(redBar);
        scrollPanel.add(greenBar);
        scrollPanel.add(blueBar);

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(scrollPanel, BorderLayout.CENTER);
        bigPanel.add(labelPanel, BorderLayout.CENTER);




        redBar.addAdjustmentListener(this);
        blueBar.addAdjustmentListener(this);
        greenBar.addAdjustmentListener(this);


        //orientation, starting value, width, doodad, min, max, value

        frame.add(scrollPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //g.setColor(Color.MAGENTA);
        g.setColor(new Color(red,green,blue));
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
    }

    public static void main(String[] args){
        JuliaSetProgram app = new JuliaSetProgram();
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
       if(e.getSource() == redBar)
            red = redBar.getValue();

       if(e.getSource() == greenBar)
            green = greenBar.getValue();

       if(e.getSource() == blueBar)
            blue = blueBar.getValue();

        repaint();

    }
}