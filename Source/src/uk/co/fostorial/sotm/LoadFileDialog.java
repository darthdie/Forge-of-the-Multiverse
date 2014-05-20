/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.fostorial.sotm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFileDialog {

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

    private String selectedPath;

    public LoadFileDialog() {
        this(null);
    }

    public LoadFileDialog(Map<String, String[]> filters) {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.validate();
        chooser.setCurrentDirectory(ApplicationPreferences.getLastPath());

        selectedPath = "";
        setFilters(filters);
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
        FileFilter[] filters = chooser.getChoosableFileFilters();
        if(filters.length <= 1) {
            return;
        }
        
        for(int i = 1; i < filters.length; i++) {
            chooser.removeChoosableFileFilter(filters[i]);
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

        if (filters.length > 1) {
            chooser.setFileFilter(chooser.getChoosableFileFilters()[1]);
        }

        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        selectedPath = chooser.getSelectedFile().getAbsolutePath();
        
        ApplicationPreferences.setLastPath(chooser.getSelectedFile());

        return true;
    }

    private FileNameExtensionFilter extensionFilterFrom(Map.Entry entry) {
        return new FileNameExtensionFilter((String) entry.getKey(), (String[]) entry.getValue());
    }

}
