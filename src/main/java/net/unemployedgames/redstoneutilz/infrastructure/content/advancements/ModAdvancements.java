package net.unemployedgames.redstoneutilz.infrastructure.content.advancements;

import net.minecraft.util.StringRepresentable;

public enum ModAdvancements implements StringRepresentable {
    BLOCK_ZINC_GET("block_zinc_get"),
    CONFIGURED_CUSTOM_BUTTON_CHANGE("configured_custom_button_change"),
    CONFIGURED_CUSTOM_BUTTON_CHANGE_WOOD("configured_custom_button_change_wood"),
    CONFIGURED_CUSTOM_BUTTON_GET("configured_custom_button_gte"),
    COPYCAT_BLOCK_GET("copycat_block_get"),
    DESTROYER_GET("destroyer_get"),
    INGOT_ZINC_GET("ingot_zinc_get"),
    PLACER_GET("placer_get")

    ;

    private String id;
    ModAdvancements(String id) {
        this.id = id;
    }
    @Override
    public String getSerializedName() {
        return null;
    }
    public String getId() {
        return "redstoneutilz:"+id;
    }
}
