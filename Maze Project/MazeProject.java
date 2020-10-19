import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MazeProject extends JPanel implements KeyListener {
    JFrame frame;
    int x;
    int y;
    ArrayList<Integer> pathwaysX
    = new ArrayList<Integer>();
    ArrayList<Integer> pathwaysY
    = new ArrayList<Integer>();
    public MazeProject(){
        x = 0;
        y = 0;
        frame = new JFrame("A-Masing Program");
        frame.add(this);
        frame.setSize(1200, 700);
        frame.addKeyListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
       super.paintComponent(g);  //clean screen
       Graphics2D g2 = (Graphics2D)g;
       g2.setColor(Color.BLACK);
       g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());


       File fileName = new File("map.txt");
       g2.setColor(new Color(120,180,0));

       try
       {
           BufferedReader input = new BufferedReader(new FileReader(fileName));
           String text = input.readLine();
           int xMap = 0;
           int yMap = 0;

           while((text = input.readLine()) != null)
           {
            int height = 50;
            int width = 50;
            for(char c : text.toCharArray()) {

               System.out.print(c);

               if(c == '*'){
                   g2.setColor(new Color(120,180,0));
                   pathwaysX.add(xMap);
                   pathwaysY.add(yMap);
               }

                   if(c == '#')
                   g2.setColor(new Color(20,30,0));

                 g2.fillRect(xMap, yMap, height, width);

                 g2.setColor(Color.white);
                 g2.drawRect(xMap, yMap, width, height);
               xMap += width;

            }
            System.out.println();
            yMap += height;
            xMap = 0;
           }

       }catch(IOException e)
       {
           System.out.println("File not found.");
       }

       g2.setColor(new Color(120,180,0));
       g2.drawRect(x, y, 50, 50);
       g2.setColor(Color.MAGENTA);
       g2.setStroke(new BasicStroke(5));
       g2.drawOval(x, y, 30, 30);
    }

    public static void main(String[] args) {
         System.out.println("running program");

         MazeProject app = new MazeProject();

    }

    public void keyPressed(KeyEvent e) {
        //up = 39
        int possibleX = x;
        int possibleY = y;
        int blockSize = 50;

         if(e.getKeyCode() == 38)
         possibleY -= blockSize;
        if(e.getKeyCode() == 37)
        possibleX -= blockSize;
        if(e.getKeyCode() == 39)
        possibleX += blockSize;
          if(e.getKeyCode() == 40)
          possibleY += blockSize;


          if(frame.getWidth() >= possibleX && frame.getHeight() >= possibleY){ //check if its outta bounds
            System.out.println(possibleX + ","+ possibleY);
             if(possibleX < frame.getWidth() && possibleY < frame.getHeight()){

               if(pathwaysX.indexOf(possibleX) != -1 && pathwaysY.indexOf(possibleY) != -1){
                x = possibleX;
                y = possibleY;
               }

             }
          }

          //if(e.keyCode() == 32){}


        //left = 37
        //right = 39
        //down = 40
        //spacebar = 32

        repaint();

    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}