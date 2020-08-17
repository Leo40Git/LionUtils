package adudecalledleo.lionutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

/**
 * <p>{@link Block} that faces one of four cardinal directions. Facing property is {@link #FACING}.</p>
 * Unlike {@link HorizontalFacingBlock}, this
 * {@linkplain #appendProperties(StateManager.Builder) appends the property to the block} and
 * {@linkplain #getPlacementState(ItemPlacementContext) automatically configures the placement state}.
 * @see HorizontalFacingBlock
 * @since 1.0.0
 */
public class ProperHorizontalFacingBlock extends HorizontalFacingBlock {
    public ProperHorizontalFacingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
