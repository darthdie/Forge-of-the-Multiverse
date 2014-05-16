package uk.co.fostorial.sotm.structure;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class HeroCard extends Card {

    private String quoteString1;
    private String quoteString2;
    private String issueString;

    private boolean healthPointsVisible;
    private String healthPointsImage;
    
    private boolean bonusIconVisible;
    private String bonusIconImage;

    private String cardText;

    private Color nameColor;
    private Color classColor;

    private Font nameFont;
    private Color nameFontColor;
    private Font hpFont;
    private Color hpFontColor;
    private Font classFont;
    private Color classFontColor;
    private Font descriptionFont;
    private Color descriptionFontColor;
    private Font quoteFont;
    private Color quoteFontColor;

    public HeroCard(String name, Integer id) {
        super(Card.HERO_CARD, id);
        setName(name);
        setNumberInDeck(1);
        setClasses("None");
        setHealthPoints("N/A");
        setPortraitFile("images" + File.separator + "herocard" + File.separator + "portrait.png");

        this.setHealthPointsVisible(false);
        this.setHealthPointsImage("images" + File.separator + "herocard" + File.separator + "hpimage.png");

        this.setBonusIconVisible(false);
        this.setBonusIconImage("images" + File.separator + "herocard" + File.separator + "bonusIcon.png");
        
        nameColor = new Color(241, 157, 15);
        classColor = new Color(241, 157, 15);

        cardText = "Card Text";

        quoteString1 = "\"Once in while you just";
        quoteString2 = "gotta improvise!\"";
        issueString = "- Citizen Snips, The Crab Cometh #4";

        nameFontColor = Color.white;
        nameFont = new Font("SF Ferretopia", Font.PLAIN, 50);
        hpFontColor = Color.black;
        hpFont = new Font("SF Ferretopia", Font.PLAIN, 70);
        classFontColor = Color.black;
        classFont = new Font("Comic Book", Font.PLAIN, 30);
        descriptionFontColor = Color.black;
        descriptionFont = new Font("Comic Book", Font.PLAIN, 24);
        quoteFontColor = Color.black;
        quoteFont = new Font("Comic Book", Font.PLAIN, 20);
    }

    public String getXML() {
        String xml = "";
        xml += " <herocard>\n";
        xml += "  <id>" + getCardID() + "</id>\n";
        xml += "  <name>" + getName() + "</name>\n";
        xml += "  <classes>" + getClasses() + "</classes>\n";
        xml += "  <healthpoints>" + getHealthPoints() + "</healthpoints>\n";
        xml += "  <healthpointsvisible>" + isHealthPointsVisible() + "</healthpointsvisible>\n";
        xml += "  <healthpointsimage>" + getHealthPointsImage() + "</healthpointsimage>\n";
        xml += "  <portrait>" + getPortraitFile() + "</portrait>\n";
        xml += "  <numberindeck>" + getNumberInDeck() + "</numberindeck>\n";
        xml += "  <cardtext>" + getCardText() + "</cardtext>\n";
        xml += "  <quotestring1>" + getQuoteString1() + "</quotestring1>\n";
        xml += "  <quotestring2>" + getQuoteString2() + "</quotestring2>\n";
        xml += "  <issuestring>" + getIssueString() + "</issuestring>\n";
        xml += "  <classcolour>" + getClassColor().getRGB() + "</classcolour>\n";
        xml += "  <namecolour>" + getNameColor().getRGB() + "</namecolour>\n";

        xml += "  <namefontcolor>" + getNameFontColor().getRGB() + "</namefontcolor>\n";
        xml += "  <namefont>" + getNameFont().getFontName() + ";" + getNameFont().getStyle() + ";" + getNameFont().getSize() + "</namefont>\n";
        xml += "  <hpfontcolor>" + getHpFontColor().getRGB() + "</hpfontcolor>\n";
        xml += "  <hpfont>" + getHpFont().getFontName() + ";" + getHpFont().getStyle() + ";" + getHpFont().getSize() + "</hpfont>\n";
        xml += "  <classfontcolor>" + getClassFontColor().getRGB() + "</classfontcolor>\n";
        xml += "  <classfont>" + getClassFont().getFontName() + ";" + getClassFont().getStyle() + ";" + getClassFont().getSize() + "</classfont>\n";
        xml += "  <descriptionfontcolor>" + getDescriptionFontColor().getRGB() + "</descriptionfontcolor>\n";
        xml += "  <descriptionfont>" + getDescriptionFont().getFontName() + ";" + getDescriptionFont().getStyle() + ";" + getDescriptionFont().getSize() + "</descriptionfont>\n";
        xml += "  <quotefontcolor>" + getQuoteFontColor().getRGB() + "</quotefontcolor>\n";
        xml += "  <quotefont>" + getQuoteFont().getFontName() + ";" + getQuoteFont().getStyle() + ";" + getQuoteFont().getSize() + "</quotefont>\n";

        xml += " </herocard>\n";
        return xml;
    }

    public String getQuoteString1() {
        return quoteString1;
    }

    public void setQuoteString1(String quoteString1) {
        this.quoteString1 = quoteString1;
    }

    public String getQuoteString2() {
        return quoteString2;
    }

    public void setQuoteString2(String quoteString2) {
        this.quoteString2 = quoteString2;
    }

    public String getIssueString() {
        return issueString;
    }

    public void setIssueString(String issueString) {
        this.issueString = issueString;
    }

    public boolean isHealthPointsVisible() {
        return healthPointsVisible;
    }

    public void setHealthPointsVisible(boolean healthPointsVisible) {
        this.healthPointsVisible = healthPointsVisible;
    }
    
    public boolean isBonusIconVisible() {
        return bonusIconVisible;
    }

    public void setBonusIconVisible(boolean bonusIconVisible) {
        this.bonusIconVisible = bonusIconVisible;
    }

    public String getHealthPointsImage() {
        return healthPointsImage;
    }

    public void setHealthPointsImage(String healthPointsImage) {
        this.healthPointsImage = healthPointsImage;
    }

    public String getBonusIconImage() {
        return bonusIconImage;
    }

    public void setBonusIconImage(String bonusIconImage) {
        this.bonusIconImage = bonusIconImage;
    }
    
    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }

    public Color getNameColor() {
        return nameColor;
    }

    public void setNameColor(Color nameColor) {
        this.nameColor = nameColor;
    }

    public Color getClassColor() {
        return classColor;
    }

    public void setClassColor(Color classColor) {
        this.classColor = classColor;
    }

    public Font getNameFont() {
        return nameFont;
    }

    public void setNameFont(Font nameFont) {
        this.nameFont = nameFont;
    }

    public Color getNameFontColor() {
        return nameFontColor;
    }

    public void setNameFontColor(Color nameFontColor) {
        this.nameFontColor = nameFontColor;
    }

    public Font getHpFont() {
        return hpFont;
    }

    public void setHpFont(Font hpFont) {
        this.hpFont = hpFont;
    }

    public Color getHpFontColor() {
        return hpFontColor;
    }

    public void setHpFontColor(Color hpFontColor) {
        this.hpFontColor = hpFontColor;
    }

    public Font getClassFont() {
        return classFont;
    }

    public void setClassFont(Font classFont) {
        this.classFont = classFont;
    }

    public Color getClassFontColor() {
        return classFontColor;
    }

    public void setClassFontColor(Color classFontColor) {
        this.classFontColor = classFontColor;
    }

    public Font getDescriptionFont() {
        return descriptionFont;
    }

    public void setDescriptionFont(Font descriptionFont) {
        this.descriptionFont = descriptionFont;
    }

    public Color getDescriptionFontColor() {
        return descriptionFontColor;
    }

    public void setDescriptionFontColor(Color descriptionFontColor) {
        this.descriptionFontColor = descriptionFontColor;
    }

    public Font getQuoteFont() {
        return quoteFont;
    }

    public void setQuoteFont(Font quoteFont) {
        this.quoteFont = quoteFont;
    }

    public Color getQuoteFontColor() {
        return quoteFontColor;
    }

    public void setQuoteFontColor(Color quoteFontColor) {
        this.quoteFontColor = quoteFontColor;
    }
}
