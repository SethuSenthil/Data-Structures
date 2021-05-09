import java.awt.event.*;
import java.util.*;
import java.awt.*;

import javax.security.auth.x500.X500Principal;
import javax.swing.*;
import javax.swing.event.*;


public class PaintProgram extends JPanel implements MouseMotionListener, ActionListener, MouseListener, AdjustmentListener, ChangeListener {


    Jframe frame;
    ArrayList<Point> points;
    Color currentColor;
    JMenuBar bar;
    JMenu colorMenu;
    JMenuItem[] colorOptions;
    Color[] colors;
    Stack<ArrayList<Point>> freeLines;
    boolean drawingFreeLine;
    int penWidth;
    JScrollBar penWidthBar;
    JColorChooser colorChooser;

    public PaintProgram() {
        frame = new Jframe("Bestest Paint Program Ever Created By Me Lol");
        frame.add(this);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};

        colorOptions = new JMenuItem[colors.length];
        colorMenu.setLayout(new GridLayout(8,1));


        for(int x=0; x<colors.length; x++){
            colorOptions[x] =new JMenuItem();
            colorOptions[x].putClientProperty("colorIndex", x + "");
            colorOptions[x].setBackground(colors[x]);
            colorOptions.addActionListener(this);
            colorOptions[x].setPreferredSize(new Dimension(50, 30));
            colorMenu.add(colorOptions[x]);
        }

        points = new ArrayList<Point>();
        freeLines = new Stack<ArrayList<Point>>();
        drawingFreeLine = false;
        penWidthBar = new JScrollBar();
        penWidthBar.addAdjustmentListener(this);
        penWidth = penWidthBar.getValue();
        currentColor = colors[0];
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);

        colorMenu.add(colorChooser);



        this.addMouseMotionListener(this);
        this.addMouseMotionListener(this);

        bar.add(colorMenu);
        bar.add(penWidthBar);
        frame.add(bar, BorderLayout.NORTH);

        frame.setSize(1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Iterator<ArrayList<Point>> it = freeLines.iterator();

        while (it.hasNext()) {
            ArrayList<Point> temp = it.next();
            g2.setStroke(new BasicStroke(temp.get(0).getPenWidth()));
            g2.setColor(temp.get(0).getColor());

            for(int x = 0; x< temp.size(); x++) {
                Point p1 = temp.get(x);
                Point p2 = temp.get(x+1);

                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

            }
        }

        if(drawingFreeLine){
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth()));
            g2.setColor(points.get(0).getColor());

        for(int x = 0; x< points.size(); x++) {
            Point p1 = points.get(x);
            Point p2 = points.get(x+1);
            g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

        }
    }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
        currentColor = colors[index];

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        drawingFreeLine = true;
        points.add(new Point(e.getX(), e.getY(), currentColor, penWidth));
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
        int x,y,penWidth;
        Color color;
        public Point(int x, int y, Color c, int penWidth){
            this.x = x;
            this.y = y;
            this.color = color;
            this.penWidth = penWidth;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public int getPenWidth(){
            return penWidth;
        }

        public Color getColor(){
            return color;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

        if(drawingFreeLine){
        freeLines.push(points);
        points = new ArrayList<Point>();
        drawingFreeLine = false;
        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void stateChanged(ChangeEvent e) {
        currentColor = colorChooser.getColor();
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        // TODO Auto-generated method stub
        penWidth = penWidthBar.getValue();
    }

}