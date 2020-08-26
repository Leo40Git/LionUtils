package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.internal.fapibridge.network.ServerPacketRegistryBridge;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class ServerPacketRegistryBridgeImpl extends PacketRegistryBridgeImpl
        implements ServerPacketRegistryBridge {
    public static final ServerPacketRegistryBridge INSTANCE = new ServerPacketRegistryBridgeImpl();

    private ServerPacketRegistryBridgeImpl() {
        super(ServerSidePacketRegistry.INSTANCE);
        InitializerUtil.singletonCheck(INSTANCE);
    }

    @Override
    public void sendToPlayer(PlayerEntity player, Identifier packetID, PacketByteBuf byteBuf) {
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packetID, byteBuf);
    }
}
