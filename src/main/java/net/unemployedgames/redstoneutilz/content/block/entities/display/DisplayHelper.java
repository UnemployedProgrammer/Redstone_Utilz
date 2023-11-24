package net.unemployedgames.redstoneutilz.content.block.entities.display;

import com.mojang.math.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import org.checkerframework.checker.units.qual.A;

public class DisplayHelper {
    public static int get1stDisplayPosition_Y(Level lvl, BlockPos pos) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int y = pos.getY();

        while (continuey) {
            if(lvl.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).is(tg)) {
                y++;
            } else {
                continuey = false;
            }
        }

        return y - 1;
    }

    public static int get2stDisplayPosition_Y(Level lvl, BlockPos pos) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int y = pos.getY();

        while (continuey) {
            if(lvl.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).is(tg)) {
                y--;
            } else {
                continuey = false;
            }
        }

        return y + 1;
    }



    public static GetXCoordDisplayBlockResultMatchbox get1stDisplayPosition_X_OR_Y(Level lvl, BlockPos pos, Direction direction) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int x = pos.get(getAxisForDirection(direction));

        while (continuey) {
            if(lvl.getBlockState(getCoordForDirection(direction, bp)).is(tg) && lvl.getBlockState(getCoordForDirection(direction, pos)).getValue(DirectionalBlock.FACING) == direction) {
                x++;
                bp = getCoordForDirection(direction, bp);
            } else {
                continuey = false;
            }
        }

        return new GetXCoordDisplayBlockResultMatchbox(x, getAxisForDirection(direction));
    }

    public static GetXCoordDisplayBlockResultMatchbox get2stDisplayPosition_X_OR_Y(Level lvl, BlockPos pos, Direction direction) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int x = pos.get(getAxisForDirection(direction));

        while (continuey) {
            if(lvl.getBlockState(getCoordForDirection(direction.getOpposite(), bp)).is(tg) && lvl.getBlockState(getCoordForDirection(direction.getOpposite(), pos)).getValue(DirectionalBlock.FACING) == direction.getOpposite()) {
                x++;
                bp = getCoordForDirection(direction.getOpposite(), bp);
            } else {
                continuey = false;
            }
        }

        return new GetXCoordDisplayBlockResultMatchbox(x, getAxisForDirection(direction));
    }

    public static BlockPos getCoordForDirection(Direction direction, BlockPos pos) {
        if(direction == Direction.NORTH) return pos.west();
        if(direction == Direction.EAST) return pos.north();
        if(direction == Direction.SOUTH) return pos.east();
        if(direction == Direction.WEST) return pos.south();
        else return pos;
    }
    public static Direction.Axis getAxisForDirection(Direction direction) {
        if(direction == Direction.NORTH) return Direction.Axis.X;
        if(direction == Direction.EAST) return Direction.Axis.Z;
        if(direction == Direction.SOUTH) return Direction.Axis.X;
        if(direction == Direction.WEST) return Direction.Axis.Z;
        else return Direction.Axis.Z;
    }

    public static int countYAmountDisplays(Level lvl, BlockPos pos) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int y = pos.getY();
        int c = 0;

        while (continuey) {
            if(lvl.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).is(tg)) {
                y++;
                c++;
            } else {
                continuey = false;
            }
        }

        return c;
    }

    public static int countXAmountDisplays(Level lvl, BlockPos pos, Direction direction) {
        Block tg = ModBlocks.DISPLAY_BLOCK.get();
        BlockPos bp = pos;
        Boolean continuey = true;
        int x = pos.getY();
        int c = 0;

        while (continuey) {
            if(lvl.getBlockState(getCoordForDirection(direction.getOpposite(), bp)).is(tg) && lvl.getBlockState(getCoordForDirection(direction.getOpposite(), pos)).getValue(DirectionalBlock.FACING) == direction.getOpposite()) {
                x++;
                bp = getCoordForDirection(direction.getOpposite(), bp);
                c++;
            } else {
                continuey = false;
            }
        }

        return c + 1;
    }
}
