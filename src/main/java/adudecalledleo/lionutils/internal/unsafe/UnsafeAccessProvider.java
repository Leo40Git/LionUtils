package adudecalledleo.lionutils.internal.unsafe;

import adudecalledleo.lionutils.JavaVersion;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.unsafe.impl.UnsafeAccessImpl8D;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;
import org.apache.logging.log4j.Logger;

public class UnsafeAccessProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|UnsafeAccess");
    private static UnsafeAccess instance;
    private static boolean initialized = false;

    public static boolean initAndCheckAvailable() {
        if (!initialized) {
            LOGGER.info("Initializing Unsafe access");
            if (JavaVersion.get() <= 8) {
                try {
                    instance = new UnsafeAccessImpl8D();
                } catch (Exception e) {
                    LOGGER.error("Couldn't initialize Java <=8 implementation of Unsafe access", e);
                }
            } else {
                // TODO
                LOGGER.error("We don't support Java >=9 yet, sorry");
                /*
                try {
                    instance = new UnsafeAccessImpl9U();
                } catch (Exception e) {
                    LOGGER.error("Couldn't initialize Java >=9 of Unsafe access", e);
                }*/
            }
            if (instance == null)
                LOGGER.warn("Unsafe is unavailable!");
            initialized = true;
        }
        return instance != null;
    }

    public static UnsafeAccess getUnsafeAccess() {
        if (!initAndCheckAvailable())
            LOGGER.warn("Unsafe access retrieved when Unsafe was unavailable. This could be a problem!");
        return instance;
    }
}
