package zzzank.mods.kube_jei;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import static zzzank.mods.kube_jei.KubeJEI.MOD_ID;

/**
 * @author ZZZank
 */
@Mod(MOD_ID)
public class KubeJEI {
    public static final String MOD_ID = "kube_jei";

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
