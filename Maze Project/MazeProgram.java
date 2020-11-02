import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class MazeProgram extends JPanel implements KeyListener, MouseListener {
    JFrame frame;
    Maze maze = new Maze();
    Explorer explorer;
    int[][] wallArrReal;
    int x = 100, y = 100;
    int windowHeight = 100;
    int windowWidth = 100;
    int realWindowHeight = 800;
    int realWindowHeightWithoutBar = realWindowHeight - 35;
    int realWindowWidth = 1000;
    int width;
    int height;
    double slope = (double) realWindowHeightWithoutBar / realWindowWidth;
    int originalWall = 200;
    double shrink = .6;
    Location finish;
    boolean gameOver = false;
    boolean showMiniMap = false;
    boolean flash = false;

    int moves = 0;

    public MazeProgram() {
        setBoard();
        frame = new JFrame();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(realWindowWidth, realWindowHeight);
        frame.setVisible(true);
        frame.addKeyListener(this);

        System.out.println("Welcome to the A-maze-ing Maze!");
        System.out.println("Use the Arrow keys to move forward and back and turn the camera.");
        System.out.println("Press M to enable the minimap. This will deduct 20000 points when you enable it and another 20000 points every move you make with it enabled.");
        System.out.println("Press F to enable the flashlight. This will deduct 10000 points when you enable it and another 10000 points every move you make with it enabled.");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 800);

        g.setColor(Color.WHITE);

        if (!gameOver) {
            int[][] wallArr = wallArrReal;
            int expX = explorer.getX();
            int expY = explorer.getY();
            if (explorer.direction == 1) {
                wallArr = wallArrReal;
            } else if (explorer.direction == 2) {


                for (int loop = 0; loop != 3; loop++) {
                    //THIS TURNS LEFT
                    int tempX = expX;
                    expX = wallArr[0].length - expY - 1;
                    expY = tempX;
                    int[][] newWallArr = new int[wallArr[0].length][wallArr.length];
                    for (int ix = 0; ix != wallArr.length; ix++) {
                        for (int iy = 0; iy != wallArr[0].length; iy++) {
                            newWallArr[iy][ix] = wallArr[ix][newWallArr.length - iy - 1];
                        }
                    }
                    wallArr = newWallArr;
                }

            } else if (explorer.direction == 3) {
                for (int loop = 0; loop != 2; loop++) {
                    //THIS TURNS LEFT
                    int tempX = expX;
                    expX = wallArr[0].length - expY - 1;
                    expY = tempX;
                    int[][] newWallArr = new int[wallArr[0].length][wallArr.length];
                    for (int ix = 0; ix != wallArr.length; ix++) {
                        for (int iy = 0; iy != wallArr[0].length; iy++) {
                            newWallArr[iy][ix] = wallArr[ix][newWallArr.length - iy - 1];
                        }
                    }
                    wallArr = newWallArr;
                }
            } else { // up
                //THIS TURNS LEFT
                int tempX = expX;
                expX = wallArr[0].length - expY - 1;
                expY = tempX;
                int[][] newWallArr = new int[wallArr[0].length][wallArr.length];
                for (int ix = 0; ix != wallArr.length; ix++) {
                    for (int iy = 0; iy != wallArr[0].length; iy++) {
                        newWallArr[iy][ix] = wallArr[ix][newWallArr.length - iy - 1];
                    }
                }
                wallArr = newWallArr;
            }


            int yOffSet = wallArr[0].length;
            int y;
            do {
                y = expY + yOffSet;
                for (int x = wallArr.length - 1; x != expX - 1; x--) {
                    if (y >= 0 && y < wallArr[x].length && expY - 1 >= 0 && (wallArr[x][y] == 1 || wallArr[x][y] == 2)) {
                        float distance = (float) Math.pow(Math.pow((float) x - (float) expX, 2) + Math.pow((float) y - (float) expY, 2), .5);
                        float colorVal = (255 - distance * 50) / 255;
                        if (flash)
                            colorVal = (255 - distance * 10) / 255;
                        if (colorVal < 0)
                            colorVal = 0;
                        g.setColor(new Color(colorVal, colorVal, colorVal));

                        if (wallArr[x][y] == 2) {
                            g.setColor(new Color(0, colorVal, 0, .5f));

                        }
                        int yOffSetMinus1 = 0;
                        if (yOffSet != 0)
                            yOffSetMinus1 = (yOffSet - yOffSet / Math.abs(yOffSet));
                        double initX = 0;
                        for (int i = 0; i != Math.abs(expX - x); i++) {
                            initX += (double) originalWall * Math.pow(shrink, i);
                        }
                        double xWall = (double) originalWall * Math.pow(shrink, Math.abs(expX - x));
                        if (expY > y) {
                            int xShift = yOffSetMinus1 * (int) (realWindowWidth - (initX + xWall) * 2);
                            int xShift2 = yOffSetMinus1 * (int) (realWindowWidth - (initX) * 2);
                            int[] xpoints = {xShift2 + (int) initX, xShift + (int) (initX + xWall), xShift + (int) (initX + xWall), xShift2 + (int) initX};
                            int[] ypoints = {(int) (initX * slope), (int) (initX * slope + slope * xWall), (int) (realWindowHeightWithoutBar - (initX * slope) - slope * xWall), (int) (realWindowHeightWithoutBar - initX * slope)};
                            float distance2 = (float) Math.pow(Math.pow((float) x + 1 - (float) expX, 2) + Math.pow((float) y - (float) expY, 2), .5);
                            float colorVal2 = (255 - distance2 * 50) / 255;
                            if (flash)
                                colorVal2 = (255 - distance2 * 10) / 255;
                            if (colorVal2 < 0)
                                colorVal2 = 0;

                            Graphics2D g2d = (Graphics2D) g;

                            g2d.setPaint(new GradientPaint(xShift2 + (int) initX, 0, new Color(colorVal, colorVal, colorVal), xShift + (int) (initX + xWall), 0, new Color(colorVal2, colorVal2, colorVal2)));
                            g2d.fillPolygon(xpoints, ypoints, 4);

                            xShift = yOffSet * (int) (realWindowWidth - initX * 2);
                            if (x - 1 >= 0 && wallArr[x - 1][y] != 1) {
                                g.fillRect(xShift + (int) initX, (int) (initX * slope), (int) (realWindowWidth - initX * 2), (int) (realWindowHeightWithoutBar - (initX * slope * 2)));
                            }
                        } else if (expY < y) {
                            int xShift = yOffSetMinus1 * (int) (realWindowWidth - (initX + xWall) * 2);
                            int xShift2 = yOffSetMinus1 * (int) (realWindowWidth - (initX) * 2);
                            int[] xpoints = {xShift2 + (int) (realWindowWidth - initX), xShift + (int) (realWindowWidth - initX - xWall), xShift + (int) (realWindowWidth - initX - xWall), xShift2 + (int) (realWindowWidth - initX)};
                            int[] ypoints = {(int) (initX * slope), (int) (initX * slope + slope * xWall), (int) (realWindowHeightWithoutBar - (initX * slope) - slope * xWall), (int) (realWindowHeightWithoutBar - initX * slope)};

                            float distance2 = (float) Math.pow(Math.pow((float) x + 1 - (float) expX, 2) + Math.pow((float) y - (float) expY, 2), .5);
                            float colorVal2 = (255 - distance2 * 50) / 255;
                            if (flash)
                                colorVal2 = (255 - distance2 * 10) / 255;
                            if (colorVal2 < 0)
                                colorVal2 = 0;

                            Graphics2D g2d = (Graphics2D) g;


                            g2d.setPaint(new GradientPaint(xShift2 + (int) (realWindowWidth - initX), 0, new Color(colorVal, colorVal, colorVal), xShift + (int) (realWindowWidth - initX - xWall), 0, new Color(colorVal2, colorVal2, colorVal2)));
                            g.fillPolygon(xpoints, ypoints, 4);

                            xShift = yOffSet * (int) (realWindowWidth - initX * 2);
                            if (x - 1 >= 0 && wallArr[x - 1][y] != 1) {
                                g.fillRect(xShift + (int) initX, (int) (initX * slope), (int) (realWindowWidth - initX * 2), (int) (realWindowHeightWithoutBar - (initX * slope * 2)));
                            }
                        } else {
                            g.fillRect((int) initX, (int) (initX * slope), (int) (realWindowWidth - initX * 2), (int) (realWindowHeightWithoutBar - (initX * slope * 2)));
                        }
                    }
                }

                if (yOffSet > 0) {
                    y = expY + yOffSet;
                    yOffSet *= -1;
                } else {
                    y = expY + yOffSet;
                    yOffSet *= -1;
                    yOffSet--;
                }

            } while (y != expY);


            if (showMiniMap) {
                g.setColor(Color.ORANGE);
                int unitWidth = windowWidth / width;
                int unitHeight = (windowHeight) / height;

                for (int i = 0; i != maze.walls.size(); i++) {
                    g.fillRect(maze.walls.get(i).getX() * unitWidth, maze.walls.get(i).getY() * unitHeight, unitWidth, unitHeight);
                }
                g.setColor(Color.RED);
                g.fillRect(explorer.getX() * unitWidth, explorer.getY() * unitHeight, unitWidth, unitHeight);
                g.setColor(Color.GREEN);
                g.fillRect(finish.x * unitWidth, finish.y * unitHeight, unitWidth, unitHeight);
            }

            g.setColor(Color.BLUE);
            Font font = new Font("Times New Roman", Font.PLAIN, 80);
            FontMetrics metrics = g.getFontMetrics(font);

            int fontY = metrics.getAscent();
            g.setFont(font);

        } else {
            //GAMEOVER SCREEN
            Font font = new Font("Times New Roman", Font.PLAIN, 80);
            FontMetrics metrics = g.getFontMetrics(font);

            String text = "You Completed the Maze!";
            int x = (realWindowWidth - metrics.stringWidth(text)) / 2;
            int y = ((realWindowHeight - metrics.getHeight()) / 2) + metrics.getAscent() - metrics.getHeight();
            g.setFont(font);
            g.drawString(text, x, y);

            text = "Moves Used: " + moves;
            x = (realWindowWidth - metrics.stringWidth(text)) / 2;
            y = ((realWindowHeight - metrics.getHeight()) / 2) + metrics.getAscent() + metrics.getHeight() * 2;
            g.setFont(font);
            g.drawString(text, x, y);
        }

    }

    public void setBoard() {
        File name = new File("maze1.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(name));
            BufferedReader input2 = new BufferedReader(new FileReader(name));
            height = 0;
            width = 0;
            String text;
            while ((text = input2.readLine()) != null) {
                height++;
                if (text.length() > width)
                    width = text.length();
            }
            text = input.readLine();

            wallArrReal = new int[width][height];

            int lines = 0;

            do {
                for (int i = 0; i != text.length(); i++) {
                    if (text.charAt(i) == '#') {
                        maze.addMazing(i, lines);
                        wallArrReal[i][lines] = 1;
                    } else if (text.charAt(i) == 'S') {
                        explorer = new Explorer(i, lines);
                    } else if (text.charAt(i) == 'F') {
                        wallArrReal[i][lines] = 2;
                        finish = new Location(i, lines);
                    }
                }
                lines++;
            } while ((text = input.readLine()) != null);
        } catch (IOException io) {
            System.err.println("File error");
        }

    }

    public void keyPressed(KeyEvent e) {
        if (finish.x == explorer.getX() && finish.y == explorer.getY()) {
            gameOver = true;
        } else {

            if (e.getKeyCode() == 65 + 12) {
                showMiniMap = !showMiniMap;
            }
            if (e.getKeyCode() == 70) {
                flash = !flash;
            }

            if (e.getKeyCode() == 37) {//left
                explorer.left();
            }


            if (e.getKeyCode() == 38) {//forward
                if (maze.checkPos(explorer.forwardPos(), width, height)) {
                    explorer.forward();
                    moves++;

                }
            }
            if (e.getKeyCode() == 40) { //back
                explorer.right();
                explorer.right();
                if (maze.checkPos(explorer.forwardPos(), width, height)) {
                    explorer.forward();
                    moves++;
                }
                explorer.right();
                explorer.right();
            }
            if (e.getKeyCode() == 39) {//right
                explorer.right();

            }
            if (finish.x == explorer.getX() && finish.y == explorer.getY())
                gameOver = true;
        }
        repaint();


    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }
}