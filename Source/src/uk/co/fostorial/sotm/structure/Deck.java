package uk.co.fostorial.sotm.structure;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Deck implements PropertyChangeListener {
    private String name;
    private String path;
    private List<Card> cards = new ArrayList<>();

    private int nextID = 0;
    private final DeckType type;
    
    private final PropertyChangeSupport changes;

    public Deck(DeckType deckType, String name) {
        this(deckType, name, "");
    }
    
    public Deck(DeckType deckType, String name, String path) {
        this.changes = new PropertyChangeSupport(this);
        this.type = deckType;
        this.name = name;
        this.path = path;
    }

    public List<Card> getCards() {
        return cards;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String value) {
        path = value;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        setNextID(findHighestID(cards));
        
        for(Card c : cards) {
            c.addPropertyChangeListener(this);
        }
    }
    
    private int findHighestID(List<Card> cards) {
        int i = 0;
        for (Card c : cards) {
            if (c.getCardID() > i) {
                i = c.getCardID();
            }
        }

        return i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!Objects.equals(this.name, name)) {
            String oldValue = this.name;
            this.name = name;

            changes.firePropertyChange("name", oldValue, name);
            changes.firePropertyChange("displayName", "", getDisplayName());
        }
    }
    
    public String getDisplayName() {
        if(getIsDirty()) {
            return getName() + "*";
        }
        
        return getName();
    }
    
    public boolean getIsDirty() {
        return cards.stream().anyMatch((c) -> (c.getIsDirty()));
    }
    
    public void setIsDirty(boolean dirty) {
        cards.stream().forEach((c) -> c.setIsDirty(dirty));
    }
    
    public DeckType getType() {
        return type;
    }

    public void addCard(Card card) {
        boolean found = false;
        for (Card c : this.getCards()) {
            if (c.getCardID().equals(card.getCardID())) {
                found = true;
            }
        }
        
        if (found == true) {
            // TODO validation error
        }

        card.addPropertyChangeListener(this);
        
        card.setNumberInDeck(1);
        cards.add(card);
    }

    public void removeCard(Card card) {
        card.removePropertyChangeListener(this);
        cards.remove(card);
    }

    public int getNextID() {
        return nextID;
    }

    private void setNextID(int nextID) {
        this.nextID = nextID;
    }

    public Integer getNextIDInteger() {
        nextID++;
        return nextID;
    }

    public String getXML() {
        String xml = "";
        xml += "<?xml version= \"1.0\"?>\n";
        xml += "<deck>\n";

        String typeString = "deck";
        if (type == DeckType.Hero) {
            typeString = "herodeck";
        }
        else if (type == DeckType.Villain) {
            typeString = "villaindeck";
        }
        else if (type == DeckType.Environment) {
            typeString = "environmentdeck";
        }
        
        xml += " <type>" + typeString + "</type>\n";

        for (Card c : cards) {
            xml += c.getXML();
        }

        xml += "</deck>\n";
        xml += "</xml>\n";

        return xml;
    }

    public List<DeckStatistic> getStats() {
        List<DeckStatistic> stats = new ArrayList<>();

        Card front = null;
        Card back = null;

        int numberOfUniqueCards = 0;
        int numberOfCards = 0;
        int numberOfPowers = 0;
        int additionalHP = 0;
        List<String> numberOfClasses = new ArrayList<>();
        HashMap<String, Integer> classCounts = new HashMap<>();

        for (Card c : getCards()) {
            if (c instanceof HeroFrontCard) {
                front = c;
            }

            if (c instanceof HeroBackCard) {
                back = c;
            }

            if (c instanceof VillainFrontCard && front == null) {
                front = c;
            }

            if (c instanceof VillainFrontCard && front != null) {
                back = c;
            }

            if (c instanceof VillainCard || c instanceof HeroCard || c instanceof EnvironmentCard) {
                numberOfUniqueCards++;
                numberOfCards += c.getNumberInDeck();
                additionalHP += c.getHealthPointsInt() * c.getNumberInDeck();

                if (c instanceof VillainCard && ((VillainCard) c).getCardText().toLowerCase().contains("power:")) {
                    numberOfPowers++;
                }

                if (c instanceof HeroCard && ((HeroCard) c).getCardText().toLowerCase().contains("power:")) {
                    numberOfPowers++;
                }

                /* Determine class stats */
                String classes = c.getClasses().replace(" ", "");

                if (classes.contains(",") == false && classes.equals("N/A") == false) {
                    if (numberOfClasses.contains(classes) == false) {
                        numberOfClasses.add(classes);
                        classCounts.put(classes, c.getNumberInDeck());
                    } else {
                        Integer i = classCounts.get(classes);
                        if (i == null) {
                            i = 0;
                        }
                        
                        Integer ni = i + c.getNumberInDeck();
                        classCounts.put(classes, ni);
                    }
                }

                if (classes.contains(",")) {
                    String[] split = classes.split(",");
                    for (String s : split) {
                        if (numberOfClasses.contains(s) == false) {
                            numberOfClasses.add(s);
                            classCounts.put(s, c.getNumberInDeck());
                        } else {
                            Integer i = classCounts.get(s);
                            if (i == null) {
                                i = 0;
                            }
                            
                            Integer ni = i + c.getNumberInDeck();
                            classCounts.put(s, ni);
                        }
                    }
                }
            }
        }

        if (back instanceof HeroBackCard) {
            numberOfPowers++;
        }

        stats.add(new DeckStatistic("Unique Cards", "" + numberOfUniqueCards));
        stats.add(new DeckStatistic("# of Cards", "" + numberOfCards));
        stats.add(new DeckStatistic("# HP", "" + additionalHP));

        if (this instanceof HeroDeck) {
            stats.add(new DeckStatistic("# of Powers", "" + numberOfPowers));
        }

        stats.add(new DeckStatistic("# of Classes", "" + numberOfClasses.size()));

        for (String s : numberOfClasses) {
            Integer i = classCounts.get(s);
            if (i == null) {
                i = 0;
            }
            
            stats.add(new DeckStatistic("# of " + s, "" + i));
            stats.add(new DeckStatistic("% " + s, "" + (int) ((i.doubleValue() / (double) numberOfCards) * 100d) + "%"));
        }

        return stats;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equalsIgnoreCase("isDirty")) {
            changes.firePropertyChange("displayName", "", getDisplayName());
        } 
    }

    
    public enum DeckType {
        Hero,
        Villain,
        Environment
    }
}
