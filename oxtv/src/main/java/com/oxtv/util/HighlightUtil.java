package com.oxtv.util;

public class HighlightUtil {

    public static String highlight(String text, String keyword) {
        if (keyword == null || keyword.trim().isEmpty() || text == null) return text;
        String escapedKeyword = keyword.replaceAll("([\\\\\\[\\](){}.+*?^$|])", "\\\\$1");
        return text.replaceAll("(?i)(" + escapedKeyword + ")", "<mark>$1</mark>");
    }
}
