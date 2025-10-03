plugins {
    id("openely.java-conventions")
}

tasks.compileJava {
    options.release = 8
}

dependencies {
    compileOnly(libs.slf4j)
    implementation(libs.gson)
    implementation(libs.guava)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.minimessage) {
        exclude("net.kyori")
    }
}