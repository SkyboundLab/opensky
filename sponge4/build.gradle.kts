plugins {
    id("openely.platform-conventions")
}

tasks.compileJava {
    options.release = 8
}

dependencies {
    // Sponge
    compileOnly(libs.sponge.api4)
    annotationProcessor(libs.sponge.api4)

    // Libraries
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.gson)
    implementation(libs.snakeyaml)
}

tasks {
    shadowJar {
        sequenceOf(
            "io.leangen",
            "net.kyori.adventure.text.minimessage",
            "space.arim",
            "org.yaml",
            "com.google.gson"
        ).forEach {
            relocate(it, "${project.group}.${rootProject.name.lowercase()}.dependency.$it")
        }
    }
}