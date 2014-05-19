/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.fostorial.sotm;

import java.io.File;
import java.util.prefs.Preferences;

public class ApplicationPreferences {
    private static final Preferences prefs;
    
    static {
        prefs = Preferences.userRoot().node(ApplicationPreferences.class.getName());
    }
    
    public static File getLastPath() {
        return new File(prefs.get("lastPath", "."));
    }
    
    public static void setLastPath(File path) {
        if(path == null) {
            return;
        }
        
        prefs.put("lastPath", path.getAbsolutePath());
    }
}
