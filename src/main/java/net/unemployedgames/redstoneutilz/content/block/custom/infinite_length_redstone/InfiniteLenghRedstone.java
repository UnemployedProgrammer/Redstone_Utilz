package net.unemployedgames.redstoneutilz.content.block.custom.infinite_length_redstone;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;

import java.util.Set;

import static net.minecraft.world.level.SignalGetter.DIRECTIONS;

public class InfiniteLenghRedstone extends RedStoneWireBlock {

    private int power = 0;
    public InfiniteLenghRedstone(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return power;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return power;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        power = calculateTargetStrength(pLevel, pPos);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        power = calculateTargetStrength(pLevel, pPos);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        power = calculateTargetStrength(pLevel, pPos);
    }

    private int calculateTargetStrength(Level pLevel, BlockPos pPos) {
        int i = this.getWorstNeighborSignal(pPos, pLevel);
        if (i == 0)
            return 0;
        else
            return 15;
    }

    private int getDirectSignal(BlockPos pPos, Direction pDirection, Level lvl) {
        return lvl.getBlockState(pPos).getDirectSignal(lvl, pPos, pDirection);
    }

    public int getDirectSignalTo(BlockPos pPos, Level lvl) {
        int i = 0;
        i = Math.max(i, this.getDirectSignal(pPos.below(), Direction.DOWN, lvl));
        if (i >= 15) {
            return i;
        } else {
            i = Math.max(i, this.getDirectSignal(pPos.above(), Direction.UP, lvl));
            if (i >= 15) {
                return i;
            } else {
                i = Math.max(i, this.getDirectSignal(pPos.north(), Direction.NORTH, lvl));
                if (i >= 15) {
                    return i;
                } else {
                    i = Math.max(i, this.getDirectSignal(pPos.south(), Direction.SOUTH, lvl));
                    if (i >= 15) {
                        return i;
                    } else {
                        i = Math.max(i, this.getDirectSignal(pPos.west(), Direction.WEST, lvl));
                        if (i >= 15) {
                            return i;
                        } else {
                            i = Math.max(i, this.getDirectSignal(pPos.east(), Direction.EAST, lvl));
                            return i >= 15 ? i : i;
                        }
                    }
                }
            }
        }
    }

    public int getSignal(BlockPos pPos, Direction pDirection, Level lvl) {

        BlockState blockstate = lvl.getBlockState(pPos);
        int i = blockstate.getSignal(lvl, pPos, pDirection);
        return blockstate.shouldCheckWeakPower(lvl, pPos, pDirection) ? Math.max(i, this.getDirectSignalTo(pPos, lvl)) : i;
    }

    private int getWorstNeighborSignal(BlockPos pPos, Level lvl) {
        int i = 0;

        for(Direction direction : DIRECTIONS) {
            int j = this.getSignal(pPos.relative(direction), direction, lvl);
            if (j == 0) {
                return 0;
            }

            if (j > i) {
                i = j;
            }
        }

        return 15;
    }
}
