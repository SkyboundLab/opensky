plugins {
    id("openely.platform-conventions")
    id("xyz.jpenilla.run-velocity") version "2.2.0"
}

dependencies {
    // Velocity
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)

    // Libraries
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.gson)
    implementation(libs.bstats.velocity)
    implementation(libs.snakeyaml)
    implementation(libs.cloud.velocity)
}

tasks {
    shadowJar {
        sequenceOf(
            "cloud.commandframework",
            "io.leangen",
            "net.kyori.adventure.text.minimessage",
            "space.arim",
            "org.yaml",
            "org.bstats",
            "com.google.gson"
        ).forEach {
            relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
        }
    }

    runVelocity {
        // Configure the Velocity version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        velocityVersion("3.3.0-SNAPSHOT")
        jvmArgs("-javaagent:MultiYggdrasil-snapshot.dirty.jar=ely.by", "-Dmultiyggdrasil.mojangYggdrasilService=enabled", "-Dmultiyggdrasil.noNamespaceSuffix")
      }
}