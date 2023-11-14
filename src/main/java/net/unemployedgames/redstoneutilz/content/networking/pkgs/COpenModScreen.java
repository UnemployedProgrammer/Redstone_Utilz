package net.unemployedgames.redstoneutilz.content.networking.pkgs;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.MainHomePage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class COpenModScreen {
    private final int data;

    /**
     *  1:Home|2:...
     *  */
    public COpenModScreen(int data) {
        this.data = data;
    }

    /**
     *  1:Home|2:...
     *  */
    public COpenModScreen(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var success = new AtomicBoolean(false);

        context.get().enqueueWork(() -> {

            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> {
                success.set(true);
                Minecraft.getInstance().setScreen(new MainHomePage(true));
                return null;
            });
        });
        context.get().setPacketHandled(true);
        return success.get();
    }
}
