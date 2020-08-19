package org.hyperskill;

/**
 * Enum representing different application modes:
 * ENCODE - transform message with bit level correction strategies
 * SEND - transform message simulating transmission errors
 * DECODE - transform encoded message (with or without errors)
 */
public enum WorkingMode {
    ENCODE, SEND, DECODE;

    public static boolean contains(String test) {
        for (WorkingMode workingMode : WorkingMode.values()) {
            if (workingMode.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}


