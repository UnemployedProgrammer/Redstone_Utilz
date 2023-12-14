package net.unemployedgames.redstoneutilz.content.block.entities.renamer;

import net.minecraftforge.fml.ModList;

public class RenamerBlockHelper {
    public static final boolean isCreateModLoaded() {
        return ModList.get().isLoaded("create");
    }
}
