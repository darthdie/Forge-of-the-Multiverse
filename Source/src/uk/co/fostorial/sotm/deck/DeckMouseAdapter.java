/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm.deck;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import uk.co.fostorial.sotm.CreatorFrame;

public class DeckMouseAdapter extends MouseAdapter {
    private final CreatorFrame frame;

    public DeckMouseAdapter(CreatorFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        } else if (e.getClickCount() == 2) {
            JTable table = (JTable) e.getSource();
            Point p = e.getPoint();
            int row = table.rowAtPoint(p);
            if (row == -1) {
                return;
            }

            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.editCard();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            doPop(e);
        }
    }

    private void doPop(MouseEvent e) {
        frame.getCreatorMenuBar().getCardMenu().show(e.getComponent(), e.getX(), e.getY());
    }
}
