pluginManagement {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        // See https://jmfayard.github.io/refreshVersions
        id("de.fayard.refreshVersions") version "0.60.5"
    }
}

plugins {
    id("de.fayard.refreshVersions")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CrediKiosko"
include(":app")
include(":core")
include(":features:auth")
include(":features:home")
include(":features:products")
include(":features:onboarding")
include(":features:customers")
include(":features:credits")
