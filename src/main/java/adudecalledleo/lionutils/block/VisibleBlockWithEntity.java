package adudecalledleo.lionutils.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;

/**
 * <p>{@link net.minecraft.block.Block Block} that provides a {@link net.minecraft.block.entity.BlockEntity
 * BlockEntity}.</p> Unlike {@link BaseBlockWithEntity}, this block is visible without having to add a {@link
 * net.minecraft.client.render.block.entity.BlockEntityRenderer BlockEntityRenderer} to it.
 *
 * @since 3.0.0
 */
public abstract class VisibleBlockWithEntity extends BaseBlockWithEntity {
    public VisibleBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
