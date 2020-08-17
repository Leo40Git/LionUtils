package adudecalledleo.lionutils.entity;

import adudecalledleo.lionutils.InitializerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * Helper class for dealing with entities.
 * @since 3.0.0
 */
public final class EntityUtil {
    private EntityUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * Sets an {@link Entity}'s position.
     * @param e entity
     * @param x new X position
     * @param y new Y position
     * @param z new Z position
     */
    public static void setPos(Entity e, double x, double y, double z) {
        e.updateTrackedPosition(x, y, z);
        e.setPos(x, y, z);
    }

    /**
     * Sets an {@link Entity}'s position.
     * @param e entity
     * @param pos new position
     */
    public static void setPos(Entity e, Vec3d pos) {
        e.method_30228(pos); // like updateTrackedPosition(pos,x, pos.y, pos.z) but skips a redundant Vec3d alloc
        e.setPos(pos.x, pos.y, pos.z); // unfortunately, there's no method that directly sets Entity.pos...
    }
}
