
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