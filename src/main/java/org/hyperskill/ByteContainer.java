package org.hyperskill;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class ByteContainer {

    private final byte[] bytes;

    public ByteContainer(byte[] bytes) {
        if (bytes == null) {
            bytes = new byte[0];
        }
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteContainer that = (ByteContainer) o;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return "ByteContainer{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

    public static ByteContainer readFile(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Utils.copy(new FileInputStream(file), baos, 100000);
        byte[] bytes = baos.toByteArray();
        return new ByteContainer(bytes);
    }

    public ByteContainer randomizeEveryByte(Random random) {
        byte[] buffer = new byte[getBytes().length];
        int counter = 0;
        for (byte b : getBytes()) {
            int randomPosition = Utils.getRandomPosition(8, random);
            BitLevelErrorEmulator emulator = new BitLevelErrorEmulator(b);
            buffer[counter] = (byte) emulator.invertBit(randomPosition).getContent();
            counter++;
        }

        return new ByteContainer(buffer);
    }


}
