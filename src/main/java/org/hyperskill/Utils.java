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

    public static byte[] getByteArrayFromString(String given) {
        byte[] target = new byte[given.toCharArray().length];
        int counter = 0;
        for (byte ignored : target) {
            target[counter] = (byte) given.charAt(counter);
            counter++;
        }
        return target;
    }
}
