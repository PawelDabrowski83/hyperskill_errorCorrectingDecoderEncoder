package org.hyperskill;

import java.util.Arrays;

public class SymbolLevelCorrection {

    private final String text;

    public SymbolLevelCorrection(String text) {
        if (text == null) {
            text = "";
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SymbolLevelCorrection{" +
                "text='" + text + '\'' +
                '}';
    }

    public SymbolLevelCorrection multiplyMessage(int multiplier) {
        if (text.isEmpty()) {
            return this;
        }
        if (multiplier < 2) {
            return new SymbolLevelCorrection(text);
        }
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : chars) {
            builder.append(multiplyChar(c, multiplier));
        }
        return new SymbolLevelCorrection(builder.toString());
    }

    protected static String multiplyChar(char c, int multiplier) {
        if (multiplier < 0) {
            multiplier = 1;
        }
        char[] buffer = new char[multiplier];
        Arrays.fill(buffer, c);
        return new String(buffer);
    }
}
