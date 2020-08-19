package org.hyperskill;

import java.io.File;
import java.io.IOException;

public class Application {

    private static final String RECEIVED_FILE = "received.txt";
    private static final String SENT_FILE = "send.txt";
    private static final String ENCODED_FILE = "encoded.txt";
    private static final String DECODED_FILE = "decoded.txt";
    private ByteContainer byteContainer;

    public void encode() {
        try {
            byteContainer = ByteContainer.readFile(new File(RECEIVED_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void send() {

    }

    public static void decode() {

    }
}
