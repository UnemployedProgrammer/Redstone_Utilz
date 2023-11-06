package net.unemployedgames.redstoneutilz.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.core.BlockPos;
import net.unemployedgames.redstoneutilz.gui.block.CopyCatButtonConfigurationScreen;

public class ClientHooks {
    public static void openCopyCatButtonConfigurationScreen(BlockPos pos) {
        //Minecraft.getInstance().setScreen(new CopyCatButtonConfigurationScreen(pos));
        Minecraft.getInstance().setScreen(new CopyCatButtonConfigurationScreen(pos));
    }
}
