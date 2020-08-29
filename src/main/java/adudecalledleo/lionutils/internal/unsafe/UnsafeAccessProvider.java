package adudecalledleo.lionutils.internal.unsafe;

import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.unsafe.impl.UnsafeAccessImpl;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.util.JavaVersion;

public class UnsafeAccessProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|UnsafeAccess");
    private static UnsafeAccess instance;
    private static boolean initialized = false;

    public static boolean initAndCheckAvailable() {
        if (!initialized) {
            LOGGER.info("Initializing Unsafe access, running Java {} by {} FYI", JavaVersion.current(),
                    System.getProperty("java.vendor"));
            try {
                instance = new UnsafeAccessImpl();
            } catch (Exception e) {
                LOGGER.error("Couldn't initialize Unsafe access implementation", e);
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
