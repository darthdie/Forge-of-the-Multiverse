package uk.co.fostorial.sotm.structure;

import java.util.Objects;

public class Card {
    final static int HERO_FRONT = 0;
    final static int HERO_BACK = 1;
    final static int HERO_CARD = 2;
    final static int VILLAIN_FRONT = 3;
    final static int VILLAIN_BACK = 4;
    final static int VILLAIN_CARD = 5;
    final static int ENVIRONMENT_CARD = 6;
    final static int CARD_BACK = 7;

    private String name;
    private int cardType;
    private String classes;
    private String healthPoints;
    private int numberInDeck;
    private int cardID;
    private String portraitFile;
    private boolean isDirty;

    public Card(int cardType, Integer cardID) {
        this.cardType = cardType;
        this.cardID = cardID;
        this.numberInDeck = 0;
        this.isDirty = false;
    }
    
    public boolean getIsDirty() {
        return isDirty;
    }
    
    public void setIsDirty(boolean dirty) {
        isDirty = dirty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!Objects.equals(this.name, name)) {
            this.name = name;
            setIsDirty(true);
        }
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        if(this.cardType != cardType) {
            this.cardType = cardType;
            setIsDirty(true);
        }
    }

    public String getCardTypeString() {
        String ret = "";

        switch (cardType) {
            case HERO_FRONT:
                ret = "Hero Front";
                break;
            case HERO_BACK:
                ret = "Hero Back";
                break;
            case HERO_CARD:
                ret = "Hero Card";
                break;
            case VILLAIN_FRONT:
                ret = "Villain Character";
                break;
            case VILLAIN_BACK:
                ret = "Villain Character";
                break;
            case VILLAIN_CARD:
                ret = "Villain Card";
                break;
            case ENVIRONMENT_CARD:
                ret = "Environment Card";
                break;
            case CARD_BACK:
                ret = "Card Back";
                break;
        }

        return ret;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        if(!Objects.equals(this.classes, classes)) {
            this.classes = classes;
            setIsDirty(true);
        }
    }

    public String getHealthPoints() {
        return healthPoints;
    }

    public int getHealthPointsInt() {
        if (healthPoints != null) {
            try {
                Integer i = new Integer(healthPoints);
                return i;
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        
        return 0;
    }

    public void setHealthPoints(String healthPoints) {
        if(!Objects.equals(this.healthPoints, healthPoints)) {
            this.healthPoints = healthPoints;
            setIsDirty(true);
        }
    }

    public Integer getNumberInDeck() {
        return numberInDeck;
    }

    public void setNumberInDeck(int numberInDeck) {
        if(this.numberInDeck != numberInDeck) {
            this.numberInDeck = numberInDeck;
            setIsDirty(true);
        }
    }

    public Integer getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        if(this.cardID != cardID) {
            this.cardID = cardID;
            setIsDirty(true);
        }
    }

    public String getXML() {
        String xml = "";
        xml += " <card>";
        xml += " </card>\n";
        return xml;
    }

    public String getPortraitFile() {
        return portraitFile;
    }

    public void setPortraitFile(String portraitFile) {
        if(!Objects.equals(this.portraitFile, portraitFile)) {
            this.portraitFile = portraitFile;
            setIsDirty(true);
        }
    }
}