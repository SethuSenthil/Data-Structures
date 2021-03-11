import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.*;
import java.io.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {
    JPanel boardPanel;
    JToggleButton[][] board;
    boolean firstClick;
    int numMines;

    ImageIcon mine;
    GraphicsEnvironment ge;
    Font mineFont;

    public Minesweeper() {
        numMines = 10;
        firstClick = true;
        createBoard(10, 20);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void createBoard(int r, int c) {
        if (boardPanel != null)
            this.remove(boardPanel);
        boardPanel = new JPanel();
        board = new JToggleButton[r][c];
        boardPanel.setLayout(new GridLayout(r, c));
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                board[row][col] = new JToggleButton();
                board[row][col].putClientProperty("row", row);
                board[row][col].putClientProperty("col", col);
                board[row][col].putClientProperty("state", 0);
                board[row][col].setBorder(BorderFactory.createBevelBorder(0));
                board[row][col].setFocusPainted(false);
                board[row][col].addMouseListener(this);
                boardPanel.add(board[row][col]);
            }
        }
        this.setSize(c * 40, r * 40);
        this.add(boardPanel);
        this.revalidate();
    }

    public void mouseReleased(MouseEvent e) {
        JToggleButton btn = ((JToggleButton) e.getComponent());
        int r = (int) (btn.getClientProperty("row"));
        int c = (int) (btn.getClientProperty("col"));

        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            mineFont = Font.createFont(Font.TRUETYPE_FONT, new File("mine-sweeper.ttf"));
            ge.registerFont(mineFont);
        }catch{

        }



        if (e.getButton() == MouseEvent.BUTTON1) { // Left Click'
            board[r][c].setBackground(Color.LIGHT_GRAY);
            board[r][c].setOpaque(true);
            if (firstClick) {
                setBoard(r, c);
                firstClick = false;
            }

            int state = (int) board[r][c].getClientProperty("state");
            if (state == -1) {
                JOptionPane.showMessageDialog(null, "Your a loser!");
                // board[row][col].setBackground(Color.RED)
                // flip over the mine location
            } else {
                expand(r, c);
                checkWin();
            }
        }
    }

    public void write(int r, int c, int state) {
        Color color = Color.BLUE;

        switch (state) {
        // case 1: board[r][c].setForeground(color); break;
        case 2:
            color = Color.GREEN;
        case 3:
            color = Color.RED;
        case 4:
            color = new Color(128, 0, 128);
        case 5:
            color = new Color(128, 0, 0);
        case 6:
            color = Color.CYAN;
        case 7:
            color = Color.MAGENTA;
        case 8:
            color = Color.GRAY;
        }
        if (state == -1) {
            // board[r][c].setIcon(mineIcon);
        }
        if (state > 0) {
            board[r][c].setForeground(color);
            board[r][c].setText("" + state);
        }

    }

    public void expand(int r, int c) {
        int state = (int) board[r][c].getClientProperty("state");
        if (state > 0) {
            board[r][c].setText("" + state);
        } else {
            for (int r33 = r - 1; r33 <= r + 1; r33++) {
                for (int c33 = c - 1; c33 <= c + 1; c33++) {
                    try {
                        if (!board[r33][c33].isSelected())
                            expand(r33, c33);
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }
        }
    }

    public void checkWin() {
        int dimR = board.length;
        int dimC = board[0].length;
        int totalSpaces = dimR * dimC;
        int count = 0;
        for (int r = 0; r < dimR; r++) {
            for (int c = 0; c < dimC; c++) {
                int state = (int) board[r][c].getClientProperty("state");
                if (board[r][c].isSelected() && state != -1)
                    count++;
            }
        }

        if (numMines == totalSpaces - count)
            JOptionPane.showMessageDialog(null, "Your a Winner");
    }

    public void setBoard(int currRow, int currCol) {
        int count = numMines;
        int dimR = board.length;
        int dimC = board[0].length;
        while (count > 0) {
            int randR = (int) (Math.random() * dimR);
            int randC = (int) (Math.random() * dimC);
            int state = (int) board[randR][randC].getClientProperty("state");
            if (state == 0 && (Math.abs(currRow - randR) > 1 || Math.abs(currCol - randC) > 1)) {
                board[randR][randC].putClientProperty("state", -1);
                count--;
            }
        }

        for (int r = 0; r < dimR; r++) {
            for (int c = 0; c < dimC; c++) {
                int state = (int) board[r][c].getClientProperty("state");
                board[r][c].setText("" + state);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new Minesweeper();
    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
