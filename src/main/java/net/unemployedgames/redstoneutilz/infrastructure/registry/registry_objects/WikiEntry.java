package net.unemployedgames.redstoneutilz.infrastructure.registry.registry_objects;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WikiEntry {

    public static enum Type {
        BLOCK,ITEM;
    }

    private String title;
    private String description;
    private Item isFor_I;
    private Block isFor_B;

    public WikiEntry(String title, String description, Item isFor) {
        this.title = title;
        this.description = description;
        this.isFor_I = isFor;
    }
    public WikiEntry(String title, String description, Block isFor) {
        this.title = title;
        this.description = description;
        this.isFor_B = isFor;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public Type getType() {
        if (this.isFor_I != null) {
            return Type.ITEM;
        } else {
            return Type.BLOCK;
        }
    }

    public Block getIsFor_Block() {
        return isFor_B;
    }

    public Item getIsFor_Item() {
        return isFor_I;
    }
}
