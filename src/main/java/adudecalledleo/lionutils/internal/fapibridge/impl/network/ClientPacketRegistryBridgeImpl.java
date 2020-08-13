package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ClientPacketRegistryBridgeImpl implements ClientPacketRegistryBridge {
    public static final ClientPacketRegistryBridge INSTANCE = new ClientPacketRegistryBridgeImpl();

    private ClientPacketRegistryBridgeImpl() {
    }

    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return ClientSidePacketRegistry.INSTANCE.toPacket(packetID, byteBuf);
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) {
        ClientSidePacketRegistry.INSTANCE.register(packetID,
                (context, buffer) -> consumer.accept(new PacketContextBridgeImpl(context), buffer));
    }

    @Override
    public void sendToServer(Identifier packetID, PacketByteBuf byteBuf) {
        ClientSidePacketRegistry.INSTANCE.sendToServer(packetID, byteBuf);
    }
}
