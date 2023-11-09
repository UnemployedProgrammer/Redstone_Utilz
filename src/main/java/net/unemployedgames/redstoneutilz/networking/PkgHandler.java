package net.unemployedgames.redstoneutilz.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.networking.pkgs.SSetNbtCopyCatButtonPck;

public class PkgHandler {

    public static final String protocollVersion = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(RedstoneMod.Mod_ID, "syncscreencopycatbutton"), () ->protocollVersion, protocollVersion::equals, protocollVersion::equals
    );

    public static void register() {
        INSTANCE.messageBuilder(SSetNbtCopyCatButtonPck.class, 1, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SSetNbtCopyCatButtonPck::encode)
                .decoder(SSetNbtCopyCatButtonPck::new)
                .consumerMainThread(SSetNbtCopyCatButtonPck::handle)
                .add();
    }
}