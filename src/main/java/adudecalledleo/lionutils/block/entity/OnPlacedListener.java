package adudecalledleo.lionutils.block.entity;

import adudecalledleo.lionutils.block.BaseBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * <p>Represents a listener to the attached block's {@link Block#onPlaced(World, BlockPos, BlockState, LivingEntity,
 * ItemStack) onPlaced} event.</p>
 * <strong>NOTE:</strong> For this to be called, the attached block has to extend {@link BaseBlockWithEntity}!
 *
 * @since 5.0.0
 */
@FunctionalInterface
public interface OnPlacedListener {
    /**
     * Called when the attached block is placed.
     *
     * @param world
     *         world block was placed in
     * @param pos
     *         position of block
     * @param state
     *         state of block
     * @param placer
     *         entity that placed the block
     * @param itemStack
     *         stack entity was holding when placing the block
     */
    void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack);
}
