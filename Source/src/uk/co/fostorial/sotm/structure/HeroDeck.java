package uk.co.fostorial.sotm.structure;

import java.util.List;

public class HeroDeck extends Deck {
    public HeroDeck(String name) {
       this(name, "");
    }
        
    public HeroDeck(String name, String path) {
        super(DeckType.Hero, name, path);

        addCard(new HeroFrontCard("Hero Front", getNextIDInteger()));
        addCard(new HeroBackCard("Hero Back", getNextIDInteger()));
        addCard(new BackCard("Card Back", getNextIDInteger()));
    }   
    
    public HeroDeck(List<Card> cards, String name) {
        this(cards, name, "");
    }
    
    public HeroDeck(List<Card> cards, String name, String path) {
        super(DeckType.Hero, name, path);
        setCards(cards);
    }
}
