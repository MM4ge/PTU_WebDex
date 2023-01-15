package org.allenfulmer.ptuviewer.views;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class StartupWindow {
    private JPanel panel1;
    private JButton button;
    private JTextPane msg;

    public StartupWindow() {
        JFrame frame = new JFrame("PTU Viewer + Generator");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400,300);

        StyledDocument documentStyle = msg.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            File curr = new File("src/main/resources/static/assets/pokeball.png");
            System.out.println(curr.getAbsolutePath());
            frame.setIconImage(ImageIO.read(curr));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        button.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create("http://localhost:8081/"));
            } catch (IOException ex) {
                throw new RuntimeException("Error in finding, running, or accessing the user's default browser!", ex);
            }
        });
    }

    public static void main(String[] args) {
        new StartupWindow();
    }
}
