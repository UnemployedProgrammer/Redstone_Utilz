package net.unemployedgames.redstoneutilz.content.networking.pkgs;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.unemployedgames.redstoneutilz.content.block.entities.ConfigurableCustomButtonEntity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SSetNbtCopyCatButtonPck {
    private final int ticks;
    private final int power;
    private final BlockPos entity;

    public SSetNbtCopyCatButtonPck(int ticks, int power, BlockPos entity) {
        this.power = power;
        this.ticks = ticks;
        this.entity = entity;
    }

    public SSetNbtCopyCatButtonPck(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.ticks);
        buf.writeInt(this.power);
        buf.writeBlockPos(this.entity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var sucess = new AtomicBoolean(false);

        context.get().enqueueWork(() -> {
            BlockEntity bentity = context.get().getSender().level().getBlockEntity(entity);

            if (bentity instanceof ConfigurableCustomButtonEntity configurableCustomButton) {
                configurableCustomButton.setSignalstrengh(this.power);
                configurableCustomButton.setTicksdelayed(this.ticks);
                sucess.set(true);
            }
        });
        context.get().setPacketHandled(true);
        return sucess.get();
    }
}
