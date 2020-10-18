package adudecalledleo.lionutils.client.entity;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.RequiresFabricAPI;
import adudecalledleo.lionutils.entity.EntityUtil;
import adudecalledleo.lionutils.entity.SpawnPacketUtil;
import adudecalledleo.lionutils.network.PacketBufUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

/**
 * Helper class for dealing with spawn packets on the client-side. To be used with {@link SpawnPacketUtil}.
 *
 * @since 7.0.0
 */
@RequiresFabricAPI
@Environment(EnvType.CLIENT)
public final class ClientSpawnPacketUtil {
    private ClientSpawnPacketUtil() {
        InitializerUtil.utilCtor();
    }

    /**
     * Registers a handler for an entity spawn packet received from the server.<br>
     * Should be used in tandem with {@link SpawnPacketUtil#create(Entity, Identifier)} on the server-side.
     *
     * @param packetID
     *         spawn packet ID
     */
    // @author UpcraftLP
    public static void register(Identifier packetID) {
        ClientSidePacketRegistry.INSTANCE.register(packetID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = PacketBufUtil.readVec3d(byteBuf);
            float pitch = PacketBufUtil.readAngle(byteBuf);
            float yaw = PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                EntityUtil.setPos(e, pos);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                world.addEntity(entityId, e);
            });
        });
    }
}
