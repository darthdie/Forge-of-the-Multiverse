package uk.co.fostorial.sotm.structure;

import java.util.List;

public class VillainDeck extends Deck {
    public VillainDeck(String name) {
        this(name, "");
    }
    
    public VillainDeck(String name, String path) {
        super(DeckType.Villain, name, path);

        addCard(new VillainFrontCard("Villain Front", getNextIDInteger()));
        addCard(new VillainFrontCard("Villain Back", getNextIDInteger()));
        addCard(new BackCard("Card Back", getNextIDInteger()));
    }

    public VillainDeck(List<Card> cards, String name) {
        this(cards, name, "");
    }
    
    public VillainDeck(List<Card> cards, String name, String path) {
        super(DeckType.Villain, name, path);
        setCards(cards);
    }
}
