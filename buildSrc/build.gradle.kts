plugins {
    `kotlin-dsl`
}

dependencies() {
    implementation("com.github.johnrengelman:shadow:${libs.plugins.shadow.get().version}")
}