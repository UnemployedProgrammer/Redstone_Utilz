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
    private int ticksdelayed;
    private int signalstrengh;
    public ConfigurableCustomButtonEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.CONFIGURABLE_CUSTOM_BUTTON_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);

        this.ticksdelayed = pTag.getInt("ticks_pressed");
        this.signalstrengh = pTag.getInt("signal_strengh");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putInt("ticks_pressed", this.ticksdelayed);
        pTag.putInt("signal_strengh", this.signalstrengh);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    public int getTicksdelayed() {
        return this.ticksdelayed;
    }
    public int getSignalstrengh() {
        return this.signalstrengh;
    }

    public void setSignalstrengh(int signalstrengh) {
        this.signalstrengh = signalstrengh;
        setChanged();
    }

    public void setTicksdelayed(int ticksdelayed) {
        this.ticksdelayed = ticksdelayed;
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

   // @Override
    //public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        //this.ticksdelayed = pkt.getTag().getInt("ticks_pressed");
        //this.signalstrengh = pkt.getTag().getInt("signal_strengh");

    //    handleUpdateTag(pkt.getTag());
    //     load(pkt.getTag());
    //}
}
