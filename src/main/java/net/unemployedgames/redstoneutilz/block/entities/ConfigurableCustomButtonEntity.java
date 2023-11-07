package net.unemployedgames.redstoneutilz.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.unemployedgames.redstoneutilz.util.TickableBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfigurableCustomButtonEntity extends BlockEntity implements TickableBlockEntity {
    private int ticksdelayedl;
    private int signalstrenghl;
    public ConfigurableCustomButtonEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.CONFIGURABLE_CUSTOM_BUTTON_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);

        this.ticksdelayedl = pTag.getInt("ticks_pressed");
        this.signalstrenghl = pTag.getInt("signal_strengh");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putInt("ticks_pressed", this.ticksdelayedl);
        pTag.putInt("signal_strengh", this.signalstrenghl);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    public int getTicksdelayed() {
        return this.ticksdelayedl;
    }
    public int getSignalstrengh() {
        return this.signalstrenghl;
    }

    public void setSignalstrengh(int ss) {
        this.signalstrenghl = ss;
        saveAdditional(this.getUpdateTag());
        setChanged();
    }

    public void setTicksdelayed(int td) {
        this.ticksdelayedl = td;
        saveAdditional(this.getUpdateTag());
        setChanged();
    }

    @Override
    public void tick() {
        if(this.level == null || this.level.isClientSide())
            return;

        setChanged();

        this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt); // Call the superclass method to handle the default behavior.

        CompoundTag tag = pkt.getTag();
        this.ticksdelayedl = tag.getInt("ticks_pressed");
        this.signalstrenghl = tag.getInt("signal_strengh");
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}
