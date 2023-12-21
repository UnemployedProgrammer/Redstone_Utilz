package net.unemployedgames.redstoneutilz.infrastructure.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootInputAndOutputSlotInventoryBlockEntity extends BlockEntity {

    public Direction inputSide;
    public Direction outputSide;
    public final ItemStackHandler input = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            RootInputAndOutputSlotInventoryBlockEntity.this.setChanged();
        }
    };
    public final ItemStackHandler output = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            RootInputAndOutputSlotInventoryBlockEntity.this.setChanged();
        }
    };


    public final LazyOptional<ItemStackHandler> optional_input = LazyOptional.of(() -> this.input);
    public final LazyOptional<ItemStackHandler> optional_output = LazyOptional.of(() -> this.output);

    public RootInputAndOutputSlotInventoryBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, Direction inputSide, Direction outputSide) {
        super(pType, pPos, pBlockState);
        this.inputSide = inputSide;
        this.outputSide = outputSide;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        CompoundTag inventories = new CompoundTag();
        inventories.put("input", this.input.serializeNBT());
        inventories.put("output", this.output.serializeNBT());
        pTag.put("inventory", inventories);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("inventory", CompoundTag.TAG_COMPOUND)) {
            CompoundTag inventories = pTag.getCompound("inventory");
            if(inventories.contains("input", CompoundTag.TAG_COMPOUND)) {
                this.input.deserializeNBT(inventories.getCompound("input"));
            }
            if(inventories.contains("output", CompoundTag.TAG_COMPOUND)) {
                this.output.deserializeNBT(inventories.getCompound("output"));
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == inputSide) {
                return optional_input.cast();
            }

            if(side == outputSide) {
                return optional_output.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional_input.invalidate();
        this.optional_output.invalidate();
    }

    public ItemStackHandler getInputInventory() {
        return input;
    }
    public ItemStackHandler getOutputInventory() {
        return output;
    }

    public ItemStack getInputItem() {
        return this.input.getStackInSlot(0);
    }
    public void setInputItem(ItemStack itemStack) {
        this.input.setStackInSlot(0, itemStack);
    }
    public ItemStack getOutputItem() {
        return this.output.getStackInSlot(0);
    }
    public void setOutputItem(ItemStack itemStack) {
        this.output.setStackInSlot(0, itemStack);
    }
}
