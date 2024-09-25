package zzzank.mods.kube_jei.impl.builder;

import lombok.Setter;
import lombok.experimental.Accessors;
import zzzank.mods.kube_jei.impl.CustomRecipeManagerPlugin;

/**
 * @author ZZZank
 */
@Setter
@Accessors(chain = true)
public class CustomRecipeManagerPluginBuilder {
    public CustomRecipeManagerPlugin.RecipeMatcher recipeMatcher;
    public CustomRecipeManagerPlugin.NoFocusRecipeMatcher noFocusRecipeMatcher;
    public CustomRecipeManagerPlugin.UidMatcher uidMatcher;
}
