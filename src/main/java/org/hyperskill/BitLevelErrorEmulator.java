package org.hyperskill;

import java.util.Objects;

public class BitLevelErrorEmulator {

    private static final int UNSIGNED_BYTE = 255;
    private final int content;

    public BitLevelErrorEmulator(int content) {
        if (content > UNSIGNED_BYTE) {
            content = content % UNSIGNED_BYTE;
        }
        this.content = content;
    }

    public int getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitLevelErrorEmulator that = (BitLevelErrorEmulator) o;
        return content == that.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "BitLevelErrorEmulator{" +
                "content=" + content +
                '}';
    }

    public BitLevelErrorEmulator invertBit(int position) {
        if (position < 0 || position > 7) {
            return this;
        }
        int mask = 1 << 7 - position;
        String bin = Integer.toBinaryString(mask);
        int result = getContent() ^ mask;
        return new BitLevelErrorEmulator(result);
    }
}
