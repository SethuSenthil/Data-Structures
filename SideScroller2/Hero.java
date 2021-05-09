import java.awt.*;
import java.awt.geom.*;

public class Hero {
    int heroX, heroY, originalY, aladdinCount = 0, jumpCount = 0, count = 0;
    int[][] locsAndDims, jumpLocsAndDims;
    boolean jumping = false, falling = false, onBox = false;

    public Hero(int heroX, int heroY, int[][] locsAndDims, int[][] jumpLocsAndDims) {
        this.heroX = heroX;
        this.heroY = heroY;
        originalY = heroY;
        this.locsAndDims = locsAndDims;
        this.jumpLocsAndDims = jumpLocsAndDims;
    }

    public int getX() {
        return heroX;
    }

    public int getY() {
        return heroY;
    }

    public boolean isJumping() {
        return jumping;
    }

    public int getHeight() {
        return locsAndDims[0][3] * 2;
    }

    public void setJumping(boolean j) {
        jumping = j;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling() {
        falling = !falling;
    }

    public void setFalling(boolean f) {
        falling = f;
    }

    public boolean isOnBox() {
        return onBox;
    }

    public void setOnBox() {
        onBox = !onBox;
    }

    public void setOnBox(boolean b) {
        onBox = b;
    }

    public boolean sameLevel(Block block) {
        return heroY + getHeight() == block.getY() + 50;
    }

    public void updateJumping() {
        heroY--;
        count++;
        if (count % 25 == 0) {
            jumpCount++;
            if (jumpCount == 6) {
                setJumping(false);
                setFalling(true);
            }
        }
    }

    public void updateFalling() {
        heroY++;
        count++;

        if (count % 25 == 0) {
            jumpCount++;
            if (jumpCount == 12) {
                jumpCount = 0;
            }
        }
    }

    public int getOriginY() {
        return originalY;
    }

    public void zeroJumpCount() {
        jumpCount = 0;
    }

    public void setJumpCount(int n) {
        jumpCount = n;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void updateJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
        if (jumpCount == 12)
            this.jumpCount = 0;
    }

    public int getAladdinCount() {
        return aladdinCount;
    }

    public void updateAladdinCount(int aladdinCount) {
        this.aladdinCount = aladdinCount;
        if (aladdinCount == 13)
            this.aladdinCount = 1;
    }

    public boolean isAbove(Block block) {
        return heroY + getHeight() - 1 < block.getY();
    }

    public Rectangle2D CollisionBelow() {
        return new Rectangle2D.Double(getX(), getY() + 1, jumpLocsAndDims[jumpCount][2] * 2, locsAndDims[0][3] * 2);
    }

    public Rectangle2D CollisionBox() {
        return new Rectangle2D.Double(getX(), getY(), locsAndDims[aladdinCount][2] * 2, locsAndDims[0][3] * 2);
    }
}
