package org.hyperskill;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class SymbolLevelCorrection{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolLevelCorrection that = (SymbolLevelCorrection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
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

    /**
     * Decryption of message with multiplied characters and generated errors
     * @param multiplier multiplication factor of coded message - greater than 0
     * @return SymbolLevelCorrection object with decoded message
     * @throws IllegalArgumentException if text is not multiplied by given factor, ie. text.length % multiplier != 0
     */
    public SymbolLevelCorrection decodeMultipliedMessage(int multiplier) {
        if (multiplier < 2) {
            return this;
        }
        if (text.isEmpty()) {
            return new SymbolLevelCorrection("");
        }
        if (text.length() % multiplier != 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder builder = new StringBuilder();
        int currentChar = 0;

        while (currentChar < text.length()) {
            String subsection = text.substring(currentChar, currentChar + multiplier);
            builder.append(findRepeatedChar(subsection));
            currentChar += multiplier;
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

    /**
     * Find repeated char in given string.
     * @param text cannot be null, empty or shorter than two
     * @return repeated char
     * @exception  IllegalArgumentException if String text is null, empty or shorter than 3
     */
    protected static char findRepeatedChar(@NotNull String text) throws IllegalArgumentException {
        if (text.length() < 3) {
            throw new IllegalArgumentException();
        }
        char[] chars = text.toCharArray();
        if (chars[0] == chars[1] || chars[0] == chars[2]) {
            return chars[0];
        }
        if (chars[1] == chars[2]) {
            return chars[1];
        } else {
            throw new IllegalArgumentException();
        }
    }
}
