package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ServerPacketRegistryBridgeImpl implements ServerPacketRegistryBridge {
    public static final ServerPacketRegistryBridge INSTANCE = new ServerPacketRegistryBridgeImpl();

    private ServerPacketRegistryBridgeImpl() {
    }

    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return ServerSidePacketRegistry.INSTANCE.toPacket(packetID, byteBuf);
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) {
        ServerSidePacketRegistry.INSTANCE.register(packetID,
                (context, buffer) -> consumer.accept(new PacketContextBridgeImpl(context), buffer));
    }

    @Override
    public void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf) {
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packetID, byteBuf);
    }
}
