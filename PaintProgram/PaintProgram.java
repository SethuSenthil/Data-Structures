import java.awt.event.*;
import java.util.*;
import java.awt.*;

import javax.security.auth.x500.X500Principal;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaintProgram extends JPanel
        implements MouseMotionListener, ActionListener, MouseListener, AdjustmentListener, ChangeListener {

    JFrame frame;
    ArrayList<Point> points;
    Color currentColor;
    JMenuBar bar;
    JMenu colorMenu, fileMenu;
    JMenuItem save, load, clear, exit;
    JMenuItem[] colorOptions;
    Color[] colors;
    Stack<ArrayList<Point>> freeLines;
    boolean drawingFreeLine;
    int penWidth;
    JScrollBar penWidthBar;
    JColorChooser colorChooser;
    BufferedImage loadedImage;

    JFileChooser filechooser;

    public PaintProgram() {
        frame = new JFrame("Bestest Paint Program Ever Created By Me Lol");
        frame.add(this);

        bar = new JMenuBar();
        colorMenu = new JMenu("Color Options");

        colors = new Color[] { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
                Color.MAGENTA };

        colorOptions = new JMenuItem[colors.length];
        colorMenu.setLayout(new GridLayout(8, 1));

        for (int x = 0; x < colors.length; x++) {
            colorOptions[x] = new JMenuItem();
            colorOptions[x].putClientProperty("colorIndex", x + "");
            colorOptions[x].setBackground(colors[x]);
            colorOptions[x].addActionListener(this);
            colorOptions[x].setPreferredSize(new Dimension(50, 30));
            colorMenu.add(colorOptions[x]);
        }

        points = new ArrayList<Point>();
        freeLines = new Stack<ArrayList<Point>>();
        drawingFreeLine = false;
        penWidthBar = new JScrollBar();
        penWidthBar.addAdjustmentListener(this);
        penWidth = penWidthBar.getValue();

        fileMenu = new JMenu("File");
        save = new JMenuItem("Save");
        load = new JMenuItem("Load");
        clear = new JMenuItem("New");
        exit = new JMenuItem("Exit");

        save.addActionListener(this);
        load.addActionListener(this);
        clear.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(clear);
        fileMenu.add(load);
        fileMenu.add(save);
        fileMenu.add(exit);

        String currDir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(currDir);

        currentColor = colors[0];
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);

        colorMenu.add(colorChooser);

        this.addMouseMotionListener(this);
        this.addMouseMotionListener(this);

        bar.add(fileMenu);
        bar.add(colorMenu);
        bar.add(penWidthBar);
        frame.add(bar, BorderLayout.NORTH);

        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        if(loadedImage!= null){
            g2.drawImage(loadedImage,0,0, null);
        }

        Iterator<ArrayList<Point>> it = freeLines.iterator();

        while (it.hasNext()) {
            ArrayList<Point> temp = it.next();
            if (temp.size() > 0) {
                g2.setStroke(new BasicStroke(temp.get(0).getPenWidth()));
                g2.setColor(temp.get(0).getColor());

                for (int x = 0; x < temp.size(); x++) {
                    Point p1 = temp.get(x);
                    Point p2 = temp.get(x + 1);

                    g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

                }
            }
        }

        if (drawingFreeLine) {
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth()));
            g2.setColor(points.get(0).getColor());

            for (int x = 0; x < points.size(); x++) {
                Point p1 = points.get(x);
                Point p2 = points.get(x + 1);
                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == save) {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            filechooser.setFileFilter(filter);

            if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    String st = file.getAbsolutePath();
                    if(st.indexOf(".png") >= 0)
                       st = st.substring(0,st.length()-4);
                    ImageIO.write(createImage(), "png", new File(st+".png"));
                } catch (IOException ioe) {
                    freeLines = new Stack<ArrayList<Point>>();
                    repaint();
                }
            }
        }else if(e.getSource() == load){
            fileChooser.showOpenDialog(null);
            File imageFile = fileChooser.getSelectedFile();
             if(imageFile != null && imageFile.toString().indexOf(".png") >= 0){
                 try{
                loadedImage = ImageIO.read(imgFile);
                 }catch(IOException ioe){}
             }else{
                if(imgFile != null)
                  JOptionPane.showOpenDialog("Wrong file type. Please select a PNG file.");
             }
        }else if (e.getSource() == clear){
            freeLines = new Stack<ArrayList<Point>>();
            loadedImage=null;
            repaint();
        }else if(e.getSource() == exit){
            /*boolean okToClose = true;
            if(freeLines.size() > 0){
                okToClose
            }else{

            }*/
            System.exit(0);
        }else {
            int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
            currentColor = colors[index];
        }

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

    public static void main(String[] args) {
        PaintProgram program = new PaintProgram();
    }

    public BufferedImage createImage() {
        int width = this.getWidth();
        int height = this.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        this.paint(g2);
        g2.dispose();
        return img;
    }

    public class Point {
        int x, y, penWidth;
        Color color;

        public Point(int x, int y, Color c, int penWidth) {
            this.x = x;
            this.y = y;
            this.color = c;
            this.penWidth = penWidth;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getPenWidth() {
            return penWidth;
        }

        public Color getColor() {
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

        if (drawingFreeLine) {
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