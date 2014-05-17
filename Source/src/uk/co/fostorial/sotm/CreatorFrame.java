package uk.co.fostorial.sotm;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;
import javafx.stage.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    
    private JFileChooser chooser = new JFileChooser();
    
    public CreatorFrame() {
        setupFrame();
        this.setVisible(true);
    }
    
    private void setupFrame() {
        addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            case FILE_NEW_HERO_DECK:
                tabbedPane.addTab("New Hero Deck", new DeckManager(DeckManager.HERO_MODE, null, this));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
                break;
            case FILE_NEW_VILLAIN_DECK:
                tabbedPane.addTab("New Villain Deck", new DeckManager(DeckManager.VILLAIN_MODE, null, this));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
                break;
            case FILE_NEW_ENVIRONMENT_DECK:
                tabbedPane.addTab("New Environment Deck", new DeckManager(DeckManager.ENVIRONMENT_MODE, null, this));
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent().requestFocus();
                break;
            case FILE_OPEN_HERO_DECK:
                String path = browseForFile();
                if (path.equals("")) {
                    break;
                }
                
                Deck deck = DeckDocument.create(path).parse();
                if (deck == null) {
                    break;
                }
                
                if (deck.getType() == Deck.DeckType.Hero) {
                    tabbedPane.addTab(deck.getName(), new DeckManager(DeckManager.HERO_MODE, deck, this));
                } else if (deck.getType() == Deck.DeckType.Villain) {
                    tabbedPane.addTab(deck.getName(), new DeckManager(DeckManager.VILLAIN_MODE, deck, this));
                } else if (deck.getType() == Deck.DeckType.Environment) {
                    tabbedPane.addTab(deck.getName(), new DeckManager(DeckManager.ENVIRONMENT_MODE, deck, this));
                }
                
                tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
                break;
        }
    }
    
    private String browseForFile() {
        int outcome = chooser.showOpenDialog(this);
        if (outcome != JFileChooser.APPROVE_OPTION) {
            return "";
        }
        
        return chooser.getSelectedFile().getAbsolutePath();
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
            } catch (ClassNotFoundException e1) {
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
    
    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
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
    
    public JFileChooser getChooser() {
        return chooser;
    }
    
    public void setChooser(JFileChooser chooser) {
        this.chooser = chooser;
    }

    @Override
    public void windowOpened(java.awt.event.WindowEvent e) {
       
    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        ApplicationPreferences.setLastPath(chooser.getSelectedFile());
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
}
