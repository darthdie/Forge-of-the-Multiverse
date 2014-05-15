package uk.co.fostorial.sotm.structure;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Deck {
    private String name;
    private List<Card> cards = new ArrayList<>();

    private int nextID = 0;
    private final DeckType type;

    public Deck(DeckType deckType) {
        type = deckType;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        card.setNumberInDeck(1);
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public int getNextID() {
        return nextID;
    }

    public void setNextID(int nextID) {
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
    
    public static Deck loadFromFile(String path) {
        Deck deck = null;
        try {
            File f = new File(path);

            Document document = Jsoup.parse(f, null);

            String deckType = "deck";
            Elements els = document.getElementsByTag("type");
            for (Element el : els) {
                deckType = el.text();
            }

            List<Card> cards = new ArrayList<>();

            els = document.getElementsByTag("herofrontcard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                HeroFrontCard card = new HeroFrontCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setPowerName(findElement(el, "powername"));
                card.setPowerText(findElement(el, "powertext"));
                card.setNemesisPath(findElement(el, "nemesispath"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setColor(new Color(Integer.parseInt(findElement(el, "powercolor"))));

                if (findElement(el, "namefontcolor").isEmpty() == false) {
                    card.setNameFontColor(new Color(new Integer(findElement(el, "namefontcolor"))));
                }

                if (findElement(el, "hpfontcolor").isEmpty() == false) {
                    card.setHpFontColor(new Color(new Integer(findElement(el, "hpfontcolor"))));
                }

                if (findElement(el, "powernamefontcolor").isEmpty() == false) {
                    card.setPowerNameFontColor(new Color(new Integer(findElement(el, "powernamefontcolor"))));
                }

                if (findElement(el, "powerfontcolor").isEmpty() == false) {
                    card.setPowerFontColor(new Color(new Integer(findElement(el, "powerfontcolor"))));
                }

                if (findFontElement(el, "namefont") != null) {
                    card.setNameFont(findFontElement(el, "namefont"));
                }

                if (findFontElement(el, "powernamefont") != null) {
                    card.setPowerNameFont(findFontElement(el, "powernamefont"));
                }

                if (findFontElement(el, "hpfont") != null) {
                    card.setHpFont(findFontElement(el, "hpfont"));
                }

                if (findFontElement(el, "powerfont") != null) {
                    card.setPowerFont(findFontElement(el, "powerfont"));
                }

                cards.add(card);
            }

            els = document.getElementsByTag("herobackcard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                HeroBackCard card = new HeroBackCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setTextColour(new Color(new Integer(findElement(el, "abilitycolor"))));
                card.setAbilityLine1(findElement(el, "abilityline1"));
                card.setAbilityLine2(findElement(el, "abilityline2"));
                card.setAbilityLine3(findElement(el, "abilityline3"));
                card.setAbilityLine4(findElement(el, "abilityline4"));
                card.setAbilityLine5(findElement(el, "abilityline5"));
                card.setAbilityLine6(findElement(el, "abilityline6"));

                if (findElement(el, "textfontcolor").isEmpty() == false) {
                    card.setTextFontColor(new Color(new Integer(findElement(el, "textfontcolor")).intValue()));
                }

                if (findFontElement(el, "textfont") != null) {
                    card.setTextFont(findFontElement(el, "textfont"));
                }

                cards.add(card);
            }

            els = document.getElementsByTag("villainfrontcard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                VillainFrontCard card = new VillainFrontCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setDescription1(findElement(el, "description1"));
                card.setDescription2(findElement(el, "description2"));
                card.setNemesisPath(findElement(el, "nemesispath"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setColor(new Color(new Integer(findElement(el, "descriptioncolor"))));
                card.setClassColor(new Color(new Integer(findElement(el, "classcolor"))));

                if (findElement(el, "namefontcolor").isEmpty() == false) {
                    card.setNameFontColor(new Color(new Integer(findElement(el, "namefontcolor"))));
                }

                if (findElement(el, "hpfontcolor").isEmpty() == false) {
                    card.setHpFontColor(new Color(new Integer(findElement(el, "hpfontcolor"))));
                }

                if (findElement(el, "classfontcolor").isEmpty() == false) {
                    card.setClassFontColor(new Color(new Integer(findElement(el, "classfontcolor"))));
                }

                if (findElement(el, "descriptionfontcolor").isEmpty() == false) {
                    card.setDescriptionFontColor(new Color(new Integer(findElement(el, "descriptionfontcolor"))));
                }

                if (findFontElement(el, "namefont") != null) {
                    card.setNameFont(findFontElement(el, "namefont"));
                }

                if (findFontElement(el, "classfont") != null) {
                    card.setClassFont(findFontElement(el, "classfont"));
                }

                if (findFontElement(el, "hpfont") != null) {
                    card.setHpFont(findFontElement(el, "hpfont"));
                }

                if (findFontElement(el, "descriptionfont") != null) {
                    card.setDescriptionFont(findFontElement(el, "descriptionfont"));
                }

                cards.add(card);
            }

            els = document.getElementsByTag("backcard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                BackCard card = new BackCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                cards.add(card);
            }

            els = document.getElementsByTag("herocard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                HeroCard card = new HeroCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setHealthPointsImage(findElement(el, "healthpointsimage"));
                card.setHealthPointsVisible(new Boolean(findElement(el, "healthpointsvisible")));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setQuoteString1(findElement(el, "quotestring1"));
                card.setQuoteString2(findElement(el, "quotestring2"));
                card.setIssueString(findElement(el, "issuestring"));
                card.setNameColor(new Color(new Integer(findElement(el, "namecolour")).intValue()));
                card.setClassColor(new Color(new Integer(findElement(el, "classcolour")).intValue()));
                card.setCardText(findElement(el, "cardtext"));

                if (findElement(el, "namefontcolor").isEmpty() == false) {
                    card.setNameFontColor(new Color(new Integer(findElement(el, "namefontcolor")).intValue()));
                }

                if (findElement(el, "hpfontcolor").isEmpty() == false) {
                    card.setHpFontColor(new Color(new Integer(findElement(el, "hpfontcolor")).intValue()));
                }

                if (findElement(el, "classfontcolor").isEmpty() == false) {
                    card.setClassFontColor(new Color(new Integer(findElement(el, "classfontcolor")).intValue()));
                }

                if (findElement(el, "descriptionfontcolor").isEmpty() == false) {
                    card.setDescriptionFontColor(new Color(new Integer(findElement(el, "descriptionfontcolor")).intValue()));
                }

                if (findElement(el, "quotefontcolor").isEmpty() == false) {
                    card.setQuoteFontColor(new Color(new Integer(findElement(el, "quotefontcolor")).intValue()));
                }

                if (findFontElement(el, "namefont") != null) {
                    card.setNameFont(findFontElement(el, "namefont"));
                }

                if (findFontElement(el, "classfont") != null) {
                    card.setClassFont(findFontElement(el, "classfont"));
                }

                if (findFontElement(el, "hpfont") != null) {
                    card.setHpFont(findFontElement(el, "hpfont"));
                }

                if (findFontElement(el, "descriptionfont") != null) {
                    card.setDescriptionFont(findFontElement(el, "descriptionfont"));
                }

                if (findFontElement(el, "quotefont") != null) {
                    card.setQuoteFont(findFontElement(el, "quotefont"));
                }

                cards.add(card);
            }

            els = document.getElementsByTag("villaincard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                VillainCard card = new VillainCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setHealthPointsImage(findElement(el, "healthpointsimage"));
                card.setHealthPointsVisible(new Boolean(findElement(el, "healthpointsvisible")));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setQuoteString1(findElement(el, "quotestring1"));
                card.setQuoteString2(findElement(el, "quotestring2"));
                card.setIssueString(findElement(el, "issuestring"));
                card.setNameColor(new Color(new Integer(findElement(el, "namecolour")).intValue()));
                card.setClassColor(new Color(new Integer(findElement(el, "classcolour")).intValue()));
                card.setCardText(findElement(el, "cardtext"));

                if (findElement(el, "namefontcolor").isEmpty() == false) {
                    card.setNameFontColor(new Color(new Integer(findElement(el, "namefontcolor")).intValue()));
                }

                if (findElement(el, "hpfontcolor").isEmpty() == false) {
                    card.setHpFontColor(new Color(new Integer(findElement(el, "hpfontcolor")).intValue()));
                }

                if (findElement(el, "classfontcolor").isEmpty() == false) {
                    card.setClassFontColor(new Color(new Integer(findElement(el, "classfontcolor")).intValue()));
                }

                if (findElement(el, "descriptionfontcolor").isEmpty() == false) {
                    card.setDescriptionFontColor(new Color(new Integer(findElement(el, "descriptionfontcolor")).intValue()));
                }

                if (findElement(el, "quotefontcolor").isEmpty() == false) {
                    card.setQuoteFontColor(new Color(new Integer(findElement(el, "quotefontcolor")).intValue()));
                }

                if (findFontElement(el, "namefont") != null) {
                    card.setNameFont(findFontElement(el, "namefont"));
                }

                if (findFontElement(el, "classfont") != null) {
                    card.setClassFont(findFontElement(el, "classfont"));
                }

                if (findFontElement(el, "hpfont") != null) {
                    card.setHpFont(findFontElement(el, "hpfont"));
                }

                if (findFontElement(el, "descriptionfont") != null) {
                    card.setDescriptionFont(findFontElement(el, "descriptionfont"));
                }

                if (findFontElement(el, "quotefont") != null) {
                    card.setQuoteFont(findFontElement(el, "quotefont"));
                }

                cards.add(card);
            }

            els = document.getElementsByTag("environmentcard");
            for (Element el : els) {
                Integer id = new Integer(findElement(el, "id"));
                String name = findElement(el, "name");
                EnvironmentCard card = new EnvironmentCard(name, id);
                card.setClasses(findElement(el, "classes"));
                card.setHealthPoints(findElement(el, "healthpoints"));
                card.setHealthPointsImage(findElement(el, "healthpointsimage"));
                card.setHealthPointsVisible(new Boolean(findElement(el, "healthpointsvisible")));
                card.setPortraitFile(findElement(el, "portrait"));
                card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
                card.setQuoteString1(findElement(el, "quotestring1"));
                card.setNameColor(new Color(new Integer(findElement(el, "namecolour"))));
                card.setClassColor(new Color(new Integer(findElement(el, "classcolour"))));
                card.setQuoteColor(new Color(new Integer(findElement(el, "quotecolour"))));
                card.setCardText(findElement(el, "cardtext"));

                card.setClassVisible(new Boolean(findElement(el, "classvisible")));

                if (findElement(el, "namefontcolor").isEmpty() == false) {
                    card.setNameFontColor(new Color(new Integer(findElement(el, "namefontcolor"))));
                }

                if (findElement(el, "hpfontcolor").isEmpty() == false) {
                    card.setHpFontColor(new Color(new Integer(findElement(el, "hpfontcolor"))));
                }

                if (findElement(el, "classfontcolor").isEmpty() == false) {
                    card.setClassFontColor(new Color(new Integer(findElement(el, "classfontcolor"))));
                }

                if (findElement(el, "descriptionfontcolor").isEmpty() == false) {
                    card.setDescriptionFontColor(new Color(new Integer(findElement(el, "descriptionfontcolor"))));
                }

                if (findElement(el, "quotefontcolor").isEmpty() == false) {
                    card.setQuoteFontColor(new Color(new Integer(findElement(el, "quotefontcolor"))));
                }

                if (findFontElement(el, "namefont") != null) {
                    card.setNameFont(findFontElement(el, "namefont"));
                }

                if (findFontElement(el, "classfont") != null) {
                    card.setClassFont(findFontElement(el, "classfont"));
                }

                if (findFontElement(el, "hpfont") != null) {
                    card.setHpFont(findFontElement(el, "hpfont"));
                }

                if (findFontElement(el, "descriptionfont") != null) {
                    card.setDescriptionFont(findFontElement(el, "descriptionfont"));
                }

                if (findFontElement(el, "quotefont") != null) {
                    card.setQuoteFont(findFontElement(el, "quotefont"));
                }

                cards.add(card);
            }

            if (deckType.equals("herodeck")) {
                deck = new HeroDeck(cards);
            } else if (deckType.equals("villaindeck")) {
                deck = new VillainDeck(cards);
            } else if (deckType.equals("environmentdeck")) {
                deck = new EnvironmentDeck(cards);
            }

            deck.setNextID(findHighestID(deck.getCards()));
            deck.setName(f.getName().replace(".xml", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deck;
    }
    
    private static String findElement(Element el, String attr) {
        String val = "";
        Elements subels = el.getElementsByTag(attr);
        for (Element sel : subels) {
            val = sel.text();
        }
        return val;
    }
    
    private static Integer findHighestID(List<Card> cards) {
        int i = 0;
        for (Card c : cards) {
            if (c.getCardID() > i) {
                i = c.getCardID();
            }
        }

        return i;
    }
    
    private static Font findFontElement(Element el, String attr) {
        String val = "";
        Elements subels = el.getElementsByTag(attr);
        for (Element sel : subels) {
            val = sel.text();
        }

        try {
            String[] vals = val.split(";");
            return new Font(vals[0], new Integer(vals[1]), new Integer(vals[2]));
        } catch (Exception e) {
            return null;
        }
    }
    
    public enum DeckType {
        Hero,
        Villain,
        Environment
    }
}
