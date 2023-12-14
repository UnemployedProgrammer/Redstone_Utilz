package net.unemployedgames.redstoneutilz;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.config.AllConfigs;
import net.unemployedgames.redstoneutilz.content.item.ModCreativeModTabs;
import net.unemployedgames.redstoneutilz.content.item.ModItems;
import net.unemployedgames.redstoneutilz.content.util.registerOwns.OwnWikiEntrys;
import net.unemployedgames.redstoneutilz.infrastructure.SettingsSystem;
import net.unemployedgames.redstoneutilz.infrastructure.registry.FinalRegisterStorage;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RedstoneMod.Mod_ID)
public class RedstoneMod
{
    // Define mod id in a common place for everything to reference
    public static final String Mod_ID = "redstoneutilz";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public RedstoneMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        //FinalRegisterStorage MOD_COMPLETLYOWNTYPES_REGISTER = new FinalRegisterStorage();


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        RegisterBlockEntities.BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(modEventBus);
        AllConfigs.register(modLoadingContext);
        //MOD_COMPLETLYOWNTYPES_REGISTER.init(OwnWikiEntrys.reg);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        //MOD_COMPLETLYOWNTYPES_REGISTER.announceLoadedElements(LOGGER);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPYCAT_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPYCAT_BUTTON.get(), RenderType.translucent());

            SettingsSystem.createSettings(); //CREATE SETTINGS FILE(IF NOT THERE)
        }
    }
}
