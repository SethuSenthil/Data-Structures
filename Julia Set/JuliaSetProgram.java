import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;

public class JuliaSetProgram extends JPanel implements AdjustmentListener {

    JFrame frame;
    int red, green, blue;
    JScrollBar redBar, greenBar, blueBar;
    JPanel scrollPanel, labelPanel, bigPanel;
    JLabel redLabel, greenLabel, blueLabel;
    BufferedImage image;
    double a = 0.0, b = 0.0;
    int w = 1000, h = 600;

    public JuliaSetProgram() {
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(w, h);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // orientation,starting value,doodad size,min value,max value
        redBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);
        red = redBar.getValue();
        redBar.addAdjustmentListener(this);

        greenBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);
        green = greenBar.getValue();
        greenBar.addAdjustmentListener(this);

        blueBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 255);
        blue = blueBar.getValue();
        blueBar.addAdjustmentListener(this);

        GridLayout grid = new GridLayout(3, 1);
        redLabel = new JLabel("Red");
        greenLabel = new JLabel("Green");
        blueLabel = new JLabel("Blue");

        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(redLabel);
        labelPanel.add(greenLabel);
        labelPanel.add(blueLabel);

        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(redBar);
        scrollPanel.add(greenBar);
        scrollPanel.add(blueBar);

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);

        frame.add(bigPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static double Sqr(double a) {
        return a * a;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // g.setColor(new Color(red,green,blue));
        // g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        // g.setColor(Color.MAGENTA);
        // g.setColor(new Color(red,green,blue));
        // g.fillRect(0,0,frame.getWidth(),frame.getHeight());

        for (int x = 0; x < w; x++) {

            for (int y = 0; y < h; y++) {

                double zx = 1.5 * (x - (w / 2)) / (0.5 * 1 * w);
                double zy = (y - (h / 2)) / (0.5 * 1 * h);
                float i = 10, is = 5; // iterations

                while ((Sqr(zx) + Sqr(zy)) < 6 && i > 0) {

                    double num = Sqr(zx) - Sqr(zy) + a;

                    zy = 2 * zx * zy + b;
                    zx = num;
                    i -= 1;

                }

                int c;

                if (i > 0)
                    c = Color.HSBtoRGB(((is / i) % 1), 50, 50);
                else
                    c = Color.HSBtoRGB((is / i), 1, 0);

                image.setRGB(x, y, c);
            }

        }

        g.drawImage(image, 0, 0, null);

    }

    public BufferedImage drawJulia(Graphics g) {
        // put your drawJulia image code in here
        return null;
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == redBar)
            red = redBar.getValue();
        if (e.getSource() == greenBar)
            green = greenBar.getValue();
        if (e.getSource() == blueBar)
            blue = blueBar.getValue();
        repaint();
    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }

}