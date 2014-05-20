/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.fostorial.sotm.deck;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import uk.co.fostorial.sotm.CreatorFrame;
import uk.co.fostorial.sotm.design.CreatorTab;
import uk.co.fostorial.sotm.design.CreatorTabCardBack;
import uk.co.fostorial.sotm.design.CreatorTabEnvironmentCard;
import uk.co.fostorial.sotm.design.CreatorTabHeroBack;
import uk.co.fostorial.sotm.design.CreatorTabHeroCard;
import uk.co.fostorial.sotm.design.CreatorTabHeroFront;
import uk.co.fostorial.sotm.design.CreatorTabVillainCard;
import uk.co.fostorial.sotm.design.CreatorTabVillainFront;
import uk.co.fostorial.sotm.structure.BackCard;
import uk.co.fostorial.sotm.structure.Card;
import uk.co.fostorial.sotm.structure.Deck;
import uk.co.fostorial.sotm.structure.EnvironmentCard;
import uk.co.fostorial.sotm.structure.HeroBackCard;
import uk.co.fostorial.sotm.structure.HeroCard;
import uk.co.fostorial.sotm.structure.HeroFrontCard;
import uk.co.fostorial.sotm.structure.VillainCard;
import uk.co.fostorial.sotm.structure.VillainFrontCard;

/**
 *
 * @author MCP
 */
public class DeckPrinter {
    private final CreatorFrame frame;
    private final Deck deck;

    public DeckPrinter(CreatorFrame frame, Deck deck) {
        this.frame = frame;
        this.deck = deck;
    }

    public void exportDeckPagesToImage(DeckImageExportOptions options) {
        switch (deck.getType()) {
            case Hero:
                exportHeroPages(options.Extension, options.ExportDirectory);
                break;
            case Villain:
                exportVillainPages(options.Extension, options.ExportDirectory);
                break;
            case Environment:
                exportEnvironmentPages(options.Extension, options.ExportDirectory);
                break;
        }
    }
    
    private void exportEnvironmentPages(String extension, String folder) {
        BackCard cardBack = null;
        List<Card> cards = new ArrayList<>();

        for (Card c : deck.getCards()) {
            if (c != null) {
                if (c instanceof BackCard) {
                    cardBack = (BackCard) c;
                } else if (c instanceof EnvironmentCard) {
                    int numberInDeck = c.getNumberInDeck();
                    for (int i = 0; i < numberInDeck; i++) {
                        cards.add(c);
                    }
                }
            }
        }

        CreatorTab creatorTab = new CreatorTabCardBack(frame, (BackCard) deck.getCards().get(0));
        BufferedImage image = creatorTab.getImage();

        int imageType = BufferedImage.TYPE_INT_RGB;
        BufferedImage outputImage = new BufferedImage((image.getWidth() * 3) + (4 * 20), (image.getHeight() * 3) + (4 * 20), imageType);
        Graphics2D g = outputImage.createGraphics();
        g.setBackground(Color.white);
        g.setColor(Color.white);
        g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

        int cardWidth = image.getWidth();
        int cardHeight = image.getHeight();

        creatorTab.dispose();
        System.gc();

        int x = 20;
        int y = 20;
        BufferedImage cardImage = null;
        int currentCard = 0;
        int currentPage = 0;

        for (Card c : cards) {
            if (c != null) {
                if (c instanceof BackCard) {
                    creatorTab = new CreatorTabCardBack(frame, (BackCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof EnvironmentCard) {
                    creatorTab = new CreatorTabEnvironmentCard(frame, (EnvironmentCard) c);
                    cardImage = creatorTab.getImage();
                }
            }

            g.drawImage(cardImage, x, y, null);
            cardImage = null;
            x += cardWidth + 20;
            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }

        /* Write card backs */
        int filledPages = currentPage;

        x = 20 + cardWidth + 20 + cardWidth + 20;
        y = 20;

        currentCard = 0;
        currentPage = 0;

        creatorTab = new CreatorTabCardBack(frame, cardBack);
        cardImage = creatorTab.getImage();
        creatorTab.dispose();

        for (Card card : cards) {
            if (currentCard == 0 && currentPage == 0) {
                x = 20;
            }

            if (currentCard == 1 && currentPage == 0) {
                x = 20 + cardWidth + 20;
            }

            if (currentCard == 2 && currentPage == 0) {
                x = 20 + cardWidth + 20 + cardWidth + 20;
            }

            g.drawImage(cardImage, x, y, null);
            x += cardWidth + 20;

            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1 + filledPages) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }
    }

    private void exportVillainPages(String extension, String folder) {
        /* Variable used to split pages */
        int currentCard = 0;
        int currentPage = 0;

        /* sort into correct types */
        HeroFrontCard heroFront = null;
        HeroBackCard heroBack = null;
        List<VillainFrontCard> villainFronts = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        BackCard cardBack = null;
        VillainFrontCard villainFront = null;
        VillainFrontCard villainBack = null;

        for (Card c : deck.getCards()) {
            if (c != null) {
                if (c instanceof BackCard) {
                    cardBack = (BackCard) c;
                } else if (c instanceof VillainCard) {
                    int numberInDeck = c.getNumberInDeck();
                    for (int i = 0; i < numberInDeck; i++) {
                        cards.add(c);
                    }
                } else if (c instanceof VillainFrontCard) {
                    villainFronts.add((VillainFrontCard) c);
                }
            }
        }

        if (villainFronts.size() == 2) {
            villainFront = villainFronts.get(0);
            villainBack = villainFronts.get(1);
        }

        CreatorTab creatorTab = new CreatorTabVillainFront(frame, villainFront);
        BufferedImage image = creatorTab.getImage();

        int imageType = BufferedImage.TYPE_INT_RGB;
        BufferedImage outputImage = new BufferedImage((image.getWidth() * 3) + (4 * 20), (image.getHeight() * 3) + (4 * 20), imageType);
        Graphics2D g = outputImage.createGraphics();
        g.setBackground(Color.white);
        g.setColor(Color.white);
        g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

        g.drawImage(image, 20, 20, null);

        int cardWidth = image.getWidth();
        int cardHeight = image.getHeight();

        creatorTab.dispose();
        System.gc();

        int x = 20 + cardWidth + 20;
        int y = 20;

        BufferedImage cardImage = null;
        for (Card c : cards) {
            if (c != null) {
                if (c instanceof BackCard) {
                    creatorTab = new CreatorTabCardBack(frame, (BackCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof VillainFrontCard) {
                    creatorTab = new CreatorTabVillainFront(frame, (VillainFrontCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof VillainCard) {
                    creatorTab = new CreatorTabVillainCard(frame, (VillainCard) c);
                    cardImage = creatorTab.getImage();
                }
            }

            g.drawImage(cardImage, x, y, null);
            cardImage = null;
            x += cardWidth + 20;
            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }

        /* Write card backs */
        int filledPages = currentPage;

        x = 20 + cardWidth + 20 + cardWidth + 20;
        y = 20;

        if (villainBack != null) {
            creatorTab = new CreatorTabVillainFront(frame, villainBack);
            image = creatorTab.getImage();

            g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

            g.drawImage(image, x, y, null);
        }

        currentCard = 0;
        currentPage = 0;

        creatorTab = new CreatorTabCardBack(frame, cardBack);
        cardImage = creatorTab.getImage();
        creatorTab.dispose();

        for (Card card : cards) {
            if (currentCard == 0 && currentPage == 0) {
                x = 20 + cardWidth + 20;
            }

            if (currentCard == 1 && currentPage == 0) {
                x = 20;
            }

            if (currentCard == 2 && currentPage == 0) {
                x = 20;
                y += cardHeight + 20;
            }

            g.drawImage(cardImage, x, y, null);
            x += cardWidth + 20;

            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1 + filledPages) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }
    }

    private void exportHeroPages(String extension, String folder) {
        int currentCard = 0;
        int currentPage = 0;

        HeroFrontCard heroFront = null;
        HeroBackCard heroBack = null;
        List<Card> cards = new ArrayList<>();
        BackCard cardBack = null;

        for (Card c : deck.getCards()) {
            if (c != null) {
                if (c instanceof HeroFrontCard) {
                    heroFront = (HeroFrontCard) c;
                } else if (c instanceof HeroBackCard) {
                    heroBack = (HeroBackCard) c;
                } else if (c instanceof BackCard) {
                    cardBack = (BackCard) c;
                } else if (c instanceof HeroCard) {
                    int numberInDeck = c.getNumberInDeck();
                    for (int i = 0; i < numberInDeck; i++) {
                        cards.add(c);
                    }
                }
            }
        }

        BufferedImage outputImage = null;
        Graphics2D g = null;
        int cardWidth = 0;
        int cardHeight = 0;
        BufferedImage cardImage = null;

        if (heroFront != null) {
            CreatorTab creatorTab = new CreatorTabHeroFront(frame, heroFront);
            BufferedImage image = creatorTab.getImage();

            int imageType = BufferedImage.TYPE_INT_RGB;
            outputImage = new BufferedImage((image.getWidth() * 3) + (4 * 20), (image.getHeight() * 3) + (4 * 20), imageType);
            g = outputImage.createGraphics();
            g.setBackground(Color.white);
            g.setColor(Color.white);
            g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

            g.drawImage(image, 20, 20, null);

            cardWidth = image.getWidth();
            cardHeight = image.getHeight();

            creatorTab.dispose();
            System.gc();
        }

        int x = 20 + cardWidth + 20;
        int y = 20;

        for (Card c : cards) {
            if (c != null) {
                if (c instanceof HeroFrontCard) {
                    CreatorTab creatorTab = new CreatorTabHeroFront(frame, (HeroFrontCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof HeroBackCard) {
                    CreatorTab creatorTab = new CreatorTabHeroBack(frame, (HeroBackCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof BackCard) {
                    CreatorTab creatorTab = new CreatorTabCardBack(frame, (BackCard) c);
                    cardImage = creatorTab.getImage();
                }

                if (c instanceof HeroCard) {
                    CreatorTab creatorTab = new CreatorTabHeroCard(frame, (HeroCard) c);
                    cardImage = creatorTab.getImage();
                }
            }

            g.drawImage(cardImage, x, y, null);
            cardImage = null;
            x += cardWidth + 20;
            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }

        /* Write card backs */
        int filledPages = currentPage;

        x = 20 + cardWidth + 20 + cardWidth + 20;
        y = 20;

        if (heroBack != null) {
            CreatorTab creatorTab = new CreatorTabHeroBack(frame, heroBack);
            BufferedImage image = creatorTab.getImage();

            g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());

            g.drawImage(image, x, y, null);
        }

        currentCard = 0;
        currentPage = 0;

        CreatorTab creatorTab = new CreatorTabCardBack(frame, cardBack);
        cardImage = creatorTab.getImage();
        creatorTab.dispose();
        creatorTab = null;

        for (Card card : cards) {
            if (currentCard == 0 && currentPage == 0) {
                x = 20 + cardWidth + 20;
            }

            if (currentCard == 1 && currentPage == 0) {
                x = 20;
            }

            if (currentCard == 2 && currentPage == 0) {
                x = 20;
                y += cardHeight + 20;
            }

            g.drawImage(cardImage, x, y, null);
            x += cardWidth + 20;

            System.gc();

            if (x >= ((3 * cardWidth) + (4 * 20))) {
                x = 20;
                y += cardHeight + 20;
            }

            currentCard++;

            /* Save and reset page */
            if (currentCard >= 9 || (currentCard + (currentPage * 9)) >= cards.size()) {
                try {
                    File f = new File(folder + File.separator + deck.getName() + (int) (currentPage + 1 + filledPages) + "." + extension);
                    ImageIO.write(outputImage, extension, f);

                    x = 20;
                    y = 20;

                    currentPage++;
                    currentCard = 0;

                    g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                } catch (IOException e) {
                }
            }

            System.gc();
        }
    }

    public boolean exportToText(String filePath) {
        try {
            if(!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            
            FileWriter fstream = new FileWriter(new File(filePath));
            try (BufferedWriter out = new BufferedWriter(fstream)) {
                switch(deck.getType()) {
                    case Hero:
                        exportHeroToText(out);
                        break;
                    case Villain:
                        exportVillainToText(out);
                        break;
                    case Environment:
                        exportEnvironmentToText(out);
                        break;
                }
            }
            
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void exportHeroToText(BufferedWriter out) throws IOException {
        HeroFrontCard front = null;
        HeroBackCard back = null;
        List<HeroCard> cards = new ArrayList<>();

        for (Card c : deck.getCards()) {
            if (c instanceof HeroFrontCard) {
                front = (HeroFrontCard) c;
            } else if (c instanceof HeroBackCard) {
                back = (HeroBackCard) c;
            } else if (c instanceof HeroCard) {
                cards.add((HeroCard) c);
            }
        }

        out.write("Name: " + front.getName() + "\n");
        out.write("Health Points: " + front.getHealthPoints() + "\n");
        out.write("Power: " + front.getPowerName() + " - " + front.getPowerText() + "\n");
        out.write("Incapacitated Power 1: " + back.getAbilityLine1() + " " + back.getAbilityLine2() + "\n");
        out.write("Incapacitated Power 2: " + back.getAbilityLine3() + " " + back.getAbilityLine4() + "\n");
        out.write("Incapacitated Power 3: " + back.getAbilityLine5() + " " + back.getAbilityLine6() + "\n\n");

        out.write("Cards" + "\n\n");
        for (HeroCard c : cards) {
            out.write("Name: " + c.getName() + "\n");
            out.write("Health Points: " + c.getHealthPoints() + "\n");
            out.write("Classes: " + c.getClasses() + "\n");
            out.write("Card Text: " + c.getCardText() + "\n");
            out.write("Quote: " + c.getQuoteString1() + " " + c.getQuoteString2() + " " + c.getIssueString() + "\n");
            out.write("Number in Deck: " + c.getNumberInDeck() + "\n\n");
        }
    }

    private void exportVillainToText(BufferedWriter out) throws IOException {
        VillainFrontCard front = null;
        VillainFrontCard back = null;
        List<VillainCard> cards = new ArrayList<>();

        for (Card c : deck.getCards()) {
            if (c instanceof VillainFrontCard && front == null) {
                front = (VillainFrontCard) c;
            } else if (c instanceof VillainFrontCard) {
                back = (VillainFrontCard) c;
            } else if (c instanceof VillainCard) {
                cards.add((VillainCard) c);
            }
        }

        out.write("Name: " + front.getName() + "\n");
        out.write("Side 1: Health Points: " + front.getHealthPoints() + "\n");
        out.write("Side 1: Description: " + front.getDescription1() + " " + front.getDescription2() + "\n");
        out.write("Side 2: Health Points: " + back.getHealthPoints() + "\n");
        out.write("Side 2: Description: " + back.getDescription1() + " " + back.getDescription2() + "\n");

        out.write("Cards" + "\n\n");
        for (VillainCard c : cards) {
            out.write("Name: " + c.getName() + "\n");
            out.write("Health Points: " + c.getHealthPoints() + "\n");
            out.write("Classes: " + c.getClasses() + "\n");
            out.write("Card Text: " + c.getCardText() + "\n");
            out.write("Quote: " + c.getQuoteString1() + " " + c.getQuoteString2() + " " + c.getIssueString() + "\n");
            out.write("Number in Deck: " + c.getNumberInDeck() + "\n\n");
        }
    }

    private void exportEnvironmentToText(BufferedWriter out) throws IOException {
        List<EnvironmentCard> cards = new ArrayList<>();

        for (Card c : deck.getCards()) {
            if (c instanceof EnvironmentCard) {
                cards.add((EnvironmentCard) c);
            }
        }

        for (EnvironmentCard c : cards) {
            out.write("Name: " + c.getName() + "\n");
            out.write("Health Points: " + c.getHealthPoints() + "\n");
            out.write("Classes: " + c.getClasses() + "\n");
            out.write("Card Text: " + c.getCardText() + "\n");
            out.write("Quote: " + c.getQuoteString1() + "\n");
            out.write("Number in Deck: " + c.getNumberInDeck() + "\n\n");
        }
    }
}
