package org.allenfulmer.ptuviewer.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.Pokemon;
import org.allenfulmer.ptuviewer.jsonExport.ExodusConverter;
import org.allenfulmer.ptuviewer.jsonExport.roll20.Roll20Builder;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serial;

public class ConverterWindow extends JFrame implements WindowListener {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
            {
                ConverterWindow frame;
                try {
                    frame = new ConverterWindow();
                    frame.setVisible(true);
                    frame.startup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );
    }

    private void startup() {
        PojoToDBConverter.getConvertedCapabilities();
        this.roll20JSON.setText("Capabilities Loaded!\n");
        PojoToDBConverter.getConvertedMovesMap();
        this.roll20JSON.append("Moves Loaded!\n");
        PojoToDBConverter.getConvertedAbilitiesMap();
        this.roll20JSON.append("Abilities Loaded!\n");
        PojoToDBConverter.getConvertedPokemonSpeciesMap();
        this.roll20JSON.append("All Loading Done!\n");
    }

    private static final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setLenient().create();

    private static final String DESCRIPTION_TEXT = "Converts PokeExodus JSON (the popup text when generating a Pokemon or the contents of the \"Save to Computer\" file) to Roll20 JSON (for the Import page).\r\n\r\nPaste PokeExodus JSON in the top box, click the Convert button, copy Roll20 JSON, paste into a Roll20 character sheet's Import page, and hit Import.";

    private JTextArea exodusJSON;
    private JTextArea roll20JSON;

    /**
     * Create the frame.
     */
    public ConverterWindow() {
        this.setTitle("PokeExodus -> Roll20 JSON Converter");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 600, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();

        JLabel roll20Label = new JLabel("Roll20 JSON (Destination)");

        JScrollPane scrollPane1 = new JScrollPane();

        JLabel exodusLabel = new JLabel("PokeExodus JSON (Source)");

        JTextArea description = new JTextArea();
        description.setEditable(false);
        description.setFont(new Font("Monospaced", Font.PLAIN, 12));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setText(DESCRIPTION_TEXT);

        JButton btnConvert = new JButton("Convert");
        btnConvert.addActionListener(this::convertJSON);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(ignored -> {
            this.exodusJSON.setText("");
            this.roll20JSON.setText("");
        });

        GroupLayout glContentPane = new GroupLayout(contentPane);
        glContentPane.setHorizontalGroup(
                glContentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(glContentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(glContentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                        .addGroup(glContentPane.createSequentialGroup()
                                                .addComponent(roll20Label)
                                                .addGap(20)
                                                .addComponent(btnConvert, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                                .addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                        .addComponent(exodusLabel)
                                        .addComponent(description, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE))
                                .addContainerGap())
        );
        glContentPane.setVerticalGroup(
                glContentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glContentPane.createSequentialGroup()
                                .addComponent(description, GroupLayout.PREFERRED_SIZE, 111, Short.MAX_VALUE)
                                .addGap(18)
                                .addComponent(exodusLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                .addGroup(glContentPane.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(glContentPane.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(glContentPane.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(roll20Label)
                                                        .addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addGroup(Alignment.TRAILING, glContentPane.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnConvert, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                                .addGap(5)))
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        this.exodusJSON = new JTextArea();
        scrollPane1.setViewportView(this.exodusJSON);

        this.roll20JSON = new JTextArea();
        this.roll20JSON.setEditable(false);
        scrollPane.setViewportView(this.roll20JSON);
        contentPane.setLayout(glContentPane);
    }

    public void convertJSON(ActionEvent e) {
        try {
            Pokemon p1 = ExodusConverter.convertFromExodus(this.exodusJSON.getText());
            this.roll20JSON.setText(gson.toJson(new Roll20Builder(p1).setAbilitiesAsMoves(true).setConnectionInfoInMoves(true).build()));
        } catch (Exception ex) {
            this.roll20JSON.setText("There was an error! Please ensure your Exodus JSON is accurate (or give it to Mage to check)!");
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Unnecessary
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Unnecessary - Closing handles the exit event
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Unnecessary
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Unnecessary
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Unnecessary
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Unnecessary
    }

}

