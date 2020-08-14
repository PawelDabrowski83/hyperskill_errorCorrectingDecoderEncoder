package org.hyperskill;

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
}
