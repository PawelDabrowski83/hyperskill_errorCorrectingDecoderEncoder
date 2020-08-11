package org.hyperskill;

import java.util.Random;

public class SymbolLevelErrorEmulator {

    protected static final byte PRINTABLE_CHAR_LOWER_BOUND = 32;
    protected static final byte PRINTABLE_CHAR_UPPER_BOUNT = 126;
    protected static final byte PRINTABLE_CHAR_RANGE = PRINTABLE_CHAR_UPPER_BOUNT - PRINTABLE_CHAR_LOWER_BOUND;

    private final String text;

    public SymbolLevelErrorEmulator(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String createRandomCharError(Random random) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        int randomPositionInText = (int) Math.floor(text.length() * random.nextDouble());
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.setCharAt(randomPositionInText, createRandomChar(random));
        return stringBuilder.toString();
    }

    public static char createRandomChar(Random random) {
        int charShift = (int) Math.floor(random.nextDouble() * PRINTABLE_CHAR_RANGE);
        return (char) (PRINTABLE_CHAR_LOWER_BOUND + charShift);
    }
}
