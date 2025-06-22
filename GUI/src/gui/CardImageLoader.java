package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that loads and provides access to card images.
 * It preloads all card images into a HashMap for quick retrieval.
 * The images are resized to a standard size for consistent display.
 * @return A static method to retrieve card images by their names (e.g., "ace_of_spades").
 */
public class CardImageLoader {

    private static final String[] SUITS = {"clubs", "diamonds", "hearts", "spades"};
    private static final String[] RANKS = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "jack", "queen", "king", "ace"
    };

    // HashMap to store card images with their names as keys all in one place
    private static final Map<String, ImageIcon> cardImages = new HashMap<>();
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;

    // Preload all images
    static {
    	try {
	        for (String suit : SUITS) {
	            for (String rank : RANKS) {
	                String key = rank + "_of_" + suit;
	                String path = "/gui/cards/" + key + ".png";
	
	                cardImages.put(key, loadResizedIcon(path));
	            }
	        }
	        // Load back of card image
	        cardImages.put("card_back", loadResizedIcon("/gui/cards/card_back.png"));
    	} catch (Exception e) {
    		 e.printStackTrace();
    	}
    }
    
    private static ImageIcon loadResizedIcon(String path) {
    	java.net.URL imgURL = CardImageLoader.class.getResource(path);
        if (imgURL == null) {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
        
        // Load the image
        ImageIcon icon = new ImageIcon(imgURL);
        // Resize the image
        Image scaled = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // Retrieve the card image anywhere using this method
    public static ImageIcon getCard(String name) {
        return cardImages.get(name); // e.g., "ace_of_spades"
    }
}
