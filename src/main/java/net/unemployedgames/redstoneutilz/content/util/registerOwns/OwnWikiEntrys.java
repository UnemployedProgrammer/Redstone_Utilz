package net.unemployedgames.redstoneutilz.content.util.registerOwns;

import net.minecraft.world.item.Items;
import net.unemployedgames.redstoneutilz.content.block.ModBlocks;
import net.unemployedgames.redstoneutilz.infrastructure.registry.RedstoneUtilsDefferedRegister;
import net.unemployedgames.redstoneutilz.infrastructure.registry.RedstoneUtilsRegistryObject;
import net.unemployedgames.redstoneutilz.infrastructure.registry.registry_objects.WikiEntry;

public class OwnWikiEntrys {
    public static final RedstoneUtilsDefferedRegister reg = new RedstoneUtilsDefferedRegister();
    private WikiEntry w = new WikiEntry("Copycat Configurable Button", "DESI!!!!!", ModBlocks.COPYCAT_BUTTON.get());
    public RedstoneUtilsRegistryObject CUSTOM_SIGNAL_STRENGH_BUTTON = reg.register("copycat_configurable_button", new RedstoneUtilsRegistryObject<WikiEntry>(w));
    public RedstoneUtilsRegistryObject CUSTOM_SIGNAL_STRENGH_PRESSUREPLATE = reg.register("copycat_configurable_pressure_plate", new RedstoneUtilsRegistryObject<WikiEntry>(new WikiEntry("Pressure Plate", "D", Items.ACACIA_PLANKS)));
}
