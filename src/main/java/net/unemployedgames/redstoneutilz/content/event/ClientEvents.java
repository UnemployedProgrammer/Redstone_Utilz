package net.unemployedgames.redstoneutilz.content.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.block.entities.display.DisplayBlockEntityRenderer;

@Mod.EventBusSubscriber(modid = RedstoneMod.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntities.DISPLAY_BLOCK_ENTITY.get(), DisplayBlockEntityRenderer::new);
    }

}
