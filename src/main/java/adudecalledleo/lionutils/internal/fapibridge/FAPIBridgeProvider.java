package adudecalledleo.lionutils.internal.fapibridge;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.fapibridge.impl.FAPIBridgeInitializer;
import adudecalledleo.lionutils.internal.fapibridge.impl.nop.network.NOpClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.impl.nop.network.NOpServerPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import org.apache.logging.log4j.Logger;

// +------------------------------------------------+
// | Fabric API Bridge Subsystem mk. -1             |
// | Util class -> FAPIBridgeProvider -> Fabric API |
// | If FAPI is not present, redirects to a no-op   |
// +------------------------------------------------+
public final class FAPIBridgeProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|FAPIBridge");

    private FAPIBridgeProvider() {
        InitializerUtil.badConstructor();
    }

    private static class ProviderSubclass {
        static {
            init();
        }
    }

    public static final class PacketRegistry extends ProviderSubclass {
        private PacketRegistry() {
            InitializerUtil.badConstructor();
        }
        public static ClientPacketRegistryBridge CLIENT = NOpClientPacketRegistryBridge.INSTANCE;
        public static ServerPacketRegistryBridge SERVER = NOpServerPacketRegistryBridge.INSTANCE;
    }

    private static boolean initialized = false;
    private static void init() {
        if (initialized)
            return;
        FAPIBridgeInitializer.init(LOGGER);
        initialized = true;
        LOGGER.info("Fabric API Bridge initialized.");
    }
}
