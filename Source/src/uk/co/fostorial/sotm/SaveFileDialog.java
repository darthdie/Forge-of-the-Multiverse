/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveFileDialog {

    public static Map<String, String[]> filterForType(DialogFileType type) {
        Map<String, String[]> filter = new HashMap<>();
        switch (type) {
            case Image:
                filter.put("Image Files", ImageIO.getReaderFileSuffixes());
                break;
            case Text:
                filter.put("Text Files (*.txt)", new String[]{"txt"});
                break;
            case Deck:
            case XML:
                filter.put("XML Files (*.xml)", new String[]{"xml"});
                break;
            case JPG:
                filter.put("JPG Files (*.jpg)", new String[]{"jpg"});
                break;
            case PNG:
                filter.put("PNG Files (*.png)", new String[]{"png"});
                break;
            default:
                filter = null;
                break;
        }
        
        return filter;
    }
    private final JFileChooser chooser;

    private boolean addExtension;
    private boolean overwriteConfirmation;
    private String selectedPath;

    public SaveFileDialog() {
        this(null);
    }

    public SaveFileDialog(Map<String, String[]> filters) {
        chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (!overwriteConfirmation) {
                    super.approveSelection();
                    return;
                }

                File f = getSelectedFile();
                if (f.exists()) {
                    int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.validate();
        chooser.setCurrentDirectory(ApplicationPreferences.getLastPath());

        addExtension = true;
        overwriteConfirmation = true;
        selectedPath = "";
        setFilters(filters);
    }

    public boolean getAddExtension() {
        return addExtension;
    }

    public void setAddExtension(boolean value) {
        addExtension = value;
    }

    public boolean getOverwriteConfirmation() {
        return overwriteConfirmation;
    }

    public void setOverwriteConfirmation(boolean value) {
        overwriteConfirmation = value;
    }
    
    public String getSelectedPath() {
        return selectedPath;
    }
    
    public String getDialogTitle() {
        return chooser.getDialogTitle();
    }

    public void setDialogTitle(String value) {
        chooser.setDialogTitle(value);
    }

    public void setFilters(Map<String, String[]> filters) {
        removeAllFilters();

        if (filters != null && filters.size() > 0) {
            addFilters(filters);
        }
    }

    private void removeAllFilters() {
        for (FileFilter filter : chooser.getChoosableFileFilters()) {
            chooser.removeChoosableFileFilter(filter);
        }
    }

    private void addFilters(Map<String, String[]> filters) {
        if (filters != null && filters.size() > 0) {
            Iterator it = filters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                chooser.addChoosableFileFilter(extensionFilterFrom(pairs));
            }
        }
    }

    public boolean showDialog(JFrame parent) {
        selectedPath = "";

        FileFilter[] filters = chooser.getChoosableFileFilters();

        if (filters.length > 0) {
            chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
        }

        if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        selectedPath = chooser.getSelectedFile().getAbsolutePath();

        if (addExtension
                && getExtension(chooser.getSelectedFile()).isEmpty()
                && filters.length > 0) {
            selectedPath = selectedPath + "." + ((FileNameExtensionFilter) filters[0]).getExtensions()[0];
        }
        
        ApplicationPreferences.setLastPath(chooser.getSelectedFile());

        return true;
    }

    private FileNameExtensionFilter extensionFilterFrom(Map.Entry entry) {
        return new FileNameExtensionFilter((String) entry.getKey(), (String[]) entry.getValue());
    }

    private String getExtension(File f) {
        String fileName = f.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }

        return "";
    }

}
