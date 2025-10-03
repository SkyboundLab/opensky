rootProject.name = "OpenEly"

include(
    "shared",
    "bungee",
    "velocity",
    "sponge4",
    "sponge8"
)

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://simonsator.de/repo")
        maven("https://jitpack.io")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}