package zzzank.mods.kube_jei.impl;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.resources.ResourceLocation;

/**
 * @author ZZZank
 */
@Desugar
public record RecipeType<T>(ResourceLocation uid, Class<T> type) {

	public static <T> RecipeType<T> create(ResourceLocation id, Class<T> type) {
		return new RecipeType<>(id, type);
	}
}
