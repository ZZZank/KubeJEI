package zzzank.mods.kube_jei.mod_bridge;

import net.minecraftforge.fml.ModList;

/**
 * @author ZZZank
 */
public interface ModState {

    boolean PROBE_JS = loaded("probejs");

    static boolean loaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}
