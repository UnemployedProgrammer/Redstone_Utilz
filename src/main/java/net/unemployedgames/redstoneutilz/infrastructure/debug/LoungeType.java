package net.unemployedgames.redstoneutilz.infrastructure.debug;

import net.unemployedgames.redstoneutilz.RedstoneMod;

public enum LoungeType {
    MAIN("main"),
    UI("ui"),
    BLOCK("block"),
    ITEM("item"),
    MISC("misc"),
    CUSTOM("custom")
    ;


    private String id;
    private String translatableid;
    private LoungeType(String id) {
        this.id = id;
        this.translatableid = "debug.lounge.type." + RedstoneMod.Mod_ID + id;
    }

    public String getId() {
        return id;
    }
}
