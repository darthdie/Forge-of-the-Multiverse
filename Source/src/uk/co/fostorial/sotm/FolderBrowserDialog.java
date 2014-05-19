/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.fostorial.sotm;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FolderBrowserDialog {
    private final JFileChooser chooser;
    private String selectedPath;
    
    public FolderBrowserDialog() {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.validate();
        chooser.setCurrentDirectory(ApplicationPreferences.getLastPath());
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
    
    public boolean showDialog(JFrame parent) {
        selectedPath = "";

        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        selectedPath = chooser.getSelectedFile().getAbsolutePath();
        
        ApplicationPreferences.setLastPath(chooser.getSelectedFile());

        return true;
    }
}
