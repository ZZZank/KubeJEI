package zzzank.mods.kube_jei;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author ZZZank
 */
@Mod(KubeJEI.MOD_ID)
public class KubeJEI {
    public static final String MOD_ID = "kube_jei";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public KubeJEI() {
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
