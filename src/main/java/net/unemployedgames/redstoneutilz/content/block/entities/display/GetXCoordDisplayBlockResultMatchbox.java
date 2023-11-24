package net.unemployedgames.redstoneutilz.content.block.entities.display;

import net.minecraft.core.Direction;

public class GetXCoordDisplayBlockResultMatchbox {
    public int z_y;
    public Direction.Axis axis;
    public GetXCoordDisplayBlockResultMatchbox(int z_y, Direction.Axis axis) {
        this.z_y = z_y;
        this.axis = axis;
    }

    public int getZ_y() {
        return z_y;
    }

    public Direction.Axis getAxis() {
        return axis;
    }
}
