package uk.co.fostorial.sotm.structure;

import java.util.List;

public class EnvironmentDeck extends Deck {
    public EnvironmentDeck(String name) {
        super(DeckType.Environment, name);

        addCard(new BackCard("Card Back", getNextIDInteger()));
    }

    public EnvironmentDeck(List<Card> cards, String name) {
        super(DeckType.Environment, name);
        setCards(cards);
    }
}
