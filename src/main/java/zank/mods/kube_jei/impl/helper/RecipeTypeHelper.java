package zank.mods.kube_jei.impl.helper;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * @author ZZZank
 */
public record RecipeTypeHelper(IJeiHelpers jeiHelpers) {

    public RecipeType<?> create(ResourceLocation id, Class<?> typeClass) {
        return new RecipeType<>(id, typeClass);
    }

    public RecipeType<?> find(ResourceLocation id) {
        return jeiHelpers.getRecipeType(id).orElse(null);
    }

    public Optional<RecipeType<?>> findOptional(ResourceLocation id) {
        return jeiHelpers.getRecipeType(id);
    }

    public RecipeType<?> find(ResourceLocation id, Class<?> typeClass) {
        return jeiHelpers.getRecipeType(id, typeClass).orElse(null);
    }

    public <T> Optional<RecipeType<T>> findOptional(ResourceLocation id, Class<T> typeClass) {
        return jeiHelpers.getRecipeType(id, typeClass);
    }
}
