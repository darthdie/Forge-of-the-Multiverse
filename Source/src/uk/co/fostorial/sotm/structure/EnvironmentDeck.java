package uk.co.fostorial.sotm.structure;

import java.util.List;

public class EnvironmentDeck extends Deck {
    public EnvironmentDeck() {
        super(DeckType.Environment);

        addCard(new BackCard("Card Back", getNextIDInteger()));
    }

    public EnvironmentDeck(List<Card> cards) {
        super(DeckType.Environment);
        setCards(cards);
    }
}
