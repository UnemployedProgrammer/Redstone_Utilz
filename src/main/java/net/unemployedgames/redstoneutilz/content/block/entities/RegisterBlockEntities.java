package net.unemployedgames.redstoneutilz.content.block.entities;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import net.unemployedgames.redstoneutilz.content.block.entities.configurable_button.ConfigurableCustomButtonEntity;
import net.unemployedgames.redstoneutilz.content.block.entities.display.DisplayBlockEntity;
import net.unemployedgames.redstoneutilz.content.block.entities.placer.PlacerBlockEntity;
import net.unemployedgames.redstoneutilz.content.block.entities.renamer.RenamerBlockEntity;

public class RegisterBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RedstoneMod.Mod_ID);

    public static final RegistryObject<BlockEntityType<ConfigurableCustomButtonEntity>> CONFIGURABLE_CUSTOM_BUTTON_ENTITY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("copycat_configurable_custom_button",
            () -> BlockEntityType.Builder.of(ConfigurableCustomButtonEntity::new, ModBlocks.COPYCAT_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PlacerBlockEntity>> PLACER_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("placer_block",
            () -> BlockEntityType.Builder.of(PlacerBlockEntity::new, Blocks.STONE).build(null));

    public static final RegistryObject<BlockEntityType<DisplayBlockEntity>> DISPLAY_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("display_block",
            () -> BlockEntityType.Builder.of(DisplayBlockEntity::new, Blocks.NETHERITE_BLOCK).build(null));

    public static final RegistryObject<BlockEntityType<RenamerBlockEntity>> RENAMER_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("renamer_block",
            () -> BlockEntityType.Builder.of(RenamerBlockEntity::new, Blocks.STONE).build(null));
}
