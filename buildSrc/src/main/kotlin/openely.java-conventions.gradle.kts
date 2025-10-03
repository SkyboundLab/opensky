plugins {
    `java-library`
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}