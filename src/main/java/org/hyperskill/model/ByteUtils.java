package org.hyperskill.model;

public class ByteUtils {
    public static Byte[] boxByteArray(byte[] given) {
        if (given == null || given.length ==0) {
            return new Byte[0];
        }
        Byte[] target = new Byte[given.length];
        int counter = 0;
        for (byte b : given) {
            target[counter] = new Byte(b);
            counter++;
        }
        return target;
    }
}
