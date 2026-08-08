# KubeJS 1.9.1 -> 2.0.0

Completely rewritten for Minecraft 1.21.1

- Everything in a `JEIPlugin` redirected to KubeJS via events
- Custom JEI category
- Events for "denying" categories and recipes. By filtering them early, instead of hiding them after everything is loaded, almost all related computations can be eliminated.
- Helpers for creating compound `IDrawables`, entity rendering, and custom tick timer
- `/kube_jei reload` command shortcut for reloading KubeJS & JEI

---

