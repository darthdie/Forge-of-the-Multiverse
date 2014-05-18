package uk.co.fostorial.sotm.structure;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Objects;

public class VillainFrontCard extends Card {
    private String description1;
    private String nemesisPath;
    private Color color;
    private Color classColor;
    private String description2;

    private Font nameFont;
    private Color nameFontColor;
    private Font hpFont;
    private Color hpFontColor;
    private Font classFont;
    private Color classFontColor;
    private Font descriptionFont;
    private Color descriptionFontColor;

    public VillainFrontCard(String name, Integer id) {
        super(Card.VILLAIN_FRONT, id);
        setName(name);
        setClasses("Villain");
        setHealthPoints("40");
        setPortraitFile("images" + File.separator + "villainfront" + File.separator + "portrait.png");
        description1 = "Description 1";
        description2 = "Description 2";
        color = new Color(217, 146, 131);
        classColor = new Color(163, 178, 207);

        nameFontColor = Color.white;
        nameFont = new Font("SF Ferretopia", Font.PLAIN, 110);
        hpFontColor = Color.white;
        hpFont = new Font("SF Ferretopia", Font.PLAIN, 110);
        classFontColor = Color.black;
        classFont = new Font("Comic Book", Font.PLAIN, 30);
        descriptionFontColor = Color.black;
        descriptionFont = new Font("Comic Book", Font.PLAIN, 30);
        
        setIsDirty(false);
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

    public Font getClassFont() {
        return classFont;
    }

    public void setClassFont(Font classFont) {
        if(!Objects.equals(this.classFont, classFont)) {
            this.classFont = classFont;
            setIsDirty(true);
        }
    }

    public Color getClassFontColor() {
        return classFontColor;
    }

    public void setClassFontColor(Color classFontColor) {
        if(!Objects.equals(this.classFontColor, classFontColor)) {
            this.classFontColor = classFontColor;
            setIsDirty(true);
        }
    }

    public Font getDescriptionFont() {
        return descriptionFont;
    }

    public void setDescriptionFont(Font descriptionFont) {
        if(!Objects.equals(this.descriptionFont, descriptionFont)) {
            this.descriptionFont = descriptionFont;
            setIsDirty(true);
        }
    }

    public Color getDescriptionFontColor() {
        return descriptionFontColor;
    }

    public void setDescriptionFontColor(Color descriptionFontColor) {
        if(!Objects.equals(this.descriptionFontColor, descriptionFontColor)) {
            this.descriptionFontColor = descriptionFontColor;
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

    @Override
    public String getXML() {
        String xml = "";
        xml += " <villainfrontcard>\n";
        xml += "  <id>" + getCardID() + "</id>\n";
        xml += "  <name>" + getName() + "</name>\n";
        xml += " <namefontcolor>" + getNameFontColor().getRGB() + "</namefontcolor>\n";
        xml += " <namefont>" + getNameFont().getFontName() + ";" + getNameFont().getStyle() + ";" + getNameFont().getSize() + "</namefont>\n";
        xml += "  <classes>" + getClasses() + "</classes>\n";
        xml += "  <healthpoints>" + getHealthPoints() + "</healthpoints>\n";
        xml += "  <portrait>" + getPortraitFile() + "</portrait>\n";
        xml += "  <description1>" + getDescription1() + "</description1>\n";
        xml += "  <description2>" + getDescription2() + "</description2>\n";
        xml += "  <nemesispath>" + getNemesisPath() + "</nemesispath>\n";
        xml += "  <numberindeck>" + getNumberInDeck() + "</numberindeck>\n";
        xml += "  <classcolor>" + classColor.getRGB() + "</classcolor>\n";
        xml += "  <descriptioncolor>" + color.getRGB() + "</descriptioncolor>\n";

        xml += " <hpfontcolor>" + getHpFontColor().getRGB() + "</hpfontcolor>\n";
        xml += " <hpfont>" + getHpFont().getFontName() + ";" + getHpFont().getStyle() + ";" + getHpFont().getSize() + "</hpfont>\n";
        xml += " <classfontcolor>" + getClassFontColor().getRGB() + "</classfontcolor>\n";
        xml += " <classfont>" + getClassFont().getFontName() + ";" + getClassFont().getStyle() + ";" + getClassFont().getSize() + "</classfont>\n";
        xml += " <descriptionfontcolor>" + getDescriptionFontColor().getRGB() + "</descriptionfontcolor>\n";
        xml += " <descriptionfont>" + getDescriptionFont().getFontName() + ";" + getDescriptionFont().getStyle() + ";" + getDescriptionFont().getSize() + "</descriptionfont>\n";

        xml += " </villainfrontcard>\n";
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

    public Color getClassColor() {
        return classColor;
    }

    public void setClassColor(Color classColor) {
        if(!Objects.equals(this.classColor, classColor)) {
            this.classColor = classColor;
            setIsDirty(true);
        }
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        if(!Objects.equals(this.description1, description1)) {
            this.description1 = description1;
            setIsDirty(true);
        }
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        if(!Objects.equals(this.description2, description2)) {
            this.description2 = description2;
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
}
