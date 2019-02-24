package com.pluralsight.bookstore.util;

public class TextUtil {
	//business methods
    public String sanitize(String textToSanitize) {
    		//replaces double/triple spaces (using regex) with a single one
        return textToSanitize.replaceAll("\\s+", " ");
    }
}
