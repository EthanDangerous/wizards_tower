package net.brothers_trouble.wizards_tower.block.custom;

import net.brothers_trouble.wizards_tower.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TowerDoorMiddleBlock extends Block {
    private static final VoxelShape CUSTOM_SHAPE = Shapes.box(0, -1, 0, 1, 2, 1); // Custom hitbox

    public TowerDoorMiddleBlock(Properties properties) {
        super(properties);
    }

    // ✅ Automatically place middle and top blocks
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
//            // Place middle block
//            level.setBlock(pos.above(), ModBlocks.TOWER_DOOR_MIDDLE.get().defaultBlockState(), 3);
//            // Place top block
//            level.setBlock(pos.above(2), ModBlocks.TOWER_DOOR_TOP.get().defaultBlockState(), 3);

            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
            // Break the middle block if it exists
            if (level.getBlockState(pos.above()).is(ModBlocks.TOWER_DOOR_TOP.get())) {
                level.destroyBlock(pos.above(), false);
            }
            // Break the top block if it exists
            if (level.getBlockState(pos.below()).is(ModBlocks.TOWER_DOOR_BOTTOM.get())) {
                level.destroyBlock(pos.below(), false);
            }

            // Play breaking sound
            level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    // ✅ Change hitbox shape
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return CUSTOM_SHAPE;
    }
}
