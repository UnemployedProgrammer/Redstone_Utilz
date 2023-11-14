package net.unemployedgames.redstoneutilz.content.block.entities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;

public class RegisterBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RedstoneMod.Mod_ID);

    public static final RegistryObject<BlockEntityType<ConfigurableCustomButtonEntity>> CONFIGURABLE_CUSTOM_BUTTON_ENTITY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("copycat_configurable_custom_button",
            () -> BlockEntityType.Builder.of(ConfigurableCustomButtonEntity::new, ModBlocks.COPYCAT_BLOCK.get()).build(null));
}
