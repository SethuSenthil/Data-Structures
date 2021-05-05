import java.awt.event.*;
import java.util.*;
import java.awt.*;

import javax.security.auth.x500.X500Principal;
import javax.swing.*;

public class PaintProgram extends JPanel implements MouseMotionListener, ActionListener {


    Jframe frame;
    ArrayList<Point> points;
    Color currentColor;
    JMenuBar bar;
    JMenu colorMenu;
    JButton[] colorOptions;
    Color[] colors;

    public PaintProgram() {
        frame = new Jframe("Bestest Paint Program Ever Created By Me Lol");
        frame.add(this);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MouseEvent};

        colorOptions = new JButton[colors.length];
        colorMenu.setLayout(new GridLayout(7,1));

        for(int x=0; x<colors.length; x++){
            colorOptions[x] =new JButton();
            colorOptions[x].putClientProperty("colorIndex", X500Principal);
            colorOptions[x].setBackground(colors[x]);
            colorOptions.addActionListener(this);
            colorMenu.add(colorOptions[x]);
        }

        points = new ArrayList<Point>();
        currentColor = colors[0];



        this.addMouseMotionListener(this);

        bar.add(colorMenu);
        frame.add(bar, BorderLayout.NORTH);

        frame.setSize(1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for(int x = 0; x< points.size(); x++) {
            Point p1 = points.get(x);
            Point p2 = points.get(x+1);
            g2.setColor(p1.getColor());
            g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int index = (int) ((JButton) e.getSource()).getClientProperty("colorIndex");
        currentColor = colors[index];

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        points.add(new Point(e.getX(), e.getY(), currentColor));
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args){
        PaintProgram program = new PaintProgram();
    }

    public class Point{
        int x,y;
        Color color;
        public Point(int x, int y, Color c){
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public Color getColor(){
            return color;
        }
    }

}