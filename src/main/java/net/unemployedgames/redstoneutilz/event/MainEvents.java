package net.unemployedgames.redstoneutilz.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.networking.PkgHandler;

@Mod.EventBusSubscriber(modid = RedstoneMod.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MainEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PkgHandler.register();
        });
    }
}
