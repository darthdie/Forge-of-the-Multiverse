package uk.co.fostorial.sotm;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import say.swing.JFontChooser;
import uk.co.fostorial.sotm.deck.DeckManager;
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
import uk.co.fostorial.sotm.structure.DeckDocument;
import uk.co.fostorial.sotm.structure.EnvironmentCard;
import uk.co.fostorial.sotm.structure.HeroBackCard;
import uk.co.fostorial.sotm.structure.HeroCard;
import uk.co.fostorial.sotm.structure.HeroFrontCard;
import uk.co.fostorial.sotm.structure.VillainCard;
import uk.co.fostorial.sotm.structure.VillainFrontCard;

public class CreatorFrame extends JFrame implements ChangeListener, WindowListener {

    private static final long serialVersionUID = 8105592648557148065L;

    final static public int FILE_NEW_HERO_FRONT = 1;
    final static public int FILE_NEW_HERO_BACK = 2;
    final static public int FILE_NEW_HERO_CARD = 3;
    final static public int FILE_NEW_VILLIAN_FRONT = 4;
    final static public int FILE_NEW_VILLIAN_CARD = 5;
    final static public int FILE_NEW_CARD_BACK = 6;
    final static public int FILE_NEW_HERO_DECK = 7;
    final static public int FILE_OPEN_HERO_DECK = 8;
    final static public int FILE_NEW_VILLAIN_DECK = 9;
    final static public int FILE_NEW_ENVIRONMENT_DECK = 10;
    final static public int FILE_NEW_ENVIRONMENT_CARD = 11;

    private CreatorMenuBar creatorMenuBar;

    private JTabbedPane tabbedPane;

    private final JFileChooser chooser = new JFileChooser();
    private final JFontChooser fontChooser = new JFontChooser();

    public CreatorFrame() {
        setupFrame();
        this.setVisible(true);
    }

    private void setupFrame() {
        addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Forge of the Multiverse");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setCurrentDirectory(ApplicationPreferences.getLastPath());

        creatorMenuBar = new CreatorMenuBar(this);
        this.setJMenuBar(creatorMenuBar);

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        tabbedPane.addMouseListener(new FrameMouseAdapter(this));
        this.add(tabbedPane, BorderLayout.CENTER);

        enableOSXFullscreen(this);
        creatorMenuBar.noPaneSelected();
    }

    public void newWindow(int type, Card card) {
        switch (type) {
            case FILE_NEW_HERO_FRONT:
                tabbedPane.addTab(card.getName(), new CreatorTabHeroFront(this, (HeroFrontCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_HERO_BACK:
                tabbedPane.addTab(card.getName(), new CreatorTabHeroBack(this, (HeroBackCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_HERO_CARD:
                tabbedPane.addTab(card.getName(), new CreatorTabHeroCard(this, (HeroCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_VILLIAN_FRONT:
                tabbedPane.addTab("New Villain Character", new CreatorTabVillainFront(this, (VillainFrontCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_VILLIAN_CARD:
                tabbedPane.addTab("New Villian Card", new CreatorTabVillainCard(this, (VillainCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_ENVIRONMENT_CARD:
                tabbedPane.addTab("New Environment Card", new CreatorTabEnvironmentCard(this, (EnvironmentCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_CARD_BACK:
                tabbedPane.addTab(card.getName(), new CreatorTabCardBack(this, (BackCard) card));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
            case FILE_NEW_HERO_DECK: {
                DeckManager d = new DeckManager(DeckManager.HERO_MODE, null, this);
                tabbedPane.addTab(d.getDeckDisplayName(), d);
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
            }
            break;
            case FILE_NEW_VILLAIN_DECK: {
                DeckManager d = new DeckManager(DeckManager.VILLAIN_MODE, null, this);
                tabbedPane.addTab(d.getDeckDisplayName(), d);
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
            }
            break;
            case FILE_NEW_ENVIRONMENT_DECK: {
                DeckManager d = new DeckManager(DeckManager.ENVIRONMENT_MODE, null, this);
                tabbedPane.addTab(d.getDeckDisplayName(), d);
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
            }
            break;
            case FILE_OPEN_HERO_DECK:
                String path = browseForLoadPath(BrowserFileType.Deck);
                if (path.equals("")) {
                    break;
                }

                Deck deck = DeckDocument.create(path).parse();
                if (deck == null) {
                    break;
                }

                if (deck.getType() == Deck.DeckType.Hero) {
                    tabbedPane.addTab(deck.getDisplayName(), new DeckManager(DeckManager.HERO_MODE, deck, this));
                } else if (deck.getType() == Deck.DeckType.Villain) {
                    tabbedPane.addTab(deck.getDisplayName(), new DeckManager(DeckManager.VILLAIN_MODE, deck, this));
                } else if (deck.getType() == Deck.DeckType.Environment) {
                    tabbedPane.addTab(deck.getDisplayName(), new DeckManager(DeckManager.ENVIRONMENT_MODE, deck, this));
                }

                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
        }
    }

    public void closeCurrentFrame() {
        if (tabbedPane.getComponentCount() > 0) {
            tabbedPane.remove(tabbedPane.getSelectedIndex());
        }
    }

    public void exportToJPEG() {
        if (tabbedPane.getComponentCount() > 0) {
            CreatorTab creatorTab = (CreatorTab) tabbedPane.getSelectedComponent();
            creatorTab.saveToJPG();
        }
    }

    public void exportToPNG() {
        if (tabbedPane.getComponentCount() > 0) {
            CreatorTab creatorTab = (CreatorTab) tabbedPane.getSelectedComponent();
            creatorTab.saveToPNG();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void enableOSXFullscreen(Window window) {
        if (window != null) {
            try {
                Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
                Class params[] = new Class[]{Window.class, Boolean.TYPE};
                Method method = util.getMethod("setWindowCanFullScreen", params);
                method.invoke(util, window, true);
            } catch (Exception e) {
            }
        }
    }

    public CreatorMenuBar getCreatorMenuBar() {
        return creatorMenuBar;
    }

    public void setCreatorMenuBar(CreatorMenuBar creatorMenuBar) {
        this.creatorMenuBar = creatorMenuBar;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane pane = (JTabbedPane) e.getSource();

        if (pane.getSelectedIndex() == -1) {
            creatorMenuBar.noPaneSelected();
        } else if (pane.getSelectedComponent() instanceof DeckManager) {
            creatorMenuBar.deckPaneSelected();
            pane.getSelectedComponent().repaint();
            DeckManager deckManager = (DeckManager) pane.getSelectedComponent();
            deckManager.loadPreview();
        } else if (pane.getSelectedComponent() instanceof CreatorTab) {
            creatorMenuBar.cardPaneSelected();
        }
    }

    public Font editFont(Font font) {
        fontChooser.setSelectedFont(font);

        if (fontChooser.showDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fontChooser.getSelectedFont();
        }

        return null;
    }

    public String browseForSavePath(BrowserFileType type) {
        FileNameExtensionFilter filter = null;
        switch (type) {
            case Image:
                filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                break;
            case Text:
                filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
                break;
            case Deck:
            case XML:
                filter = new FileNameExtensionFilter("XML Files (*.xml)", "xml");
                break;
            case JPG:
                filter = new FileNameExtensionFilter("JPG Files (*.jpg)", "jpg");
                break;
            case PNG:
                filter = new FileNameExtensionFilter("PNG Files (*.png)", "png");
                break;
        }

        if (filter != null) {
            chooser.addChoosableFileFilter(filter);
            chooser.setFileFilter(filter);
        }

        if (type == BrowserFileType.Directory) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.validate();
        } else {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.validate();
        }

        String path = "";
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();

            switch (type) {
                case JPG:
                    if (path.toLowerCase().endsWith(".jpg") == false
                            || path.toLowerCase().endsWith(".jpeg") == false) {
                        path = path + ".jpg";
                    }
                    break;
                case PNG:
                    if (path.toLowerCase().endsWith(".png") == false) {
                        path = path + ".png";
                    }
                    break;
                case Text:
                    if (path.toLowerCase().endsWith(".txt") == false) {
                        path = path + ".txt";
                    }
                    break;
                case Deck:
                case XML:
                    if (path.toLowerCase().endsWith(".xml") == false) {
                        path = path + ".xml";
                    }
                    break;
            }
        }

        if (filter != null) {
            chooser.removeChoosableFileFilter(filter);
        }

        return path;
    }

    public String browseForLoadPath(BrowserFileType type) {
        FileNameExtensionFilter filter = null;
        switch (type) {
            case Image:
                filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                break;
            case Text:
                filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
                break;
            case Deck:
            case XML:
                filter = new FileNameExtensionFilter("XML Files (*.xml)", "xml");
                break;
            case JPG:
                filter = new FileNameExtensionFilter("JPG Files (*.jpg)", "jpg");
                break;
            case PNG:
                filter = new FileNameExtensionFilter("PNG Files (*.png)", "png");
                break;
        }

        if (filter != null) {
            chooser.addChoosableFileFilter(filter);
            chooser.setFileFilter(filter);
        }

        if (type == BrowserFileType.Directory) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.validate();
        } else {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.validate();
        }

        String path = "";
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }

        if (filter != null) {
            chooser.removeChoosableFileFilter(filter);
        }

        return path;
    }

    public enum BrowserFileType {

        Image,
        Deck,
        Text,
        Directory,
        XML,
        JPG,
        PNG
    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        ApplicationPreferences.setLastPath(chooser.getSelectedFile());

        if (hasUnsavedChanges()) {
            int option = JOptionPane.showConfirmDialog(this,
                    "There are unsaved changes, would you like to save before quiting?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                if (!saveChanges()) {
                    return;
                }
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        System.exit(0);
    }

    private boolean hasUnsavedChanges() {
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            Component c = tabbedPane.getComponent(i);
            if (c instanceof DeckManager) {
                if (((DeckManager) c).getDeck().getIsDirty()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean saveChanges() {
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            Component c = tabbedPane.getComponent(i);
            if (c instanceof DeckManager) {
                DeckManager d = (DeckManager) c;
                if (d.getDeck().getIsDirty()) {
                    if (!d.saveDeck()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void windowOpened(java.awt.event.WindowEvent e) {
    }

    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
    }

    @Override
    public void windowIconified(java.awt.event.WindowEvent e) {
    }

    @Override
    public void windowDeiconified(java.awt.event.WindowEvent e) {
    }

    @Override
    public void windowActivated(java.awt.event.WindowEvent e) {
    }

    @Override
    public void windowDeactivated(java.awt.event.WindowEvent e) {
    }
}
