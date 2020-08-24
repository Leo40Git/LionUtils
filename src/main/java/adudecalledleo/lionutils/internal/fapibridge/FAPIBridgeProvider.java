package adudecalledleo.lionutils.internal.fapibridge;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.fapibridge.impl.FAPIBridgeInitializer;
import adudecalledleo.lionutils.internal.fapibridge.impl.nop.network.NullClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.impl.nop.network.NullServerPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import org.apache.logging.log4j.Logger;

// +-----------------------------------------------------------------+
// | Fabric API Bridge Subsystem mk. -1                              |
// | Util class -> FAPIBridgeProvider -> Fabric API                  |
// | If FAPI is not present, errors out with  a proper error message |
// | rather than a NoClassDefFoundError                              |
// +-----------------------------------------------------------------+
public final class FAPIBridgeProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|FAPIBridge");

    private FAPIBridgeProvider() {
        InitializerUtil.badConstructor();
    }

    public static final class PacketRegistry {
        private PacketRegistry() {
            InitializerUtil.badConstructor();
        }
        public static ClientPacketRegistryBridge CLIENT;
        public static ServerPacketRegistryBridge SERVER;
        static {
            CLIENT = NullClientPacketRegistryBridge.INSTANCE;
            SERVER = NullServerPacketRegistryBridge.INSTANCE;
            init();
        }
    }

    private static boolean initialized = false;
    private static void init() {
        if (initialized)
            return;
        FAPIBridgeInitializer.init();
        initialized = true;
        LOGGER.info("Fabric API Bridge initialized.");
    }
}
