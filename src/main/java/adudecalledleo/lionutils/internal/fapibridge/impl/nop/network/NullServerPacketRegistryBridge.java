package adudecalledleo.lionutils.internal.fapibridge.impl.nop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class NullServerPacketRegistryBridge extends NullPacketRegistryBridge
        implements ServerPacketRegistryBridge {
    public static final ServerPacketRegistryBridge INSTANCE = new NullServerPacketRegistryBridge();

    private NullServerPacketRegistryBridge() {
    }

    @Override
    public void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf) { }
}
