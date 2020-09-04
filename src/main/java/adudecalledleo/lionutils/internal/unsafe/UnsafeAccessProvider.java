package adudecalledleo.lionutils.internal.unsafe;

import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.unsafe.impl.UnsafeAccessImplDirect;
import adudecalledleo.lionutils.unsafe.UnsafeAccess;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.util.JavaVersion;

import java.util.function.DoublePredicate;
import java.util.function.Supplier;

public class UnsafeAccessProvider {
    private static final class ImplInfo {
        public final String description;
        public final DoublePredicate usablePredicate;
        public final Supplier<UnsafeAccess> implSupplier;

        private ImplInfo(String description,
                DoublePredicate usablePredicate, Supplier<UnsafeAccess> implSupplier) {
            this.description = description;
            this.usablePredicate = usablePredicate;
            this.implSupplier = implSupplier;
        }

        public boolean canBeUsed() {
            return usablePredicate.test(JavaVersion.current());
        }

        public UnsafeAccess getImpl() {
            return implSupplier.get();
        }
    }

    private static final ImplInfo[] IMPL_INFOS = {
            new ImplInfo("direct proxy", v -> true, UnsafeAccessImplDirect::new),
    };

    static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|UnsafeAccess");
    private static UnsafeAccess instance;
    private static boolean initialized = false;

    public static boolean initAndCheckAvailable() {
        if (!initialized) {
            LOGGER.info("Initializing Unsafe access, running Java {} by {} FYI", JavaVersion.current(),
                    System.getProperty("java.vendor"));
            for (ImplInfo info : IMPL_INFOS) {
                if (!info.canBeUsed())
                    continue;
                try {
                    instance = info.getImpl();
                } catch (Exception e) {
                    LOGGER.error("Couldn't initialize Unsafe access implementation: " + info.description, e);
                    continue;
                }
                LOGGER.info("Successfully initialized Unsafe access implementation: {}", info.description);
                break;
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
