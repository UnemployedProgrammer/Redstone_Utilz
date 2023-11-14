package net.unemployedgames.redstoneutilz.content.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.unemployedgames.redstoneutilz.content.gui.block.CopyCatButtonConfigurationScreen;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.MainHomePage;

public class ClientHooks {
    public static void openCopyCatButtonConfigurationScreen(BlockPos pos) {
        //Minecraft.getInstance().setScreen(new CopyCatButtonConfigurationScreen(pos));
        Minecraft.getInstance().setScreen(new CopyCatButtonConfigurationScreen(pos));
    }

    public static void openHomePageScreen() {
        //Minecraft.getInstance().setScreen(new CopyCatButtonConfigurationScreen(pos));
        Minecraft.getInstance().setScreen(new MainHomePage(true));
    }
}
