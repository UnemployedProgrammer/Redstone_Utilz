package net.unemployedgames.redstoneutilz.infrastructure.registry;

import com.mojang.logging.LogUtils;
import net.minecraftforge.registries.RegistryObject;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.HashMap;

public class RedstoneUtilsDefferedRegister {

    HashMap<String, RedstoneUtilsRegistryObject> objs = new HashMap<String, RedstoneUtilsRegistryObject>();

    /**
     *
     * DEPRECATED:
     * The Name cannot be 'Ye7Fp44P2PMDh26HWpgmpZvGwo1OXamyVQVPEqhkXURzWjQlX6'
     * 'Ye7Fp44P2PMDh26HWpgmpZvGwo1OXamyVQVPEqhkXURzWjQlX6' is the emptyName
     *
     * NEW: The name can be everything!
     */
    public RedstoneUtilsRegistryObject register(String name, RedstoneUtilsRegistryObject objectToRegister) {
        if (objs.get(name) != null)
            LogUtils.getLogger().error("Element was already registered. | Details: name-"+name+";/*stn*/deffered_reg-redstoneutils");

        //String[] nA = Arrays.copyOf(objs_names, objs_names.length + 1);
        //nA[objs_names.length] = name;
        //objs_names = nA;

        objs.put(name, objectToRegister);
        return objectToRegister;
    }

    public RedstoneUtilsRegistryObject getRegistryObject(String name) {
        return objs.get(name);
    }

    //public String[] getObjsKey() {return objs_names;}

    public HashMap<String, RedstoneUtilsRegistryObject> getElements() {
        return objs;
    }
    @Deprecated
    public Boolean isValid(String key) {
        return "Ye7Fp44P2PMDh26HWpgmpZvGwo1OXamyVQVPEqhkXURzWjQlX6" == key;
    }
}
