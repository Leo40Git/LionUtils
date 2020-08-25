package adudecalledleo.lionutils.internal.fapibridge.network;

import net.fabricmc.api.EnvType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.thread.ThreadExecutor;

public interface PacketContextBridge {
    EnvType getPacketEnvironment();

    PlayerEntity getPlayer();

    @SuppressWarnings("rawtypes")
    ThreadExecutor getTaskQueue();
}
