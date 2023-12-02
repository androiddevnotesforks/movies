pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "movies"

include(
    ":app",
    ":instant",
    ":benchmark",

    ":core:analytics",
    ":core:common",
    ":core:interactor",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:persistence",
    ":core:repository",
    ":core:ui",
    ":core:work",

    ":feature:account",
    ":feature:account-impl",
    ":feature:auth",
    ":feature:auth-impl",
    ":feature:details",
    ":feature:details-impl",
    ":feature:feed",
    ":feature:feed-impl",
    ":feature:gallery",
    ":feature:gallery-impl",
    ":feature:search",
    ":feature:search-impl",
    ":feature:settings",
    ":feature:settings-impl"
)