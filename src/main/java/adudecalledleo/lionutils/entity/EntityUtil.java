package adudecalledleo.lionutils.entity;

import adudecalledleo.lionutils.InitializerUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

/**
 * Utilities for dealing with entities.
 */
public final class EntityUtil {
    private EntityUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * <p>Creates a S2C packet for spawning the entity on the client.<br>
     * Required for any entity that doesn't extend {@link net.minecraft.entity.LivingEntity LivingEntity}.</p>
     * To use, simply override {@link Entity#createSpawnPacket()} with the following:<pre>
     * &#64;Override
     * public Packet&lt;?&gt; createSpawnPacket() {
     *     return EntityUtil.createSpawnPacket(this, packetID);
     * }
     * </pre>
     * Make sure you register your {@code packetId} on the client-side with {@link #registerSpawnPacket(Identifier)}!
     * @param e entity to create spawn packet for
     * @param packetID spawn packet ID
     * @return a spawn packet for the specified entity
     * @author UpcraftLP
     */
    public static Packet<?> createSpawnPacket(Entity e, Identifier packetID) {
        if (e.world.isClient)
            return null;
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
        byteBuf.writeUuid(e.getUuid());
        byteBuf.writeVarInt(e.getEntityId());
        byteBuf.writeDouble(e.getX());
        byteBuf.writeDouble(e.getY());
        byteBuf.writeDouble(e.getZ());
        byteBuf.writeByte(MathHelper.floor(e.pitch * 256.0F / 360.0F));
        byteBuf.writeByte(MathHelper.floor(e.yaw * 256.0F / 360.0F));
        return ServerSidePacketRegistry.INSTANCE.toPacket(packetID, byteBuf);
    }

    // @author UpcraftLP
    @SuppressWarnings("ConstantConditions")
    @Environment(EnvType.CLIENT)
    private static void consumeSpawnPacket(PacketContext ctx, PacketByteBuf byteBuf) {
        EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
        UUID uuid = byteBuf.readUuid();
        int entityId = byteBuf.readVarInt();
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float pitch = (byteBuf.readByte() * 360) / 256.0F;
        float yaw = (byteBuf.readByte() * 360) / 256.0F;
        ctx.getTaskQueue().execute(() -> {
            ClientWorld world = MinecraftClient.getInstance().world;
            Entity e = et.create(world);
            e.updateTrackedPosition(x, y, z);
            e.setPos(x, y, z);
            e.pitch = pitch;
            e.yaw = yaw;
            e.setEntityId(entityId);
            e.setUuid(uuid);
            world.addEntity(entityId, e);
        });
    }

    /**
     * Registers a handler for an entity spawn packet received from the server.<br>
     * Should be used in tandem with {@link #createSpawnPacket(Entity, Identifier)} on the server-side.
     * @param packetID spawn packet ID
     * @author UpcraftLP
     */
    @Environment(EnvType.CLIENT)
    public static void registerSpawnPacket(Identifier packetID) {
        ClientSidePacketRegistry.INSTANCE.register(packetID, EntityUtil::consumeSpawnPacket);
    }
}
