package org.hyperskill;

import java.util.Random;

public class SymbolLevelErrorEmulator {

    protected static final byte PRINTABLE_CHAR_LOWER_BOUND = 32;
    protected static final byte PRINTABLE_CHAR_UPPER_BOUND = 126;
    protected static final byte PRINTABLE_CHAR_RANGE = PRINTABLE_CHAR_UPPER_BOUND - PRINTABLE_CHAR_LOWER_BOUND;

    private final String text;

    public SymbolLevelErrorEmulator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SymbolLevelErrorEmulator{" +
                "text='" + text + '\'' +
                '}';
    }

    public String createRandomCharError(Random random) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        int randomPositionInText = getRandomCharPosition(text.length(), random);
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.setCharAt(randomPositionInText, createRandomChar(random));
        return stringBuilder.toString();
    }

    public String createRandomCharErrorEverySection(Random random, int sectionLength) {
        if (random == null) {
            random = new Random();
        }
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (sectionLength < 1) {
            return getText();
        }
        int charPosition = 0;
        int textLength = text.length();
        StringBuilder builder = new StringBuilder();
        while (charPosition < textLength) {
            String subsection;
            if (charPosition + sectionLength < textLength) {
                subsection = text.substring(charPosition, charPosition + sectionLength);
            } else {
                subsection = text.substring(charPosition);
            }
            builder.append(insertRandomCharIfEligible(subsection, random, sectionLength));
            charPosition += sectionLength;
        }
        return builder.toString();
    }

    private static int getRandomCharPosition(int textLength, Random random) {
        if (textLength < 1) {
            return -1;
        }
        if (random == null) {
            random = new Random();
        }
        return (int) Math.floor(textLength * random.nextDouble());
    }

    protected static String insertRandomCharIfEligible(String text, Random random, int sectionRange) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (random == null) {
            random = new Random();
        }
        if (sectionRange < 1) {
            return text;
        }
        int randomPosition = getRandomCharPosition(sectionRange, random);
        if (randomPosition > text.length()) {
            return text;
        }
        StringBuilder builder = new StringBuilder(text);
        builder.setCharAt(randomPosition - 1, createRandomChar(random));
        return builder.toString();
    }

    public static char createRandomChar(Random random) {
        if (random == null) {
            random = new Random();
        }
        int charShift = (int) Math.floor(random.nextDouble() * PRINTABLE_CHAR_RANGE);
        return (char) (PRINTABLE_CHAR_LOWER_BOUND + charShift);
    }
}
