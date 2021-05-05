import javax.swing.JPanel;
import javax.swing.Jframe;
import java.awt.*;

public class SideScroller extends JPanel{
    JFrame frame;

    public SideScroller(){
        frame = new JFrame("Aladdin's Not So Exciting Adventure");
        frame.add(this);

        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void main(String[] args){
        SideScroller app = new SideScroller();
    }
}