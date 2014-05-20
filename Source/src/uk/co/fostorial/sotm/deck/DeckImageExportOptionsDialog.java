/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm.deck;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import uk.co.fostorial.sotm.FolderBrowserDialog;

/**
 *
 * @author MCP
 */
public class DeckImageExportOptionsDialog extends JDialog implements PropertyChangeListener {
    JOptionPane optionPane;
    JTextField exportDir;
    JComboBox imageFormat;

    boolean result;
    DeckImageExportOptions exportOptions;

    public boolean getResult() {
        return result;
    }

    public DeckImageExportOptions getOptions() {
        return exportOptions;
    }

    public DeckImageExportOptionsDialog(JFrame parent) {
        super(parent, "Image Export Options", true);

        initGUI();
        centerOnParent();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void centerOnParent() {
        if (getParent() == null) {
            return;
        }

        this.setLocationRelativeTo(getParent());
    }

    private void initGUI() {
        imageFormat = new JComboBox(ImageIO.getReaderFileSuffixes());

        exportDir = new JTextField();

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener((ActionEvent e1) -> {
            FolderBrowserDialog d = new FolderBrowserDialog();
            if (d.showDialog((JFrame) getParent())) {
                exportDir.setText(d.getSelectedPath());
            }
        });

        final JComponent[] inputs = new JComponent[]{
            new JLabel("Export Options:"),
            new JLabel("Image Format:"),
            imageFormat,
            new JLabel("Export Directory:"),
            exportDir,
            browseButton};

        Object[] options = {"Export", "Cancel",};

        optionPane = new JOptionPane(inputs, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
        optionPane.addPropertyChangeListener(this);
        getContentPane().add(optionPane);

        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object value = optionPane.getValue();
        if (value == JOptionPane.UNINITIALIZED_VALUE) {
            return;
        }

        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

        if (value.equals("Export")) {
            String dirText = exportDir.getText();
            if (dirText.equals("") || !(new File(dirText).exists())) {
                JOptionPane.showMessageDialog((JFrame) getParent(), "Please enter in a valid export directory.");
                return;
            }

            result = true;
            exportOptions = new DeckImageExportOptions();
            exportOptions.ExportDirectory = dirText;
            exportOptions.Extension = (String) imageFormat.getSelectedItem();
        } else {
            result = false;
            exportOptions = null;
        }

        setVisible(false);
    }
}
