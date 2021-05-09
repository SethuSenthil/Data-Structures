import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class SideScroller extends JPanel implements Runnable, KeyListener {
    JFrame frame;
    int count = 0, scCount = 0, bcCount = 0, cCount = 0, currentColumn = 8, heroStartColumn;
    BufferedImage aladdinImage, smallCity, bigCity, clouds, sunset, boxImage, coin;
    BufferedImage[] aladdin = new BufferedImage[13];
    BufferedImage[] aladdinJumping = new BufferedImage[12];
    Thread timer;
    boolean gameOn = true, right = false;
    Hero hero;
    HashMap<Integer, ArrayList<Block>> aladdinMap = new HashMap<Integer, ArrayList<Block>>();


    public SideScroller() {
        frame = new JFrame("Aladdin's Not So Exicting Adventure");
        frame.add(this);

        int[][] locsAndDims = new int[][] {
                // x, y, width, height
                { 337, 3, 23, 55 }, { 4, 64, 31, 53 }, { 34, 64, 31, 53 }, { 62, 64, 31, 51 }, { 92, 64, 31, 51 },
                { 127, 64, 37, 51 }, { 166, 64, 31, 51 }, { 205, 64, 31, 51 }, { 233, 64, 30, 51 }, { 263, 61, 30, 56 },
                { 292, 61, 34, 56 }, { 325, 60, 41, 56 }, { 367, 60, 36, 56 } };

        int[][] jumpLocsAndDims = new int[][] {
                // x, y, width, height
                { 4, 294, 31, 59 }, { 35, 300, 25, 58 }, { 62, 301, 38, 56 }, { 100, 301, 36, 56 },
                { 140, 303, 41, 50 }, { 183, 304, 49, 47 }, { 230, 303, 42, 50 }, { 278, 302, 37, 54 },
                { 321, 303, 33, 56 }, { 4, 363, 35, 64 }, { 42, 365, 36, 63 }, { 168, 361, 25, 55 } };

        try {
            aladdinImage = ImageIO.read(new File("Aladdin.png"));
            smallCity = ImageIO.read(new File("smallCity.png"));
            bigCity = ImageIO.read(new File("bigCity.png"));
            clouds = ImageIO.read(new File("clouds.png"));
            sunset = ImageIO.read(new File("sunset.png"));
            boxImage = ImageIO.read(new File("box.png"));
            coin = ImageIO.read(new File("coin.png"));

            File file = new File("map.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            int row = 0;
            while ((text = input.readLine()) != null) {
                String[] pieces = text.split("");
                for (int x = 0; x < pieces.length; x++) {

                    if (!pieces[x].equals("-")) {
                        if (pieces[x].equals("H")) {
                            hero = new Hero(50 * x, 450, locsAndDims, jumpLocsAndDims);
                            heroStartColumn = x;
                        }
                        if (pieces[x].equalsIgnoreCase("B") || pieces[x].equalsIgnoreCase("M")) {
                            if (!aladdinMap.containsKey(x))
                                aladdinMap.put(x, new ArrayList<Block>());
                            aladdinMap.get(x).add(new Block(50 * x, 50 * row + 10, 50, 50, pieces[x]));
                        }
                        if (pieces[x].equalsIgnoreCase("C")) {
                            //coins
                        }
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < aladdin.length; x++) {
            aladdin[x] = aladdinImage.getSubimage(locsAndDims[x][0], locsAndDims[x][1], locsAndDims[x][2],
                    locsAndDims[x][3]);
            aladdin[x] = resize(aladdin[x], aladdin[x].getWidth() * 2, aladdin[x].getHeight() * 2);
        }

        for (int x = 0; x < 12; x++) {
            aladdinJumping[x] = aladdinImage.getSubimage(jumpLocsAndDims[x][0], jumpLocsAndDims[x][1],
                    jumpLocsAndDims[x][2], jumpLocsAndDims[x][3]);
            aladdinJumping[x] = resize(aladdinJumping[x], aladdinJumping[x].getWidth() * 2,
                    aladdinJumping[x].getHeight() * 2);
        }

        frame.addKeyListener(this);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        timer = new Thread(this);
        timer.start();
    }

    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image temp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledVersion = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledVersion.createGraphics();
        g2.drawImage(temp, 0, 0, null);
        g2.dispose();

        return scaledVersion;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.fillRect(0, 0, 900, 600);

        g.drawImage(sunset, 0, 0, this);
        g.drawImage(clouds, cCount + 960, 0, this);
        g.drawImage(clouds, cCount - 960, 0, this);
        g.drawImage(bigCity, bcCount + 960, 0, this);
        g.drawImage(bigCity, bcCount - 960, 0, this);
        g.drawImage(smallCity, scCount + 960, 0, this);
        g.drawImage(smallCity, scCount - 960, 0, this);

        for (int index = currentColumn - 7; index < currentColumn + 5; index++) {
            try {
                for (ArrayList<Block> blocks : aladdinMap.values()) {
                    for (int x = 0; x < blocks.size(); x++)
                        g.drawImage(boxImage, blocks.get(x).getX(), blocks.get(x).getY(), this);
                }

                /*for (ArrayList<Block> coins : aladdinMap.values()) {
                    for (int x = 0; x < coins.size(); x++)
                        g.drawImage(boxImage, coins.get(x).getX(), coins.get(x).getY(), this);
                }*/
            } catch (IndexOutOfBoundsException | NullPointerException e) {
            }
        }

        if (hero.isJumping() || hero.isFalling())
            g.drawImage(aladdinJumping[hero.getJumpCount()], hero.getX(), hero.getY(), this);
        else
            g.drawImage(aladdin[hero.getAladdinCount()], hero.getX(), hero.getY(), this);
    }

    public void run() {
        while (true) {
            if (gameOn) {
                if (hero.isJumping()) {
                    hero.updateJumping();
                } else if (hero.isFalling()) {
                    boolean hitBlock = collidingBelow();
                    if (!hitBlock && hero.getY() != hero.getOriginY()) {
                        hero.updateFalling();
                    } else {
                        hero.setFalling(false);
                        if (hitBlock)
                            hero.setOnBox(true);
                        hero.zeroJumpCount();
                    }
                } else if (hero.isOnBox()) {
                    boolean hitBlock = collidingBelow();
                    if (!hitBlock) {
                        hero.setFalling(true);
                        hero.setOnBox(false);
                    }
                }

                if (right) {
                    boolean hitBlock = false;

                    for (ArrayList<Block> blocks : aladdinMap.values()) {
                        for (int x = 0; x < blocks.size(); x++) {
                            if (hero.CollisionBox().intersects(blocks.get(x).getCollisionBox())
                                    && hero.sameLevel(blocks.get(x)) && blocks.get(x).isTopBlock()) {
                                hitBlock = true;
                                x = blocks.size();
                            }
                        }
                        if (hitBlock)
                            break;
                    }

                    if (!hitBlock) {
                        count++;

                        if (count % 20 == 0) {
                            hero.updateAladdinCount(hero.getAladdinCount() + 1);
                        }

                        for (ArrayList<Block> blocks : aladdinMap.values()) {
                            for (int x = 0; x < blocks.size(); x++) {
                                blocks.get(x).updateX(-1);
                            }
                        }

                        currentColumn = (int) (count / 50.0) + heroStartColumn;
                        scCount -= 1;
                        if (scCount < -1920)
                            scCount += 1920;
                        if (count % 3 == 0)
                            bcCount -= 1;
                        if (bcCount < -1920)
                            bcCount += 1920;
                        if (count % 5 == 0)
                            cCount -= 1;
                        if (cCount < -1920)
                            cCount += 1920;
                    }
                }

            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
            }
            repaint();
        }
    }

    public boolean collidingBelow() {
        for (int c = currentColumn; c <= currentColumn + 1; c++) {
            try {
                ArrayList<Block> blocks = aladdinMap.get(c);
                for (Block b : blocks) {
                    if (hero.CollisionBelow().intersects(b.getCollisionBox()) && hero.isAbove(b) && b.isTopBlock()) {
                        return true;
                    }
                }
            } catch (NullPointerException e) {
            }
        }
        return false;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 39) {
            right = false;
            hero.updateAladdinCount(0);
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            if (!hero.isJumping() && !hero.isFalling()) {
                hero.setJumping(true);
                hero.setOnBox(false);
            }
        }

        if (e.getKeyCode() == 39) {
            right = true;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SideScroller app = new SideScroller();
    }
}