/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm.structure;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 *
 * @author MCP
 */
public class DeckDocument {
    public static DeckDocument create(String path) {
        try {
            DeckDocument doc = new DeckDocument();
            doc.path = path;
            doc.file = new File(doc.path);
            doc.document = Jsoup.parse(doc.file, null);

            return doc;
        }
        catch(IOException ex) {
            return null;
        }
    }
    
    private String path;    
    private File file;
    private Document document;

    public Deck parse() {
        List<Card> cards = new ArrayList<>();

        cards.addAll(parseBackCards());

        String deckType = getDeckType();
        String name = file.getName().replace(".xml", "");
        switch (deckType) {
            case "herodeck":
                cards.addAll(parseHeroFrontCards());
                cards.addAll(parseHeroBackCards());
                cards.addAll(parseHeroCards());
                
                return new HeroDeck(cards, name, file.getAbsolutePath());
            case "villaindeck":
                cards.addAll(parseVillainFrontCards());
                cards.addAll(parseVillainCards());
                
                return new VillainDeck(cards, name, file.getAbsolutePath());
            case "environmentdeck":
                cards.addAll(parseEnvironmentCards());
                
                return new EnvironmentDeck(cards, name, file.getAbsolutePath());
        }

        return null;
    }

    private String getDeckType() {
        Elements els = document.getElementsByTag("type");
        return els.isEmpty() ? "deck" : els.first().text();
    }
    
    private List<Card> parseHeroFrontCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("herofrontcard");
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

            card.setIsDirty(false);
            
            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseHeroBackCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("herobackcard");
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
                card.setTextFontColor(new Color(new Integer(findElement(el, "textfontcolor"))));
            }

            if (findFontElement(el, "textfont") != null) {
                card.setTextFont(findFontElement(el, "textfont"));
            }
            
            card.setIsDirty(false);

            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseVillainFrontCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("villainfrontcard");
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

            card.setIsDirty(false);
            
            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseBackCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("backcard");
        for (Element el : els) {
            Integer id = new Integer(findElement(el, "id"));
            String name = findElement(el, "name");
            BackCard card = new BackCard(name, id);
            card.setClasses(findElement(el, "classes"));
            card.setHealthPoints(findElement(el, "healthpoints"));
            card.setPortraitFile(findElement(el, "portrait"));
            card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
            card.setIsDirty(false);
            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseHeroCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("herocard");
        for (Element el : els) {
            Integer id = new Integer(findElement(el, "id"));
            String name = findElement(el, "name");
            HeroCard card = new HeroCard(name, id);
            card.setClasses(findElement(el, "classes"));
            card.setHealthPoints(findElement(el, "healthpoints"));
            card.setHealthPointsImage(findElement(el, "healthpointsimage"));
            card.setHealthPointsVisible(Boolean.valueOf(findElement(el, "healthpointsvisible")));
            card.setPortraitFile(findElement(el, "portrait"));
            card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
            card.setQuoteString1(findElement(el, "quotestring1"));
            card.setQuoteString2(findElement(el, "quotestring2"));
            card.setIssueString(findElement(el, "issuestring"));
            card.setNameColor(new Color(new Integer(findElement(el, "namecolour"))));
            card.setClassColor(new Color(new Integer(findElement(el, "classcolour"))));
            String s = findElement(el, "cardtext");
            card.setCardText(s);

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
            
            card.setIsDirty(false);

            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseVillainCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("villaincard");
        for (Element el : els) {
            Integer id = new Integer(findElement(el, "id"));
            String name = findElement(el, "name");
            VillainCard card = new VillainCard(name, id);
            card.setClasses(findElement(el, "classes"));
            card.setHealthPoints(findElement(el, "healthpoints"));
            card.setHealthPointsImage(findElement(el, "healthpointsimage"));
            card.setHealthPointsVisible(Boolean.valueOf(findElement(el, "healthpointsvisible")));
            card.setPortraitFile(findElement(el, "portrait"));
            card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
            card.setQuoteString1(findElement(el, "quotestring1"));
            card.setQuoteString2(findElement(el, "quotestring2"));
            card.setIssueString(findElement(el, "issuestring"));
            card.setNameColor(new Color(Integer.parseInt(findElement(el, "namecolour"))));
            card.setClassColor(new Color(new Integer(findElement(el, "classcolour"))));
            card.setCardText(findElement(el, "cardtext"));

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
            
            card.setIsDirty(false);

            cards.add(card);
        }
        
        return cards;
    }
    
    private List<Card> parseEnvironmentCards() {
        List<Card> cards = new ArrayList<>();
        
        Elements els = document.getElementsByTag("environmentcard");
        for (Element el : els) {
            Integer id = new Integer(findElement(el, "id"));
            String name = findElement(el, "name");
            EnvironmentCard card = new EnvironmentCard(name, id);
            card.setClasses(findElement(el, "classes"));
            card.setHealthPoints(findElement(el, "healthpoints"));
            card.setHealthPointsImage(findElement(el, "healthpointsimage"));
            card.setHealthPointsVisible(Boolean.valueOf(findElement(el, "healthpointsvisible")));
            card.setPortraitFile(findElement(el, "portrait"));
            card.setNumberInDeck(new Integer(findElement(el, "numberindeck")));
            card.setQuoteString1(findElement(el, "quotestring1"));
            card.setNameColor(new Color(new Integer(findElement(el, "namecolour"))));
            card.setClassColor(new Color(new Integer(findElement(el, "classcolour"))));
            card.setQuoteColor(new Color(new Integer(findElement(el, "quotecolour"))));
            card.setCardText(findElement(el, "cardtext"));

            card.setClassVisible(Boolean.valueOf(findElement(el, "classvisible")));

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

            card.setIsDirty(false);
            
            cards.add(card);
        }
        
        return cards;
    }
    
    private String findElement(Element el, String attr) {
        String val = "";
        Elements subels = el.getElementsByTag(attr);
        for (Element sel : subels) {
            List<TextNode> nodes = sel.textNodes();
            if(nodes == null || nodes.isEmpty()) {
                val = sel.text();
            } 
            else {
                for(TextNode node : nodes) {
                    val += node.getWholeText();
                }
            }
        }
        
        return val;
    }
    
    private Font findFontElement(Element el, String attr) {
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
}
