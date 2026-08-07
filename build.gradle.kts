import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("dev.architectury.loom") version "1.17-SNAPSHOT"
    id("maven-publish")
}

fun prop(key: String): String = project.property(key).toString()

val modVersion = prop("mod_version")
val mavenGroup = prop("maven_group")
val minecraftVersion = prop("minecraft_version")
val neoForgeVersion = prop("neoForge_version")
val modId = prop("mod_id")
val modName = prop("mod_name")
val modAuthor = prop("mod_author")
val modDescription = prop("mod_description")
val license = prop("license")
val jeiVersion = prop("jei_version")

base {
    archivesName.set(prop("archives_base_name"))
}

version = modVersion
group = mavenGroup

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

loom {
    // use this if you are using the official mojang mappings
    // and want loom to stop warning you about their license
    silentMojangMappingsLicense()
}

repositories {
    exclusiveContent {
        forRepository {
            maven("https://cursemaven.com")
        }
        filter {
            includeGroup("curse.maven")
        }
    }
    maven("https://maven.neoforged.net/releases/") {
        name = "NeoForge"
    }
    maven("https://maven.architectury.dev") {
        content {
            includeGroup("me.shedaniel")
        }
    }
    maven("https://modmaven.dev") {
        // JEI
        name = "ModMaven"
    }
}

dependencies {
    // to change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraftVersion")

    // choose what mappings you want to use here
    // leave this uncommented if you want to use
    // mojang's official mappings, or feel free
    // to add your own mappings here (how about
    // mojmap layered with parchment, for example?)
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:$neoForgeVersion")

    modImplementation("curse.maven:kubejs-238086:8083208")
    modImplementation("curse.maven:rhino-416294:8218748")

    modImplementation("mezz.jei:jei-$minecraftVersion-neoforge:$jeiVersion")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.processResources {
    // define properties that can be used during resource processing
    inputs.property("version", version)

    // this will replace the property "\${version}" in your mods.toml
    // with the version you've defined in your gradle.properties
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(
            mapOf(
                "version" to version,
                "mod_license" to license,
                "mod_id" to modId,
                "mod_name" to modName,
                "mod_authors" to modAuthor,
                "mod_description" to modDescription
            )
        )
    }
}

tasks.withType<JavaCompile> {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

tasks.jar {
    // add some additional metadata to the jar manifest
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to modId,
                "Specification-Vendor" to modAuthor,
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to modAuthor,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
            )
        )
    }
}

// configure the maven publication
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
