package org.hyperskill;

import java.util.Objects;
import java.util.regex.Matcher;

public class HammingDecoder {

    private final String text;

    public HammingDecoder(String text) {
        if (text == null) {
            text = "";
        }
        Matcher matcher = HammingEncoder.BYTE_PATTERN.matcher(text);
        if (!matcher.find()) {
            text = "";
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HammingDecoder that = (HammingDecoder) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "HammingDecoder{" +
                "text='" + text + '\'' +
                '}';
    }

    public String decode() {
        if (text.isEmpty()) {
            return "";
        }
        String message = correctNoise(text);
        StringBuilder source = new StringBuilder(message);
        StringBuilder target = new StringBuilder();
        while (source.length() > 0) {
            String substring = source.substring(0, 8);
            source.delete(0, 8);
            target.append(decodeByte(substring));
        }
        return target.toString();
    }

    public String correctNoise(String given) {
        return "";
    }

    protected String decodeByte(String substring) {
        StringBuilder builder = new StringBuilder(substring);
        builder.deleteCharAt(7);
        builder.deleteCharAt(3);
        builder.delete(0, 2);
        return builder.toString();
    }
}
