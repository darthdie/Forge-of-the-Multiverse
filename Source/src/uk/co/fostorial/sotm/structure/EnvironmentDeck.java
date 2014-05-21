package uk.co.fostorial.sotm.structure;

import java.util.List;

public class EnvironmentDeck extends Deck {
    public EnvironmentDeck(String name) {
        this(name, "");
    }
    
    public EnvironmentDeck(String name, String path) {
        super(DeckType.Environment, name, path);

        addCard(new BackCard("Card Back", getNextIDInteger()));
    }

    public EnvironmentDeck(List<Card> cards, String name) {
        this(cards, name, "");
    }
    
    public EnvironmentDeck(List<Card> cards, String name, String path) {
        super(DeckType.Environment, name, path);
        setCards(cards);
    }
}
