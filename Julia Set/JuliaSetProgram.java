
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class JuliaSetProgram extends JPanel implements AdjustmentListener, ActionListener {
    JFrame frame;
    JScrollBar[] scrollBars;
    JLabel[] labels;
    JButton revert;
    JButton save;
    JButton openInBrowser;
    double a = 0, b = 0;
    int[] colors;
    boolean flip;
    double zoom = 1;
    int equation = 0;
    int shift = 3;
    float maxIter = 100;
    JFileChooser fileChooser;
    BufferedImage juliaImage;


    public JuliaSetProgram() {
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        flip = false;

        revert = new JButton();
        revert.setText("Reset");
        revert.setPreferredSize(new Dimension(70, 10));
        revert.addActionListener(this);

        openInBrowser = new JButton();
        openInBrowser.setText("Open in Browser");
        openInBrowser.setPreferredSize(new Dimension(70, 10));
        openInBrowser.addActionListener(this);

        save = new JButton();
        save.setText("Save");
        save.setPreferredSize(new Dimension(70, 10));
        save.addActionListener(this);

        scrollBars = new JScrollBar[6];
        colors = new int[3];
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(7, 1));



        JPanel scrollPanel = new JPanel();


        scrollPanel.setLayout(new GridLayout(7, 1));
        for (int i = 0; i < scrollBars.length; i++) {
            if (i == 2)
                scrollBars[i] = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 0, 100);
            else if (i == 3)
                scrollBars[i] = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 0, 700);
            else if (i == 4)
                scrollBars[i] = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 2);
            else if (i == 5)
                scrollBars[i] = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 0, 800);
            else
                scrollBars[i] = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -3000, 3000);

            scrollBars[i].addAdjustmentListener(this);
            scrollBars[i].setUnitIncrement(1);
            scrollPanel.add(scrollBars[i]);
        }
        JLabel[] temp = { new JLabel("A: " + scrollBars[0].getValue()), new JLabel("B: " + scrollBars[1].getValue()),
                new JLabel("Zoom: " + scrollBars[2].getValue()), new JLabel("Hue: " + scrollBars[3].getValue() + " "),
                new JLabel("Color Pallet: " + scrollBars[4].getValue() + 1),
                new JLabel("Equation:" + scrollBars[5].getValue())
             };
        labels = temp;
        for (JLabel j : labels)
            labelPanel.add(j);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(revert);
        buttonPanel.add(save);
        buttonPanel.add(openInBrowser);


        scrollBars[0].setToolTipText("A");
        scrollBars[1].setToolTipText("B");
        scrollBars[2].setToolTipText("Zoom");
        scrollBars[3].setToolTipText("Hue");
        scrollBars[4].setToolTipText("Set Exponent");
        scrollBars[5].setToolTipText("Change Pallet");
        JPanel onePanel = new JPanel();
        onePanel.setLayout(new BorderLayout());
        onePanel.add(labelPanel, BorderLayout.WEST);
        onePanel.add(scrollPanel, BorderLayout.CENTER);
        onePanel.add(buttonPanel, BorderLayout.NORTH);


        frame.add(onePanel, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawJuliaSet(g);
    }

    public void drawJuliaSet(Graphics g) {
        int width = frame.getWidth();
        int height = frame.getHeight();
        zoom = scrollBars[2].getValue();
        juliaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float iter = maxIter;
                double zx = 1.5 * ((i - (0.5 * width)) / (0.5 * zoom * width));
                double zy = (j - (0.5 * height)) / (0.5 * zoom * height);

                if (equation == 0) {
                    while ((zx * zx) + (zy * zy) < 6 && iter > 0) {
                        double val = (zx * zx) - (zy * zy) + a;
                        zy = (2 * zx * zy) + b;
                        zx = val;
                        iter--;
                    }
                }
                if (equation == 1) {
                    while ((zx * .5) + (zy) < 6 && iter > 0) {
                        double val = (zx * .5) - (zy) + a;
                        zy = (2 * zx * zy) + b;
                        zx = val;
                        iter--;
                    }
                }
                if (equation == 2) {
                    while ((zx * zx * zx) + (zy) < 6 && iter > 0) {
                        double val = (zx * zx * zx) - (zy) + a;
                        zy = (2 * zx * zy) + b;
                        zx = val;
                        iter--;
                    }
                }

                int c;
                if (iter > 0)
                    c = Color.HSBtoRGB((maxIter / iter) % 1 * shift, 1, 1);
                else
                    c = Color.HSBtoRGB(maxIter / iter, 1, 0);
                juliaImage.setRGB(i, j, c);
            }
        }
        g.drawImage(juliaImage, 0, 0, null);

    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == scrollBars[0])
            a = scrollBars[0].getValue() / 1000.0;
        if (e.getSource() == scrollBars[1])
            b = scrollBars[1].getValue() / 1000.0;
        if (e.getSource() == scrollBars[2])
            zoom = scrollBars[2].getValue() / 10.0;
        if (e.getSource() == scrollBars[3]) {
            if (scrollBars[3].getValue() == 0)
                maxIter = 2;
            else
                maxIter = (float) scrollBars[3].getValue();
        }
        if (e.getSource() == scrollBars[4])
            equation = scrollBars[4].getValue();
        if (e.getSource() == scrollBars[5])
            shift = scrollBars[5].getValue();
        repaintLabels();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == revert) {
            scrollBars[0].setValue(0);
            scrollBars[1].setValue(0);
            scrollBars[2].setValue(1);
            scrollBars[3].setValue(100);
            scrollBars[4].setValue(0);
            scrollBars[5].setValue(3);
            repaintLabels();
            //saveImage();
            //openInBrowser();
        }

        if(e.getSource() == save){
            saveImage();
        }

        if(e.getSource() == openInBrowser){
            openInBrowser();
        }

        repaint();
    }

    public void repaintLabels() {
        labels[0].setText("A: " + scrollBars[0].getValue());
        labels[1].setText("B: " + scrollBars[1].getValue());
        labels[2].setText("Zoom: " + scrollBars[2].getValue());
        labels[3].setText("Hue: " + scrollBars[3].getValue() + " ");
        labels[4].setText("Exponent " + scrollBars[4].getValue());
        labels[5].setText("Color Pallet: " + scrollBars[5].getValue());
    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }
    public void saveImage()
	{
        String currDir=System.getProperty("user.dir");
		fileChooser=new JFileChooser(currDir);
		if(juliaImage!=null)
		{
			FileFilter filter=new FileNameExtensionFilter("*.png","png");
		        fileChooser.setFileFilter(filter);
			if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			{
				File file=fileChooser.getSelectedFile();
				try
				{
					String st=file.getAbsolutePath();
					if(st.indexOf(".png")>=0)
						st=st.substring(0,st.length()-4);
					ImageIO.write(juliaImage,"png",new File(st+".png"));
				}catch(IOException e)
				{
				}

			}
		}
    }

    public void openInBrowser()
	{
		if(juliaImage!=null)
		{
            String imageString = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {
        ImageIO.write(juliaImage, "png", bos);
        byte[] imageBytes = bos.toByteArray();

        Base64.Encoder encoder = Base64.getEncoder();
        imageString = encoder.encodeToString(imageBytes);

        bos.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    String url = "https://sethusenthil.com/Base64-Photo/?data=" + "data:image/jpg;base64," + imageString;
    try {
        URI parsedURL = new URI(url);
        System.out.println(openWebpage(parsedURL));
    } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

            }
        }

    public static boolean openWebpage(URI uri) {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        public static boolean openWebpage(URL url) {
            try {
                return openWebpage(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return false;
        }

    }
