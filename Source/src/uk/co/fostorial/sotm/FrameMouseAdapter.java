/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameMouseAdapter extends MouseAdapter {
    private final CreatorFrame frame;

    public FrameMouseAdapter(CreatorFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger() && frame.getTabbedPane().getTabCount() > 0) {
            doPop(e);
        }
    }

    private void doPop(MouseEvent e) {
        frame.getCreatorMenuBar().getFrameMenu().show(e.getComponent(), e.getX(), e.getY());
    }
}
