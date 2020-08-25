package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class ClientPacketRegistryBridgeImpl extends PacketRegistryBridgeImpl
        implements ClientPacketRegistryBridge {
    public static final ClientPacketRegistryBridge INSTANCE = new ClientPacketRegistryBridgeImpl();

    private ClientPacketRegistryBridgeImpl() {
        super(ClientSidePacketRegistry.INSTANCE);
    }

    @Override
    public void sendToServer(Identifier packetID, PacketByteBuf byteBuf) {
        ClientSidePacketRegistry.INSTANCE.sendToServer(packetID, byteBuf);
    }
}
