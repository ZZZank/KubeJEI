package zank.mods.kube_jei.impl.helper;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.resources.Identifier;

import java.util.Optional;

/**
 * @author ZZZank
 */
public record RecipeTypeHelper(IJeiHelpers jeiHelpers) {

    public IRecipeType<?> create(Identifier id, Class<?> typeClass) {
        return IRecipeType.create(id, typeClass);
    }

    public IRecipeType<?> find(Identifier id) {
        return jeiHelpers.getRecipeType(id).orElse(null);
    }

    public Optional<IRecipeType<?>> findOptional(Identifier id) {
        return jeiHelpers.getRecipeType(id);
    }

    public IRecipeType<?> find(Identifier id, Class<?> typeClass) {
        return jeiHelpers.getRecipeType(id, typeClass).orElse(null);
    }

    public <T> Optional<IRecipeType<T>> findOptional(Identifier id, Class<T> typeClass) {
        return jeiHelpers.getRecipeType(id, typeClass);
    }
}
