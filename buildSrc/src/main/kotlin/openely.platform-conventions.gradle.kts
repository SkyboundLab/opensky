plugins {
    id("openely.java-conventions")
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":shared"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        minimize()

        destinationDirectory.set(file("${rootProject.rootDir}/dist"))
        archiveClassifier.set("")
        archiveBaseName.set("${rootProject.name}-${project.name.replaceFirstChar(Char::titlecase)}")
    }
}
