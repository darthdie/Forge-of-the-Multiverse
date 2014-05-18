package uk.co.fostorial.sotm.structure;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Objects;

public class HeroFrontCard extends Card {
    private String powerName;
    private String nemesisPath;
    private String powerText;
    private Color color;

    private Font nameFont;
    private Color nameFontColor;
    private Font hpFont;
    private Color hpFontColor;
    private Font powerFont;
    private Color powerFontColor;
    private Font powerNameFont;
    private Color powerNameFontColor;

    public HeroFrontCard(String name, Integer id) {
        super(Card.HERO_FRONT, id);
        setName(name);
        setClasses("N/A");
        setHealthPoints("40");
        setPortraitFile("images" + File.separator + "herofront" + File.separator + "portrait.png");
        powerName = "Power Name";
        powerText = "Power Text";
        color = new Color(217, 146, 131);

        nameFontColor = Color.white;
        nameFont = new Font("SF Ferretopia", Font.PLAIN, 110);
        hpFontColor = Color.white;
        hpFont = new Font("SF Ferretopia", Font.PLAIN, 90);
        powerFontColor = Color.black;
        powerFont = new Font("Comic Book", Font.PLAIN, 23);
        powerNameFontColor = Color.black;
        powerNameFont = new Font("Comic Book", Font.PLAIN, 30);

        setIsDirty(false);
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        if(!Objects.equals(this.powerName, powerName)) {
            this.powerName = powerName;
            setIsDirty(true);
        }
    }

    public String getNemesisPath() {
        return nemesisPath;
    }

    public void setNemesisPath(String nemesisPath) {
        if(!Objects.equals(this.nemesisPath, nemesisPath)) {
            this.nemesisPath = nemesisPath;
            setIsDirty(true);
        }
    }

    public String getPowerText() {
        return powerText;
    }

    public void setPowerText(String powerText) {
        if(!Objects.equals(this.powerText, powerText)) {
            this.powerText = powerText;
            setIsDirty(true);
        }
    }

    public String getXML() {
        String xml = "";
        xml += " <herofrontcard>\n";
        xml += "  <id>" + getCardID().intValue() + "</id>\n";
        xml += "  <name>" + getName() + "</name>\n";
        xml += "  <classes>" + getClasses() + "</classes>\n";
        xml += "  <healthpoints>" + getHealthPoints() + "</healthpoints>\n";
        xml += "  <portrait>" + getPortraitFile() + "</portrait>\n";
        xml += "  <powername>" + getPowerName() + "</powername>\n";
        xml += "  <powertext>" + getPowerText() + "</powertext>\n";
        xml += "  <nemesispath>" + getNemesisPath() + "</nemesispath>\n";
        xml += "  <numberindeck>" + getNumberInDeck() + "</numberindeck>\n";
        xml += "  <powercolor>" + color.getRGB() + "</powercolor>\n";

        xml += " <namefontcolor>" + getNameFontColor().getRGB() + "</namefontcolor>\n";
        xml += " <namefont>" + getNameFont().getFontName() + ";" + getNameFont().getStyle() + ";" + getNameFont().getSize() + "</namefont>\n";
        xml += " <hpfontcolor>" + getHpFontColor().getRGB() + "</hpfontcolor>\n";
        xml += " <hpfont>" + getHpFont().getFontName() + ";" + getHpFont().getStyle() + ";" + getHpFont().getSize() + "</hpfont>\n";
        xml += " <powerfontcolor>" + getPowerFontColor().getRGB() + "</powerfontcolor>\n";
        xml += " <powerfont>" + getPowerFont().getFontName() + ";" + getPowerFont().getStyle() + ";" + getPowerFont().getSize() + "</powerfont>\n";
        xml += " <powernamefontcolor>" + getPowerNameFontColor().getRGB() + "</powernamefontcolor>\n";
        xml += " <powernamefont>" + getPowerNameFont().getFontName() + ";" + getPowerNameFont().getStyle() + ";" + getPowerNameFont().getSize() + "</powernamefont>\n";

        xml += " </herofrontcard>\n";
        return xml;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if(!Objects.equals(this.color, color)) {
            this.color = color;
            setIsDirty(true);
        }
    }

    public Font getNameFont() {
        return nameFont;
    }

    public void setNameFont(Font nameFont) {
        if(!Objects.equals(this.nameFont, nameFont)) {
            this.nameFont = nameFont;
            setIsDirty(true);
        }
    }

    public Color getNameFontColor() {
        return nameFontColor;
    }

    public void setNameFontColor(Color nameFontColor) {
        if(!Objects.equals(this.nameFontColor, nameFontColor)) {
            this.nameFontColor = nameFontColor;
            setIsDirty(true);
        }
    }

    public Font getHpFont() {
        return hpFont;
    }

    public void setHpFont(Font hpFont) {
        if(!Objects.equals(this.hpFont, hpFont)) {
            this.hpFont = hpFont;
            setIsDirty(true);
        }
    }

    public Color getHpFontColor() {
        return hpFontColor;
    }

    public void setHpFontColor(Color hpFontColor) {
        if(!Objects.equals(this.hpFontColor, hpFontColor)) {
            this.hpFontColor = hpFontColor;
            setIsDirty(true);
        }
    }

    public Font getPowerFont() {
        return powerFont;
    }

    public void setPowerFont(Font powerFont) {
        if(!Objects.equals(this.powerFont, powerFont)) {
            this.powerFont = powerFont;
            setIsDirty(true);
        }
    }

    public Color getPowerFontColor() {
        return powerFontColor;
    }

    public void setPowerFontColor(Color powerFontColor) {
        if(!Objects.equals(this.powerFontColor, powerFontColor)) {
            this.powerFontColor = powerFontColor;
            setIsDirty(true);
        }
    }

    public Font getPowerNameFont() {
        return powerNameFont;
    }

    public void setPowerNameFont(Font powerNameFont) {
        if(!Objects.equals(this.powerNameFont, powerNameFont)) {
            this.powerNameFont = powerNameFont;
            setIsDirty(true);
        }
    }

    public Color getPowerNameFontColor() {
        return powerNameFontColor;
    }

    public void setPowerNameFontColor(Color powerNameFontColor) {
        if(!Objects.equals(this.powerNameFontColor, powerNameFontColor)) {
            this.powerNameFontColor = powerNameFontColor;
            setIsDirty(true);
        }
    }
}
