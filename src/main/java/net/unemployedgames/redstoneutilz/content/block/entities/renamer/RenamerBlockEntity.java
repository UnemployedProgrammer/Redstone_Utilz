package net.unemployedgames.redstoneutilz.content.block.entities.renamer;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntSets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.util.TickableBlockEntity;
import net.unemployedgames.redstoneutilz.infrastructure.content.blocks.RootInputAndOutputSlotInventoryBlockEntity;

import java.util.Objects;
import java.util.UUID;

public class RenamerBlockEntity extends RootInputAndOutputSlotInventoryBlockEntity implements TickableBlockEntity {
    private String rename_to = "";
    private int progress;
    private boolean isJson = false;
    public RenamerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.RENAMER_BLOCK_ENTITY.get(), pPos, pBlockState, Direction.UP, Direction.NORTH);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("rename_to", rename_to);
        pTag.putBoolean("isJson", isJson);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("rename_to", Tag.TAG_STRING)) {
            this.rename_to = pTag.getString("rename_to");
        }if(pTag.contains("isJson")) {
            this.rename_to = pTag.getString("isJson");
        }

    }

    public void setRename_to(String rename_to) {
        if(Objects.equals(rename_to, ".json-on"))
            isJson = true;
        else if (Objects.equals(rename_to, ".json-off"))
            isJson = false;
        else
            this.rename_to = rename_to;
    }
    public String getRename_to() {
        return rename_to;
    }

    public void renameOneItem() {
        if(canName()) {
            AddOneItemToSlotMethodOutput returnval =  addItemsToSlot(getOutputItem(), getInputItem().copyWithCount(1));
            if(returnval.wasSuccessful) {
                getInputItem().shrink(1);
                setOutputItem(returnval.outputStack);
                renameWholeOutputSlot(validateText(getRename_to()));
            }
            printDetails();
        } else LogUtils.getLogger().info("Cannot Craft");
    }

    public void printDetails() {
        System.out.println("DEBUGGING_RENAMER_RENAME - REDSTONEUTILS | " + System.nanoTime()+"/"+ UUID.randomUUID());
        System.out.println("Input Item Count: " + getInputItem().getCount());
        System.out.println("Output Item Count: " + getOutputItem().getCount());
        System.out.println("");
        System.out.println("Input Item: " + getInputItem().getItem().getDescription().getString());
        System.out.println("Output Item: " + getOutputItem().getItem().getDescription().getString());
        System.out.println("");
        System.out.println("Input Item Name: " + getInputItem().getDisplayName().getString());
        System.out.println("Output Item Name: " + getOutputItem().getDisplayName().getString());
    }

    public void renameWholeOutputSlot(Component name) {
        getOutputInventory().getStackInSlot(0).setHoverName(name);
    }

    public static class AddOneItemToSlotMethodOutput {
        public boolean wasSuccessful;
        public ItemStack outputStack;

        public AddOneItemToSlotMethodOutput(ItemStack outputStack, boolean wasSuccessful) {
            this.outputStack = outputStack;
            this.wasSuccessful = wasSuccessful;
        }
    }

    public AddOneItemToSlotMethodOutput addItemsToSlot(ItemStack itemStack, ItemStack add) {
        ItemStack copy = itemStack.copy();

        if(copy.isEmpty()) {
            copy = add;
            return new AddOneItemToSlotMethodOutput(copy, true);
        }

        if(!copy.isEmpty() && copy.is(add.getItem())) {
            copy.grow(add.getCount());
            return new AddOneItemToSlotMethodOutput(copy, true);
        }

        return new AddOneItemToSlotMethodOutput(itemStack, false);
    }

    public boolean canName() {
        return getOutputItem().isEmpty() || getOutputItem().is(getInputItem().getItem()) && getOutputItem().getCount() < 64 && getInputItem().getCount() >= 1;
    }
    public Component validateText(String str) {
        return isJson ? Component.Serializer.fromJson(str) : Component.literal(str);
    }

    @Override
    public ItemStackHandler getInputInventory() {
        return super.getInputInventory();
    }

    @Override
    public ItemStackHandler getOutputInventory() {
        return super.getOutputInventory();
    }

    public void moveItems() {
        if(!level.isClientSide()) {
            BlockPos pos = worldPosition.relative(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING).getOpposite());
            spawnItemStack(pos.getX() + 0.5 , pos.getY() + 1.5, pos.getZ() + 0.5, level, getOutputItem());
            setInputItem(ItemStack.EMPTY);
            setOutputItem(ItemStack.EMPTY);
        }
    }

    public void spawnItemStack(Double pX, Double pY, Double pZ, Level pLevel, ItemStack pStack) {
        ItemEntity itemEntity = new ItemEntity(pLevel, pX, pY, pZ, pStack);
        pLevel.addFreshEntity(itemEntity);
    }

    @Override
    public void tick() {
        if(this.level == null)
            return;
        if(getInputItem().isEmpty())
            return;
        else {
            progress++;
            if(progress >= 10) {
                if(level.getBlockState(worldPosition).getValue(BooleanProperty.create("powered"))) {
                    renameOneItem();
                    if (!getOutputItem().isEmpty())
                        moveItems();
                }
                progress = 0;
            }
        }
    }


    //if(this.level == null)
    //            return;
    //        if(getInputItem().isEmpty())
    //            return;
    //        else {
    //            progress++;
    //            if(progress >= 10) {
    //                renameOneItem();
    //                progress = 0;
    //            }
    //        }

    //@Override
    //public void tick() {
        //renameOneItem();
    //}
}
