package com.example.triovisioniseprtp.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Color {
    YELLOW ("yellow"),
    RED ("red"),
    BLUE("blue"),
    GREEN("green"),
    WHITE("white");

    private final String css;

    private Color(String abbreviation) {
        this.css = abbreviation;
    }

    public String toCSS() {
        return css;
    }

    public static Color getRandomColor() {
        Color[] colors = Color.values();
        List<Color> colorsList = new ArrayList<>(Arrays.asList(colors));
        colorsList.remove(Color.WHITE);

        Random random = new Random();
        int randomIndex = random.nextInt(colorsList.size());
        return colorsList.get(randomIndex);
    }

}
