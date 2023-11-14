package net.unemployedgames.redstoneutilz.infrastructure.registry;

import com.mojang.logging.LogUtils;
import net.unemployedgames.redstoneutilz.content.util.registerOwns.OwnWikiEntrys;
import org.slf4j.Logger;

import java.util.*;

public class FinalRegisterStorage {
    HashMap<String, RedstoneUtilsDefferedRegister> MOD_REGISTER_FINAL = new HashMap<String, RedstoneUtilsDefferedRegister>();

    public void init(RedstoneUtilsDefferedRegister register) {
        String uniqueID = UUID.randomUUID().toString();
        MOD_REGISTER_FINAL.put(uniqueID, register);
    }

    private static RedstoneUtilsDefferedRegister[] getAddElem(RedstoneUtilsDefferedRegister[] array, RedstoneUtilsDefferedRegister neuesElement) {
        RedstoneUtilsDefferedRegister[] nA = Arrays.copyOf(array, array.length + 1);
        nA[array.length] = neuesElement;
        return nA;
    }

    @Deprecated
    public Boolean isValid(String key) {
        return "Ye7Fp44P2PMDh26HWpgmpZvGwo1OXamyVQVPEqhkXURzWjQlX6" == key;
    }

    private void announceLoadedElementsSYS(Map mp, Logger logger) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            logger.info("Found Custom Mod Registry Group | UUID:  " + pair.getKey());
            Iterator it_ = mp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair_ = (Map.Entry)it.next();
                logger.info("Found Custom Mod Registry Object ||| " + pair_.getKey() + " | " + pair_.getValue());
                it_.remove(); // avoids a ConcurrentModificationException
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public void announceLoadedElements(Logger logger) {
        announceLoadedElementsSYS(MOD_REGISTER_FINAL, logger);
    }
}
