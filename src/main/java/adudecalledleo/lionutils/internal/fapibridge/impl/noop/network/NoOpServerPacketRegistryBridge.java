package adudecalledleo.lionutils.internal.fapibridge.impl.noop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NoOpServerPacketRegistryBridge implements ServerPacketRegistryBridge {
    public static final ServerPacketRegistryBridge INSTANCE = new NoOpServerPacketRegistryBridge();

    private NoOpServerPacketRegistryBridge() {
    }

    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return null;
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) { }

    @Override
    public void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf) { }
}
