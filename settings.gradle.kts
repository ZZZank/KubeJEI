pluginManagement {
    // when using additional gradle plugins like shadow,
    // add their repositories to this list!
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.firstdark.dev/releases") {
            // ModPublisher
        }
        gradlePluginPortal()
    }
}
