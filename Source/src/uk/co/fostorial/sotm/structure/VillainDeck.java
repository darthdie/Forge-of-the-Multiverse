package uk.co.fostorial.sotm.structure;

import java.util.List;

public class VillainDeck extends Deck {
    public VillainDeck(String name) {
        super(DeckType.Villain, name);

        addCard(new VillainFrontCard("Villain Front", getNextIDInteger()));
        addCard(new VillainFrontCard("Villain Back", getNextIDInteger()));
        addCard(new BackCard("Card Back", getNextIDInteger()));
    }

    public VillainDeck(List<Card> cards, String name) {
        super(DeckType.Villain, name);
        setCards(cards);
    }
}
