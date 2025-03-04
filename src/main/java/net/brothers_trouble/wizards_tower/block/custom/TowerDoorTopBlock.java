package net.brothers_trouble.wizards_tower.block.custom;

import net.brothers_trouble.wizards_tower.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TowerDoorTopBlock extends Block {
    private static final VoxelShape CUSTOM_SHAPE = Shapes.box(0, -2, 0, 1, 1, 1); // Custom hitbox

    public TowerDoorTopBlock(Properties properties) {
        super(properties);
    }

    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos,
                                               Player pPlayer, BlockHitResult pHitResult) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);
        return InteractionResult.SUCCESS;
    }

    // ✅ Prevent placement if the two blocks above aren't air
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.above(2)).isAir();
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
            if (level.getBlockState(pos.below()).is(ModBlocks.TOWER_DOOR_MIDDLE.get())) {
                level.destroyBlock(pos.below(), false);
            }
            // Break the top block if it exists
            if (level.getBlockState(pos.below(2)).is(ModBlocks.TOWER_DOOR_BOTTOM.get())) {
                level.destroyBlock(pos.below(2), false);
            }

            if (level.getBlockState(pos.south()).is(ModBlocks.TOWER_DOOR_TOP_FLIPPED.get())) {
                level.destroyBlock(pos.south(), false);
            }
            if (level.getBlockState(pos.below().south()).is(ModBlocks.TOWER_DOOR_MIDDLE_FLIPPED.get())) {
                level.destroyBlock(pos.below().south(), false);
            }
            if (level.getBlockState(pos.below().south(2)).is(ModBlocks.TOWER_DOOR_BOTTOM_FLIPPED.get())) {
                level.destroyBlock(pos.below().south(2), false);
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
