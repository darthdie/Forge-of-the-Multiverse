package uk.co.fostorial.sotm.structure;

import java.util.List;

public class HeroDeck extends Deck {
    public HeroDeck(String name) {
        super(DeckType.Hero, name);

        addCard(new HeroFrontCard("Hero Front", getNextIDInteger()));
        addCard(new HeroBackCard("Hero Back", getNextIDInteger()));
        addCard(new BackCard("Card Back", getNextIDInteger()));

    }
    
    public HeroDeck(List<Card> cards, String name) {
        super(DeckType.Hero, name);
        setCards(cards);
    }
}
