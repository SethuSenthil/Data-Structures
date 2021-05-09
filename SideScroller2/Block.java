import java.awt.*;
import java.awt.geom.*;

public class Block {
    int blockX, blockY, width, height;
    String type;

    public Block(int blockX, int blockY, int width, int height, String type) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public int getX() {
        return blockX;
    }

    public int getY() {
        return blockY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isTopBlock() {
        return type.equals("B");
    }

    public boolean moving() {
        return type.equals("M");
    }

    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(blockX, blockY, width, height);
    }

    public void updateX(int num) {
        blockX += num;
    }
}
