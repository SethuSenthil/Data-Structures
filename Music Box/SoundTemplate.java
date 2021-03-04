import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SoundTemplate extends JFrame implements Runnable {
	JToggleButton button[][] = new JToggleButton[37][180];
	JScrollPane buttoPane;
	JMenuBar menuBar;
	JMenu file, instrumentMenu;
	JMenuItem save, load;
	JMenuItem[] instrumentItems;
	JButton stopPlay, clear;
	JFileChooser fileChooser;
	JLabel[] labels = new JLabel[button.length];
	JPanel buttonPanel, labePanel, tempoPanel, menueButtonPanel;
	JLabel tempoLabel;
	JScrollBar tempoBar;
	JPanel panel = new JPanel();
	boolean notStopped = true;
	JFrame frame = new JFrame();
	String[] clipNames;
	Clip[] clip;
	int tempo;
	int row = 0, col = 0;
	Font font = new Font("Times New Roman", Font.PLAIN, 10);
	String[] instrumentNames = {"Bell", "Piano"};

	public SoundTemplate() {
		clipNames = new String[] { "C0", "B1", "ASharp1", "A1", "GSharp1", "G1", "FSharp1", "F1" };
		clip=new Clip[clipNames.length]; String initInstrument="\\"+instrumentNames [0] +"\\"+instrumentNames [0];
		try {
			for (int x = 0; x < clipNames.length; x++) {
				URL url = this.getClass().getClassLoader().getResource(initInstrument+ "-" + clipNames[x] + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				clip[x] = AudioSystem.getClip();
				clip[x].open(audioIn);
			}


		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(button.length, button[0].length, 2,5));

		for (int r = 0; r < button.length; r++) {
			String name = clipNames[r].replaceAll("Sharp", "#");

			for (int c = 0; c < button[0].length; c++) {
				button[r][c] = new JToggleButton();
				button[r][c].setFont(font);
				button[r][c].setText(name);
				button[r][c].setPreferredSize(new Dimension(30, 30));
				button[r][c].setMargin(new Insets(0,0,0,0));
				button[r][c].add(button[r][c]);
			}
		}

		tempoBar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 0, 50, 100);
		tempoBar.addAdjustmentListener(this);
		tempo = tempoBar.getValue();
		tempoLabel = new JLabel("Tempo: " + tempo);
		tempoPanel = new JPanel(new BorderLayout());
		tempoPanel.add(tempoLabel, BorderLayout.WEST);
		tempoLabel.add(tempoBar, BorderLayout.CENTER);

		String currDir = System.getProperty("user.dir");
		fileChooser = new JFileChooser(currDir);

		menuBar = new JMenuBar();
		menuBar.setLayout(new GridLayout(1,1));
		file = new JMenu("File");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		file.add(save);
		file.add(load);
		save.addActionListener(this);
		load.addActionListener(this);

		instrumentMenu = new JMenu("instruments");
		instrumentItems = new JMenuItem[instrumentNames.length];

		for (int x = 0; x < instrumentNames.length; x++) {
			instrumentItems[x] = new JMenuItem(instrumentNames[x]);
			instrumentItems[x].addActionListener(this);
			instrumentMenu.add(instrumentItems[x]);
		}

		menuBar.add(file);
		menuBar.add(instrumentMenu);

		menueButtonPanel = new JPanel();
		menueButtonPanel.setLayout(new GridLayout(1,2));
		stopPlay = new JButton("Play");
		stopPlay.addActionListener(this);
		menueButtonPanel.add(stopPlay);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		menueButtonPanel.add(clear);
		menuBar.add(menueButtonPanel, BorderLayout.EAST);

		//buttonPane = new JScrollPane(buttoPanel, JScrollBar.VERTICAL_SCROLLBAR_ALWAYS)

		panel.setLayout(new GridLayout(2, 2, 10, 10)); // the last two numbers "space out" the buttons
		for (int y = 0; y < 2; y++)
			for (int x = 0; x < 2; x++) {

				button[x][y] = new JToggleButton();
				panel.add(button[x][y]);
			}

		this.add(panel);
		setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Thread timing = new Thread(this);
		timing.start();
	}

	public void run() {
		do {
			try {
				if (button[0][0].isSelected()) {
					clip[0].start();
				}
				new Thread().sleep(1000);
				for (int x = 0; x < clip.length; x++) {
					clip[x].stop();
					clip[x].setFramePosition(0);
				}
			} catch (InterruptedException e) {
			}
		} while (notStopped);

	}

	public static void main(String args[]) {
		SoundTemplate app = new SoundTemplate();

	}
}