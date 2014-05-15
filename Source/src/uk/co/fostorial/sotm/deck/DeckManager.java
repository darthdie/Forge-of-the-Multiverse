package uk.co.fostorial.sotm.deck;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.co.fostorial.sotm.CreatorFrame;
import uk.co.fostorial.sotm.design.CreatorTab;
import uk.co.fostorial.sotm.design.CreatorTabCardBack;
import uk.co.fostorial.sotm.design.CreatorTabEnvironmentCard;
import uk.co.fostorial.sotm.design.CreatorTabHeroBack;
import uk.co.fostorial.sotm.design.CreatorTabHeroCard;
import uk.co.fostorial.sotm.design.CreatorTabHeroFront;
import uk.co.fostorial.sotm.design.CreatorTabVillainCard;
import uk.co.fostorial.sotm.design.CreatorTabVillainFront;
import uk.co.fostorial.sotm.structure.BackCard;
import uk.co.fostorial.sotm.structure.Card;
import uk.co.fostorial.sotm.structure.Deck;
import uk.co.fostorial.sotm.structure.EnvironmentCard;
import uk.co.fostorial.sotm.structure.EnvironmentDeck;
import uk.co.fostorial.sotm.structure.HeroBackCard;
import uk.co.fostorial.sotm.structure.HeroCard;
import uk.co.fostorial.sotm.structure.HeroDeck;
import uk.co.fostorial.sotm.structure.HeroFrontCard;
import uk.co.fostorial.sotm.structure.VillainCard;
import uk.co.fostorial.sotm.structure.VillainDeck;
import uk.co.fostorial.sotm.structure.VillainFrontCard;

public final class DeckManager extends JSplitPane implements ListSelectionListener {

    private static final long serialVersionUID = 7972443091809248703L;

    public final static int VILLAIN_MODE = 0;
    public final static int HERO_MODE = 1;
    public final static int ENVIRONMENT_MODE = 2;

    private JScrollPane cardTableScroll;
    private JTable cardTable;
    private JSplitPane horizontalSplit;
    private JLabel preview;
    private JScrollPane deckStatScroll;
    private JTable deckStatTable;

    private int deckMode;
    private Deck deck;
    private Card selectedCard;

    private final CreatorFrame frame;

    private String deckName;

    private CreatorTab creator;

    public DeckManager(int deckMode, Deck deck, CreatorFrame frame) {
        this.setDeckMode(deckMode);
        this.deck = deck;
        this.frame = frame;

        if (deck == null) {
            newDeck();
        }

        setup();
    }

    private void setup() {
        this.setAutoscrolls(true);

        horizontalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        horizontalSplit.setAutoscrolls(true);
        horizontalSplit.setDividerLocation(300);

        ImageIcon ii = new ImageIcon();
        preview = new JLabel(ii);
        preview.setBounds(0, 0, 230, 300);
        horizontalSplit.setTopComponent(preview);

        deckStatScroll = new JScrollPane();
        deckStatScroll.setAutoscrolls(true);
        deckStatTable = new JTable(new DeckStatisticsTableModel(deck));
        deckStatTable.setFillsViewportHeight(true);
        deckStatTable.setEnabled(false);
        //cardTable.setAutoCreateRowSorter(true);
        deckStatTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deckStatScroll.setViewportView(deckStatTable);
        horizontalSplit.setBottomComponent(deckStatScroll);

        this.setRightComponent(horizontalSplit);

        cardTableScroll = new JScrollPane();
        cardTableScroll.setAutoscrolls(true);
        cardTable = new JTable(new DeckManagerTableModel(deck));
        cardTable.setFillsViewportHeight(true);
        //cardTable.setAutoCreateRowSorter(true);
        cardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardTable.getSelectionModel().addListSelectionListener(this);
        cardTableScroll.setViewportView(cardTable);
        cardTable.setRowSelectionInterval(0, 0);
        cardTable.addMouseListener(new DeckMouseAdapter(frame));
        this.setLeftComponent(cardTableScroll);

        this.setDividerLocation(frame.getTabbedPane().getWidth() - 250);

    }

    public void newDeck() {
        switch (deckMode) {
            case DeckManager.VILLAIN_MODE:
                deck = new VillainDeck();
                deck.setName("New Villain Deck");
                break;
            case DeckManager.ENVIRONMENT_MODE:
                deck = new EnvironmentDeck();
                deck.setName("New Environment Deck");
                break;
            case DeckManager.HERO_MODE:
                deck = new HeroDeck();
                deck.setName("New Hero Deck");
                break;
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getDeckMode() {
        return deckMode;
    }

    public void setDeckMode(int deckMode) {
        this.deckMode = deckMode;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Integer id = (Integer) cardTable.getValueAt(cardTable.getSelectedRow(), ((DeckManagerTableModel) cardTable.getModel()).ID);

        for (Card card : deck.getCards()) {
            if (card.getCardID().equals(id)) {
                selectedCard = card;
            }
        }

        loadPreview();
    }

    public void loadPreview() {
        if (selectedCard != null) {
            if (selectedCard instanceof HeroFrontCard) {
                creator = new CreatorTabHeroFront(frame, (HeroFrontCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof HeroBackCard) {
                creator = new CreatorTabHeroBack(frame, (HeroBackCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof BackCard) {
                creator = new CreatorTabCardBack(frame, (BackCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof HeroCard) {
                creator = new CreatorTabHeroCard(frame, (HeroCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof VillainFrontCard) {
                creator = new CreatorTabVillainFront(frame, (VillainFrontCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof VillainCard) {
                creator = new CreatorTabVillainCard(frame, (VillainCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }

            if (selectedCard instanceof EnvironmentCard) {
                creator = new CreatorTabEnvironmentCard(frame, (EnvironmentCard) selectedCard);

                ImageIcon ii = new ImageIcon(creator.getImage(214, 300));
                preview.setIcon(ii);
            }
        }
    }

    public void addCardToDeck() {
        Card newCard = null;
        switch (deckMode) {
            case DeckManager.VILLAIN_MODE:
                newCard = new VillainCard("Villain Card", deck.getNextIDInteger());
                deck.addCard(newCard);
                break;
            case DeckManager.ENVIRONMENT_MODE:
                newCard = new EnvironmentCard("Environment Card", deck.getNextIDInteger());
                deck.addCard(newCard);
                break;
            case DeckManager.HERO_MODE:
                newCard = new HeroCard("Hero Card", deck.getNextIDInteger());
                deck.addCard(newCard);
                break;
        }
        
        selectedCard = newCard;
        
        cardTable.repaint();
        deckStatTable.repaint();
    }

    public void increaseNumberInDeck() {
        if (selectedCard != null && (selectedCard instanceof HeroCard || selectedCard instanceof VillainCard || selectedCard instanceof EnvironmentCard)) {
            int number = selectedCard.getNumberInDeck();
            number++;
            selectedCard.setNumberInDeck(number);
            cardTable.repaint();
            deckStatTable.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "There can be only one!", "Highlander Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void decreaseNumberInDeck() {
        if (selectedCard != null && (selectedCard instanceof HeroCard || selectedCard instanceof VillainCard || selectedCard instanceof EnvironmentCard)) {
            int number = selectedCard.getNumberInDeck();
            if (number > 1) {
                number--;
                selectedCard.setNumberInDeck(number);
                cardTable.repaint();
                deckStatTable.repaint();
            }
        }
    }

    public void deleteCard() {
        if (selectedCard != null && (selectedCard instanceof HeroCard || selectedCard instanceof VillainCard || selectedCard instanceof EnvironmentCard)) {
            int outcome = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this card?");

            if (outcome == JOptionPane.OK_OPTION) {
                deck.removeCard(selectedCard);
                cardTable.setRowSelectionInterval(0, 0);
                cardTable.repaint();
                deckStatTable.repaint();
            }
        }
    }

    public void editCard() {
        if (selectedCard == null) {
            return;
        }
        
        if (selectedCard instanceof HeroFrontCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_HERO_FRONT, selectedCard);
        }
        else if (selectedCard instanceof HeroBackCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_HERO_BACK, selectedCard);
        }
        else if (selectedCard instanceof HeroCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_HERO_CARD, selectedCard);
        }
        else if (selectedCard instanceof BackCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_CARD_BACK, selectedCard);
        }
        else if (selectedCard instanceof VillainFrontCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_VILLIAN_FRONT, selectedCard);
        }
        else if (selectedCard instanceof VillainCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_VILLIAN_CARD, selectedCard);
        }
        else if (selectedCard instanceof EnvironmentCard) {
            frame.newWindow(CreatorFrame.FILE_NEW_ENVIRONMENT_CARD, selectedCard);
        }
    }

    public void saveDeck() {
        try {
            JFileChooser chooser = frame.getChooser();
            int outcome = chooser.showSaveDialog(this);

            if (outcome == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try (FileWriter fstream = new FileWriter(f); BufferedWriter out = new BufferedWriter(fstream)) {
                    out.write(deck.getXML());      
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public CreatorTab getCreator() {
        return creator;
    }

    public void setCreator(CreatorTab creator) {
        this.creator = creator;
    }

    public void exportDeckIndividuallyPNG() {
        exportDeckIndividually("png");
    }

    public void exportDeckIndividuallyJPG() {
        exportDeckIndividually("jpg");
    }

    private void exportDeckIndividually(String type) {
        CreatorTab creator = null;

        JFileChooser chooser = frame.getChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int outcome = chooser.showSaveDialog(frame);

        if (outcome == JFileChooser.APPROVE_OPTION) {
            for (Card c : deck.getCards()) {
                if (c != null) {
                    if (c instanceof HeroFrontCard) {
                        creator = new CreatorTabHeroFront(frame, (HeroFrontCard) c);
                    }

                    if (c instanceof HeroBackCard) {
                        creator = new CreatorTabHeroBack(frame, (HeroBackCard) c);
                    }

                    if (c instanceof BackCard) {
                        creator = new CreatorTabCardBack(frame, (BackCard) c);
                    }

                    if (c instanceof HeroCard) {
                        creator = new CreatorTabHeroCard(frame, (HeroCard) c);
                    }

                    if (c instanceof VillainFrontCard) {
                        creator = new CreatorTabVillainFront(frame, (VillainFrontCard) c);
                    }

                    if (c instanceof VillainCard) {
                        creator = new CreatorTabVillainCard(frame, (VillainCard) c);
                    }

                    if (c instanceof EnvironmentCard) {
                        creator = new CreatorTabEnvironmentCard(frame, (EnvironmentCard) c);
                    }

                    if (type.equals("png")) {
                        creator.saveToPNG(chooser.getSelectedFile().getAbsolutePath());
                    }

                    if (type.equals("jpg")) {
                        creator.saveToJPG(chooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }

            JOptionPane.showMessageDialog(frame, "Export Complete!");
        }

        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public JScrollPane getDeckStatScroll() {
        return deckStatScroll;
    }

    public void setDeckStatScroll(JScrollPane deckStatScroll) {
        this.deckStatScroll = deckStatScroll;
    }

    public JTable getDeckStatTable() {
        return deckStatTable;
    }

    public void setDeckStatTable(JTable deckStatTable) {
        this.deckStatTable = deckStatTable;
    }
    
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
                JTable table =(JTable) e.getSource();
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if(row == -1) {
                    return;
                }
        
                if (frame.getTabbedPane().getSelectedComponent() instanceof DeckManager) {
                    DeckManager manager = (DeckManager)frame.getTabbedPane().getSelectedComponent();
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
}
