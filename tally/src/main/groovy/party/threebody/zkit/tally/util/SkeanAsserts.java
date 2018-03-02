package party.threebody.zkit.tally.util;

import party.threebody.skean.misc.SkeanInvalidArgumentException;

public final class SkeanAsserts {

    public static void assertNotNull(Object argVal, String argName) {
        if (argVal == null) {
            throw new SkeanInvalidArgumentException(argName + " should not be null");
        }
    }
}
