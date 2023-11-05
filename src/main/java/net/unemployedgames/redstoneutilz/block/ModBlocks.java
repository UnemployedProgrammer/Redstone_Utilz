package net.unemployedgames.redstoneutilz.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.unemployedgames.redstoneutilz.RedstoneMod;

public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RedstoneMod.Mod_ID);



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
