package adudecalledleo.lionutils.internal.fapibridge.impl;

import adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ClientPacketRegistryBridgeImpl;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ServerPacketRegistryBridgeImpl;

class PacketRegistryBridgeInitializer {
    public static void init() {
        FAPIBridgeProvider.PacketRegistry.CLIENT = ClientPacketRegistryBridgeImpl.INSTANCE;
        FAPIBridgeProvider.PacketRegistry.SERVER = ServerPacketRegistryBridgeImpl.INSTANCE;
    }
}
