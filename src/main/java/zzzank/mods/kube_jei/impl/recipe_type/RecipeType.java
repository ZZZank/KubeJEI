package zzzank.mods.kube_jei.impl.recipe_type;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.resources.ResourceLocation;

/**
 * @author ZZZank
 */
@Desugar
public record RecipeType<T>(ResourceLocation uid, Class<T> type) {
}
