package net.brothers_trouble.wizards_tower.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

import net.brothers_trouble.wizards_tower.block.ModBlocks;

public class TowerDoorBottomBlock extends Block {
    private static final VoxelShape CUSTOM_SHAPE = Shapes.box(0, 0, 0, 1, 3, 1); // Custom hitbox

    public TowerDoorBottomBlock(Properties properties) {
        super(properties);
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
            // Place middle block
            level.setBlock(pos.above(), ModBlocks.TOWER_DOOR_MIDDLE.get().defaultBlockState(), 3);
            // Place top block
            level.setBlock(pos.above(2), ModBlocks.TOWER_DOOR_TOP.get().defaultBlockState(), 3);

            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
            // Break the middle block if it exists
            if (level.getBlockState(pos.above()).is(ModBlocks.TOWER_DOOR_MIDDLE.get())) {
                level.destroyBlock(pos.above(), false);
            }
            // Break the top block if it exists
            if (level.getBlockState(pos.above(2)).is(ModBlocks.TOWER_DOOR_TOP.get())) {
                level.destroyBlock(pos.above(2), false);
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
