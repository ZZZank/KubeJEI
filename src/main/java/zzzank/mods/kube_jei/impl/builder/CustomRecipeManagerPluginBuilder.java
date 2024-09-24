package zzzank.mods.kube_jei.impl.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import zzzank.mods.kube_jei.impl.CustomRecipeManagerPlugin;

/**
 * @author ZZZank
 */
@Getter
@Setter
@Accessors(chain = true)
public class CustomRecipeManagerPluginBuilder {
    private CustomRecipeManagerPlugin.RecipeMatcher recipeMatcher;
    private CustomRecipeManagerPlugin.NoFocusRecipeMatcher noFocusRecipeMatcher;
    private CustomRecipeManagerPlugin.UidMatcher uidMatcher;
}
