plugins {
    id("openely.platform-conventions")
}

dependencies {
    compileOnly(libs.waterfall.api)

    implementation(libs.bstats.bungeecord)
    implementation(libs.cloud.bungeecord)
    implementation(libs.adventure.platform.bungeecord)

    // Libraries
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.gson)
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
}