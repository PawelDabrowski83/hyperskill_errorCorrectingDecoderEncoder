package org.hyperskill;

import java.util.Objects;
import java.util.regex.Matcher;
import static org.hyperskill.HammingEncoder.HammingCode;

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

    protected String correctByte(String binary) {
        HammingEncoder encoder = new HammingEncoder("");
        StringBuilder source = new StringBuilder(binary);
        int checkP4 = encoder.calculateParity(source.toString(), HammingCode.P4) == '1' ? 1 : 0;
        int checkP2 = encoder.calculateParity(source.toString(), HammingCode.P2) == '1' ? 1 : 0;
        int checkP1 = encoder.calculateParity(source.toString(), HammingCode.P1) == '1' ? 1 : 0;

        int position = 0;
        if (checkP1 == 1) {
            position += 1;
        }
        if (checkP2 == 1) {
            position += 2;
        }
        if (checkP4 == 1) {
            position += 4;
        }
        if (position == 0) {
            return binary;
        }
        source = invertBitAt(position - 1, source);
        return source.toString();
    }

    public String correctNoise(String binary) {
        if (binary == null || binary.isBlank() || binary.length() % 8 != 0){
            return "";
        }
        if (binary.length() == 8){
            return correctByte(binary);
        }
        StringBuilder source = new StringBuilder(binary);
        StringBuilder target = new StringBuilder();
        while (source.length() > 0){
            String substring = source.substring(0, 8);
            source.delete(0, 8);
            target.append(correctByte(substring));
        }
        return target.toString();
    }

    protected StringBuilder invertBitAt(int position, StringBuilder builder) {
        char present = builder.charAt(position);
        present = present == '1' ? '0' : '1';
        builder.setCharAt(position, present);
        return builder;
    }

    protected String decodeByte(String substring) {
        StringBuilder builder = new StringBuilder(substring);
        builder.deleteCharAt(7);
        builder.deleteCharAt(3);
        builder.delete(0, 2);
        return builder.toString();
    }
}
