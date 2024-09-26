
# KubeJEI 1.2.0 -> 1.3.0

render helper

- render helper
    - with this, you can access text rendering without worrying about stupid Rhino getting confused about parameter types
    - it also provides an entity rendering preset, where you can easily render an entity on your JEI page
- FluidStackJS will be wrapped into Forge FluidStack instead of Arch FluidStack to fix JEI not recognizing this ingredient
- auto-wrapping for IDrawable is removed due to ambiguities caused by it

---


# KubeJEI 1.1.0 -> 1.2.0

recipe manager plugin

- many members are now exposed as direct field access instead of getters
- custom recipe manager plugin
- better fallback for IDrawable wrapping

---

# KubeJEI 1.0.0 -> 1.1.0

drawable wrapper++

- adds proxy methods for every known IDrawable creation methods, with a shorter name and clearer parameter name
- adds builder style category registering in category registry event

---

# KubeJEI 1.0.0

it just works

- 11 events covering every features a JEI plugin can do\
- wrapper for JEI IDrawables
- custom helper for easing the registration of custom JEI Category/Recipe/RecipeType