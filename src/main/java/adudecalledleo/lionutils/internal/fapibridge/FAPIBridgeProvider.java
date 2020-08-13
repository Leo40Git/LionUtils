package adudecalledleo.lionutils.internal.fapibridge;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.LoggerUtil;
import adudecalledleo.lionutils.internal.fapibridge.impl.FAPIBridgeInitializer;
import adudecalledleo.lionutils.internal.fapibridge.impl.noop.network.NoOpClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.impl.noop.network.NoOpServerPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import org.apache.logging.log4j.Logger;

// ================================================================================
// - Fabric API Bridge mk. 0 - "A Fucking Mess" -
// The *sole* purpose of this entire subsystem is so utilities that need Fabric API
// don't kill everything when FAPI isn't present.
// ================================================================================
public final class FAPIBridgeProvider {
    public static final Logger LOGGER = LoggerUtil.getLogger("LionUtils|FAPIBridge");

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
        public static ClientPacketRegistryBridge CLIENT = NoOpClientPacketRegistryBridge.INSTANCE;
        public static ServerPacketRegistryBridge SERVER = NoOpServerPacketRegistryBridge.INSTANCE;
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
