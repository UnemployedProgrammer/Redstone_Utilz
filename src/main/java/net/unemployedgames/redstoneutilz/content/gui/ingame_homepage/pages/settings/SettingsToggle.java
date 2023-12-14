package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages.settings;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.unemployedgames.redstoneutilz.content.gui.AllIcons;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.SideBarButton;
import net.unemployedgames.redstoneutilz.infrastructure.SettingsSystem;
import net.unemployedgames.redstoneutilz.infrastructure.content.settings.AllSettings;
import net.unemployedgames.redstoneutilz.infrastructure.content.settings.SettingsHelper;

public class SettingsToggle extends SideBarButton {
    AllSettings setting;
    public SettingsToggle(int pX, int pY, int pWidth, int pHeight, AllSettings setting) {
        super(pX, pY, pWidth, pHeight, Component.translatable("settings.redstoneutilz."+setting.getId()), SettingsHelper.iconForCurrentSetting(setting), pButton -> SettingsHelper.toggleSetting(setting));
        this.setting = setting;
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        super.onClick(pMouseX, pMouseY);
        if(SettingsSystem.getBooleanSettings(SettingsSystem.getKey(setting.getId())))
            this.img = new SideBarImg(AllIcons.TRUE, 16, 16);
        else
            this.img = new SideBarImg(AllIcons.FALSE, 16, 16);
    }
}
