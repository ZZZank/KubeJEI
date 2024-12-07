package zzzank.mods.kube_jei;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zzzank.mods.kube_jei.mod_bridge.ModState;
import zzzank.mods.kube_jei.mod_bridge.ProbeJSPlugin;
import zzzank.probejs.plugin.ProbeJSPlugins;

/**
 * @author ZZZank
 */
@Mod(KubeJEI.MOD_ID)
public class KubeJEI {
    public static final String MOD_ID = "kube_jei";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public KubeJEI() {
        if (ModState.PROBE_JS) {
            ProbeJSPlugins.register(new ProbeJSPlugin());
        }
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SuppressWarnings("unchecked")
    public static <T> T duck(Object o) {
        return (T) o;
    }
}
