package net.unemployedgames.redstoneutilz.content.world;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.world.gen.ores.GenerateZincOre;

@Mod.EventBusSubscriber
public class InitFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, RedstoneMod.Mod_ID);
    //public static final RegistryObject<Feature<?>> ORE_ZINC = REGISTRY.register("re", GenerateZincOre::new);
}

