package org.hyperskill;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class Utils {

    public static int getRandomPosition(int textLength, Random random) {
        if (textLength < 1) {
            return -1;
        }
        if (random == null) {
            random = new Random();
        }
        return (int) Math.floor(textLength * random.nextDouble());
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize)
            throws IOException {
        byte[] buf = new byte[bufferSize];
        int bytesRead = input.read(buf);
        while (bytesRead != -1) {
            output.write(buf, 0, bytesRead);
            bytesRead = input.read(buf);
        }
        output.flush();
    }

    public static byte[] getByteArrayFromString(String binary) {
        if (binary == null || binary.isEmpty() || binary.length() % 8 != 0) {
            return new byte[0];
        }
        int bytes = binary.length() / 8;
        byte[] target = new byte[bytes];
        int counter = 0;
        StringBuilder source = new StringBuilder(binary);

        while (source.length() > 0) {
            String substring = source.substring(0, 8);
            source.delete(0, 8);
            byte selectedByte = 0;
            try {
                selectedByte = Byte.valueOf(substring, 2);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            target[counter] = selectedByte;
            counter++;
        }
        return target;
    }
}
