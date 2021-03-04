import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI extends JPanel {

    private JFrame frame;

    private JPanel panel;

    private JPanel buttonPanel;
    private JMenuBar menuBar;

    private JTextArea textArea;
    private JButton resetButton;

    private ArrayList<String> menuList;

    private String fontName = "Arial";
    private int fontSize = 12;
    private int fontStyle = 0;

    private EmptyBorder border2 = new EmptyBorder(3, 5, 3, 5);

    private Font origFont;

    public GUI() {
        frame = new JFrame("GUI Task");
        frame.setSize(1200, 800);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuList = new ArrayList<>();
        String colors = "Blue\tRed\tBlack\tGreen\tYellow\tWhite\tRandom";
        menuList.add("Font\tArial\tTimes New Roman\tCourier New\tKunstler Script\tTempus Sans ITC\tSimSun");
        menuList.add("Font Size\t12\t24\t36\t48");
        menuList.add("Font Style\tPlain\tBold\tItalic");
        menuList.add("Text Color\t" + colors);
        menuList.add("BG Color\t" + colors);
        menuList.add("Border Color\t" + colors + "\tNone");

        panel = new JPanel();
        buttonPanel = new JPanel();
        menuBar = new JMenuBar();

        textArea = new JTextArea();
        updateFont();

        for (String directionOrig : new String[] { "North", "South", "West", "East" }) {
            final String direction = directionOrig;
            JButton button = new JButton(direction);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (direction) {
                        case "North":
                        case "South":
                            panel.setLayout(new GridLayout(1, 2));
                            buttonPanel.setLayout(new GridLayout(1, 4));
                            menuBar.setLayout(new GridLayout(1, 0));
                            break;
                        case "West":
                        case "East":
                            panel.setLayout(new GridLayout(2, 1));
                            buttonPanel.setLayout(new GridLayout(4, 1));
                            menuBar.setLayout(new GridLayout(0, 1));
                            break;
                    }
                    frame.remove(panel);
                    frame.add(panel, direction);
                    frame.revalidate();
                }

            });
            buttonPanel.add(button);
        }
        boolean first = true;
        for (String menuInfo : menuList) {
            String[] arr = menuInfo.split("\t");
            JMenu menu = new JMenu(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                JMenuItem item = new JMenuItem(arr[i]);
                if (first) {
                    origFont = item.getFont();
                    first = false;
                }
                final int j = i;
                item.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setParamFromMenu(arr[0], arr[j]);
                    }

                });
                Color color = toColor(arr[j]);
                if (arr[0].equals("Font"))
                    item.setFont(new Font(arr[j], item.getFont().getStyle(), item.getFont().getSize()));
                item.setForeground(color == null ? Color.BLACK : color);
                menu.add(item);
            }
            JTextField textField = new JTextField("");
            textField.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setParamFromMenu(arr[0], e.getActionCommand());
                    textField.setText("");
                }

            });
            menu.add(textField);
            menuBar.add(menu);
        }

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fontName = "Arial";
                fontSize = 12;
                fontStyle = 0;
                updateFont();
                for (Component component : buttonPanel.getComponents())
                    component.setFont(origFont);
                for (Component component : menuBar.getComponents()) {
                    component.setFont(origFont);
                    if (component instanceof JMenu && !((JMenu) component).getText().equals("Font")) {
                        JMenu menu = (JMenu) component;
                        for (int i = 0; i < menu.getItemCount(); i++) {
                            JMenuItem item = menu.getItem(i);
                            if (item != null)
                                item.setFont(origFont);
                        }
                    }
                }
                resetButton.setFont(origFont);
                textArea.setForeground(Color.BLACK);
                textArea.setBackground(Color.WHITE);
                textArea.setText("");
                panel.setLayout(new GridLayout(1, 2));
                buttonPanel.setLayout(new GridLayout(1, 4));
                menuBar.setLayout(new GridLayout(1, 0));
                frame.remove(panel);
                frame.add(panel, BorderLayout.NORTH);
                for (Component component : buttonPanel.getComponents()) {
                    ((JButton) component).setBorder(border2);
                }
                resetButton.setBorder(border2);
                frame.revalidate();
            }

        });

        for (Component component : buttonPanel.getComponents()) {
            ((JButton) component).setBorder(border2);
        }
        resetButton.setBorder(border2);

        menuBar.add(resetButton);
        menuBar.setLayout(new GridLayout(1, 0));

        panel.add(buttonPanel);
        panel.add(menuBar);

        frame.add(textArea, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(1, 2));
        buttonPanel.setLayout(new GridLayout(1, 4));

        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);

    }

    public void setParamFromMenu(String key, String val) {
        switch (key) {
            case "Font":
                fontName = val;
                for (Component component : buttonPanel.getComponents())
                    component.setFont(new Font(val, component.getFont().getStyle(), component.getFont().getSize()));
                for (Component component : menuBar.getComponents()) {
                    component.setFont(new Font(val, component.getFont().getStyle(), component.getFont().getSize()));
                    if (component instanceof JMenu && !((JMenu) component).getText().equals("Font")) {
                        JMenu menu = (JMenu) component;
                        for (int i = 0; i < menu.getItemCount(); i++) {
                            JMenuItem item = menu.getItem(i);
                            if (item != null)
                                item.setFont(new Font(val, item.getFont().getStyle(), item.getFont().getSize()));
                        }
                    }
                }
                resetButton.setFont(new Font(val, resetButton.getFont().getStyle(), resetButton.getFont().getSize()));
                break;
            case "Font Size":
                if (val.matches("\\d+"))
                    fontSize = Integer.parseInt(val);
                break;
            case "Font Style":
                fontStyle = toFontStyle(val);
                break;
            case "Text Color":
                textArea.setForeground(toColor(val));
                break;
            case "BG Color":
                textArea.setBackground(toColor(val));
                break;
            case "Border Color":
                Border border;
                if (val.equals("None")) {
                    border = border2;
                } else {
                    LineBorder border1 = new LineBorder(toColor(val));
                    border = BorderFactory.createCompoundBorder(border1, border2);
                }
                for (Component component : buttonPanel.getComponents()) {
                    ((JButton) component).setBorder(border);
                }
                resetButton.setBorder(border);
                break;
        }
        updateFont();
    }

    public void updateFont() {
        textArea.setFont(new Font(fontName, fontStyle, fontSize));
    }

    public static Color toColor(String color) {
        if (color.equals("Random"))
            return new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        try {
            return (Color) Class.forName("java.awt.Color").getField(color.toUpperCase()).get(null);
        } catch (Exception e) {
            return null;
        }
    }

    public static int toFontStyle(String style) {
        try {
            return (int) Class.forName("java.awt.Font").getField(style.toUpperCase()).get(null);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        new GUI();
    }

}