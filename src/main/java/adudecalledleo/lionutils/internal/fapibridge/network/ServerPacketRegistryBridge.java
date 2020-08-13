package adudecalledleo.lionutils.internal.fapibridge.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface ServerPacketRegistryBridge extends PacketRegistryBridge {
    void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf);
}
