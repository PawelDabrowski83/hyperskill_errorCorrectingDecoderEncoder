package org.hyperskill;

import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.apache.logging.log4j.Logger;

public class BitLevelErrorsIT {
    final static Logger logger = LogManager.getLogger(BitLevelErrorsIT.class.getName());
    private static final int SEED = 0;
    Random random = new Random(SEED);

    @Test
    public void shouldReadInvertAndWriteWork() {
        String filename = "testIT2.txt";
        String content = "test";

        createTestFile(filename, content);
        logger.info("create file with name: " + filename + " and content: " + content);
        ByteContainer container = readFile(filename);
        logger.info("read file with name: " + filename);
        container.randomizeEveryByte(random);
        logger.info("bits randomized");

        File file = new File(filename);
        try {
            container.writeFile(file);
            logger.info("File saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("File not found or another reason not to save errored bits");
            System.out.println("File not found - errors not saved");
        }

    }

    private static void createTestFile(String filename, String content) {
        final File file = new File(filename);
        byte[] source = content.getBytes();
        logger.info("bytes for test file: " + content);
        ByteContainer container = new ByteContainer(source);
        try {
            container.writeFile(file);
            logger.info("Test file created.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Creation of test file not complete");
        }
    }

    private static ByteContainer readFile(String filename) {
        final File file = new File(filename);
        ByteContainer container = new ByteContainer(new byte[0]);
        try {
            container = ByteContainer.readFile(file);
            logger.info("read file: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Cannot read file: " + file.getAbsolutePath() + " with error: " + e.getMessage());
        }
        return container;
    }
}
