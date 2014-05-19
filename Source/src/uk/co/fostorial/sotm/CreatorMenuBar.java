package uk.co.fostorial.sotm;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import uk.co.fostorial.sotm.deck.DeckManager;
import uk.co.fostorial.sotm.deck.DeckPrinter;
import uk.co.fostorial.sotm.structure.Deck;

public final class CreatorMenuBar extends JMenuBar implements ActionListener {

    private static final long serialVersionUID = -1809228320182460442L;

    private CreatorFrame frame;

    private JMenu file;
    private JMenuItem fileNewHeroDeck;
    private JMenuItem fileNewVillainDeck;
    private JMenuItem fileNewEnvironmentDeck;
    private JMenuItem fileOpenDeck;
    private JMenuItem fileSaveDeckAs;
    private JMenuItem fileCloseCurrentTab;
    private JMenuItem fileExit;

    private JMenu deck;
    private JMenuItem deckNewCard;
    private JMenuItem deckIncreaseNumberInDeck;
    private JMenuItem deckDecreaseNumberInDeck;
    private JMenuItem deckEditCard;
    private JMenuItem deckDeleteCard;
    private JMenuItem deckStatistics;

    private JMenu export;
    private JMenuItem exportExportCurrentTabPNG;
    private JMenuItem exportExportCurrentTabJPG;
    private JMenuItem exportExportDeckIndividuallyPNG;
    private JMenuItem exportExportDeckIndividuallyJPG;
    private JMenuItem exportExportDeckFullPNG;
    private JMenuItem exportExportDeckFullJPG;
    private JMenuItem exportExportToText;

    private JMenu help;
    private JMenuItem version;

    private JPopupMenu cardMenu;
    private JMenuItem cardNewCard;
    private JMenuItem cardIncreaseNumberInDeck;
    private JMenuItem cardDecreaseNumberInDeck;
    private JMenuItem cardEditCard;
    private JMenuItem cardDeleteCard;

    private JPopupMenu frameMenu;
    private JMenuItem frameSaveDeckAs;
    private JMenuItem frameCloseCurrentTab;

    public CreatorMenuBar(CreatorFrame frame) {
        this.setFrame(frame);
        setup();
    }

    private void setup() {
        file = new JMenu("File");

        fileNewHeroDeck = new JMenuItem("New Hero Deck", new ImageIcon("Images/Icons/user.png"));
        fileNewHeroDeck.addActionListener(this);
        KeyStroke key = KeyStroke.getKeyStroke(
                KeyEvent.VK_H, KeyEvent.ALT_DOWN_MASK);
        fileNewHeroDeck.setAccelerator(key);
        file.add(fileNewHeroDeck);

        fileNewVillainDeck = new JMenuItem("New Villain Deck", new ImageIcon("Images/Icons/user_gray.png"));
        fileNewVillainDeck.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK);
        fileNewVillainDeck.setAccelerator(key);
        file.add(fileNewVillainDeck);

        fileNewEnvironmentDeck = new JMenuItem("New Environment Deck", new ImageIcon("Images/Icons/world.png"));
        fileNewEnvironmentDeck.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_E, KeyEvent.ALT_DOWN_MASK);
        fileNewEnvironmentDeck.setAccelerator(key);
        file.add(fileNewEnvironmentDeck);

        fileOpenDeck = new JMenuItem("Open Deck...", new ImageIcon("Images/Icons/arrow_right.png"));
        fileOpenDeck.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        fileOpenDeck.setAccelerator(key);
        file.add(fileOpenDeck);

        fileSaveDeckAs = new JMenuItem("Save Deck As...", new ImageIcon("Images/Icons/disk.png"));
        fileSaveDeckAs.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        fileSaveDeckAs.setAccelerator(key);
        file.add(fileSaveDeckAs);

        fileCloseCurrentTab = new JMenuItem("Close");
        fileCloseCurrentTab.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        fileCloseCurrentTab.setAccelerator(key);
        file.add(fileCloseCurrentTab);

        export = new JMenu("Export");

        exportExportCurrentTabJPG = new JMenuItem("Export to JPEG");
        exportExportCurrentTabJPG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        exportExportCurrentTabJPG.setAccelerator(key);
        export.add(exportExportCurrentTabJPG);

        exportExportCurrentTabPNG = new JMenuItem("Export to PNG");
        exportExportCurrentTabPNG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
        exportExportCurrentTabPNG.setAccelerator(key);
        export.add(exportExportCurrentTabPNG);

        exportExportDeckIndividuallyPNG = new JMenuItem("Export Cards to PNG");
        exportExportDeckIndividuallyPNG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        //exportExportDeckIndividuallyPNG.setAccelerator(key);
        export.add(exportExportDeckIndividuallyPNG);

        exportExportDeckIndividuallyJPG = new JMenuItem("Export Cards to JPG");
        exportExportDeckIndividuallyJPG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        //exportExportDeckIndividuallyPNG.setAccelerator(key);
        export.add(exportExportDeckIndividuallyJPG);

        exportExportDeckFullPNG = new JMenuItem("Export Deck to PNG");
        exportExportDeckFullPNG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        //exportExportDeckIndividuallyPNG.setAccelerator(key);
        export.add(exportExportDeckFullPNG);

        exportExportDeckFullJPG = new JMenuItem("Export Deck to JPG");
        exportExportDeckFullJPG.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        //exportExportDeckIndividuallyPNG.setAccelerator(key);
        export.add(exportExportDeckFullJPG);

        exportExportToText = new JMenuItem("Export Deck to Text");
        exportExportToText.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
        //exportExportDeckIndividuallyPNG.setAccelerator(key);
        export.add(exportExportToText);

        file.add(export);

        file.addSeparator();

        fileExit = new JMenuItem("Exit", new ImageIcon("Images/Icons/exclamation.png"));
        fileExit.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
        fileExit.setAccelerator(key);
        file.add(fileExit);

        this.add(file);

        deck = new JMenu("Deck");

        deckNewCard = new JMenuItem("Add New Card");
        deckNewCard.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        deckNewCard.setAccelerator(key);
        deck.add(deckNewCard);

        deckEditCard = new JMenuItem("Edit Card");
        deckEditCard.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        deckEditCard.setAccelerator(key);
        deck.add(deckEditCard);

        deckDeleteCard = new JMenuItem("Delete Card");
        deckDeleteCard.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
        deckDeleteCard.setAccelerator(key);
        deck.add(deckDeleteCard);

        deck.addSeparator();

        deckIncreaseNumberInDeck = new JMenuItem("Increase Number in Deck");
        deckIncreaseNumberInDeck.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
        deckIncreaseNumberInDeck.setAccelerator(key);
        deck.add(deckIncreaseNumberInDeck);

        deckDecreaseNumberInDeck = new JMenuItem("Decrease Number in Deck");
        deckDecreaseNumberInDeck.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        deckDecreaseNumberInDeck.setAccelerator(key);
        deck.add(deckDecreaseNumberInDeck);

		//deck.addSeparator();
        deckStatistics = new JMenuItem("Deck Statistics");
        deckStatistics.addActionListener(this);
        key = KeyStroke.getKeyStroke(
                KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        deckStatistics.setAccelerator(key);
		//deck.add(deckStatistics);

        this.add(deck);

        help = new JMenu("Help");

        version = new JMenuItem("Version Information");
        version.addActionListener(this);
        help.add(version);

        this.add(help);

        cardMenu = new JPopupMenu();
        cardNewCard = new JMenuItem("Add New Card");
        cardNewCard.addActionListener(this);
        cardEditCard = new JMenuItem("Edit Card");
        cardEditCard.addActionListener(this);
        cardDeleteCard = new JMenuItem("Delete Card");
        cardDeleteCard.addActionListener(this);
        cardIncreaseNumberInDeck = new JMenuItem("Increase Number in Deck");
        cardIncreaseNumberInDeck.addActionListener(this);
        cardDecreaseNumberInDeck = new JMenuItem("Decrease Number in Deck");
        cardDecreaseNumberInDeck.addActionListener(this);
        cardMenu.add(cardNewCard);
        cardMenu.add(cardEditCard);
        cardMenu.add(cardDeleteCard);
        cardMenu.addSeparator();
        cardMenu.add(cardIncreaseNumberInDeck);
        cardMenu.add(cardDecreaseNumberInDeck);

        frameMenu = new JPopupMenu();
        frameCloseCurrentTab = new JMenuItem("Close");
        frameCloseCurrentTab.addActionListener(this);
        frameSaveDeckAs = new JMenuItem("Save Deck As...");
        frameSaveDeckAs.addActionListener(this);
        //frameMenu.add(frameSaveDeckAs);
        frameMenu.add(frameCloseCurrentTab);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }

        if (e.getSource().equals(fileNewHeroDeck)) {
            frame.newWindow(CreatorFrame.FILE_NEW_HERO_DECK, null);
        }

        if (e.getSource().equals(fileNewVillainDeck)) {
            frame.newWindow(CreatorFrame.FILE_NEW_VILLAIN_DECK, null);
        }
        else if (e.getSource().equals(fileNewEnvironmentDeck)) {
            frame.newWindow(CreatorFrame.FILE_NEW_ENVIRONMENT_DECK, null);
        }
        else if (e.getSource().equals(fileOpenDeck)) {
            frame.newWindow(CreatorFrame.FILE_OPEN_HERO_DECK, null);
        }
        else if (e.getSource().equals(fileSaveDeckAs) || e.getSource().equals(frameSaveDeckAs)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.saveDeck();
            }
        }
        else if (e.getSource().equals(exportExportCurrentTabJPG)) {
            frame.exportToJPEG();
        }
        else if (e.getSource().equals(exportExportCurrentTabPNG)) {
            frame.exportToPNG();
        }
        else if (e.getSource().equals(fileCloseCurrentTab) || e.getSource().equals(frameCloseCurrentTab)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                Deck d = ((DeckManager)frame.getTabbedPane().getSelectedComponent()).getDeck();
                if(d.getIsDirty()) {
                    int option = JOptionPane.showConfirmDialog(this.getFrame(),
                        "There are unsaved changes, would you like to save your changes first?", 
                        "Unsaved Changes", 
                        JOptionPane.YES_NO_CANCEL_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);
            
                    if(option == JOptionPane.YES_OPTION) {
                        if(!((DeckManager)frame.getTabbedPane().getSelectedComponent()).saveDeck()) {
                            return;
                        }
                    }
                    else if(option == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
            }
            
            frame.closeCurrentFrame();
        }
        else if (e.getSource().equals(deckNewCard) || e.getSource().equals(cardNewCard)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.addCardToDeck();

                manager.editCard();
            }
        }
        else if (e.getSource().equals(deckIncreaseNumberInDeck) || e.getSource().equals(cardIncreaseNumberInDeck)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.increaseNumberInDeck();
            }
        }
        else if (e.getSource().equals(deckDecreaseNumberInDeck) || e.getSource().equals(cardDecreaseNumberInDeck)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.decreaseNumberInDeck();
            }
        }
        else if (e.getSource().equals(deckEditCard) || e.getSource().equals(cardEditCard)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.editCard();
            }
        }
        else if (e.getSource().equals(deckDeleteCard) || e.getSource().equals(cardDeleteCard)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.deleteCard();
            }
        }
        else if (e.getSource().equals(exportExportDeckIndividuallyPNG)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.exportDeckIndividuallyPNG();
            }
        }
        else if (e.getSource().equals(exportExportDeckIndividuallyJPG)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                manager.exportDeckIndividuallyJPG();
            }
        }
        else if (e.getSource().equals(exportExportDeckFullPNG)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                new DeckPrinter(frame, manager.getDeck()).exportDeckPagesPNG(getExportPath());
                JOptionPane.showMessageDialog(frame, "Export Complete!");
            }
        }
        else if (e.getSource().equals(exportExportDeckFullJPG)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                new DeckPrinter(frame, manager.getDeck()).exportDeckPagesJPG(getExportPath());
                JOptionPane.showMessageDialog(frame, "Export Complete!");
            }
        }
        else if (e.getSource().equals(exportExportToText)) {
            if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                DeckManager manager = (DeckManager) frame.getTabbedPane().getSelectedComponent();
                
                SaveFileDialog d = new SaveFileDialog(SaveFileDialog.filterForType(DialogFileType.Text));
                if(!d.showDialog(frame)) {
                    return;
                }

                if(new DeckPrinter(frame, manager.getDeck()).exportToText(d.getSelectedPath())) {
                    JOptionPane.showMessageDialog(frame, "Export Complete!");
                }
            }
        }
        else if (e.getSource().equals(version)) {
            JOptionPane.showMessageDialog(frame, GlobalVariables.VERSION, "Version Number", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String getExportPath() {
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        return dlg.showDialog(frame) ? dlg.getSelectedPath() : "";
    }

    public CreatorFrame getFrame() {
        return frame;
    }

    public void setFrame(CreatorFrame frame) {
        this.frame = frame;
    }

    public void deckPaneSelected() {
        deck.setVisible(true);
        deck.setEnabled(true);

        fileCloseCurrentTab.setVisible(true);
        fileCloseCurrentTab.setEnabled(true);

        fileSaveDeckAs.setVisible(true);
        fileSaveDeckAs.setEnabled(true);

        export.setEnabled(true);
        export.setVisible(true);

        exportExportCurrentTabJPG.setEnabled(false);
        exportExportCurrentTabJPG.setVisible(false);

        exportExportCurrentTabPNG.setEnabled(false);
        exportExportCurrentTabPNG.setVisible(false);

        exportExportDeckIndividuallyPNG.setEnabled(true);
        exportExportDeckIndividuallyPNG.setVisible(true);

        exportExportDeckIndividuallyJPG.setEnabled(true);
        exportExportDeckIndividuallyJPG.setVisible(true);

        exportExportDeckFullJPG.setEnabled(true);
        exportExportDeckFullJPG.setVisible(true);

        exportExportDeckFullPNG.setEnabled(true);
        exportExportDeckFullPNG.setVisible(true);

        exportExportToText.setEnabled(true);
        exportExportToText.setVisible(true);
    }

    public void cardPaneSelected() {
        deck.setVisible(false);
        deck.setEnabled(false);

        fileCloseCurrentTab.setVisible(true);
        fileCloseCurrentTab.setEnabled(true);

        fileSaveDeckAs.setVisible(false);
        fileSaveDeckAs.setEnabled(false);

        export.setEnabled(true);
        export.setVisible(true);

        exportExportCurrentTabJPG.setEnabled(true);
        exportExportCurrentTabJPG.setVisible(true);

        exportExportCurrentTabPNG.setEnabled(true);
        exportExportCurrentTabPNG.setVisible(true);

        exportExportDeckIndividuallyPNG.setEnabled(false);
        exportExportDeckIndividuallyPNG.setVisible(false);

        exportExportDeckIndividuallyJPG.setEnabled(false);
        exportExportDeckIndividuallyJPG.setVisible(false);

        exportExportDeckFullJPG.setEnabled(false);
        exportExportDeckFullJPG.setVisible(false);

        exportExportDeckFullPNG.setEnabled(false);
        exportExportDeckFullPNG.setVisible(false);

        exportExportToText.setEnabled(false);
        exportExportToText.setVisible(false);
    }

    public void noPaneSelected() {
        deck.setVisible(false);
        deck.setEnabled(false);

        fileCloseCurrentTab.setVisible(false);
        fileCloseCurrentTab.setEnabled(false);

        fileSaveDeckAs.setVisible(false);
        fileSaveDeckAs.setEnabled(false);

        export.setEnabled(false);
        export.setVisible(false);

        exportExportCurrentTabJPG.setEnabled(false);
        exportExportCurrentTabJPG.setVisible(false);

        exportExportCurrentTabPNG.setEnabled(false);
        exportExportCurrentTabPNG.setVisible(false);

        exportExportDeckIndividuallyPNG.setEnabled(false);
        exportExportDeckIndividuallyPNG.setVisible(false);

        exportExportDeckIndividuallyJPG.setEnabled(false);
        exportExportDeckIndividuallyJPG.setVisible(false);

        exportExportDeckFullJPG.setEnabled(false);
        exportExportDeckFullJPG.setVisible(false);

        exportExportDeckFullPNG.setEnabled(false);
        exportExportDeckFullPNG.setVisible(false);

        exportExportToText.setEnabled(false);
        exportExportToText.setVisible(false);
    }

    public JPopupMenu getCardMenu() {
        return cardMenu;
    }

    public void setCardMenu(JPopupMenu cardMenu) {
        this.cardMenu = cardMenu;
    }

    public JPopupMenu getFrameMenu() {
        return frameMenu;
    }

    public void setFrameMenu(JPopupMenu frameMenu) {
        this.frameMenu = frameMenu;
    }

}
