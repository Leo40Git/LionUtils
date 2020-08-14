package adudecalledleo.lionutils.internal.fapibridge.impl.nop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class NOpServerPacketRegistryBridge extends NOpPacketRegistryBridge
        implements ServerPacketRegistryBridge {
    public static final ServerPacketRegistryBridge INSTANCE = new NOpServerPacketRegistryBridge();

    private NOpServerPacketRegistryBridge() {
    }

    @Override
    public void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf) { }
}
