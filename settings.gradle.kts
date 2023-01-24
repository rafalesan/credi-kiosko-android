pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    //https://stackoverflow.com/questions/64644810/get-gradle-property-from-settings
    val properties = File(rootDir, "versions.properties").inputStream().use {
        java.util.Properties().apply { load(it) }
    }
    val kotlinVersion = properties.getValue("version.kotlin") as String
    val androidGradleVersion = properties.getValue("plugin.android") as String
    val daggerHiltVersion = properties.getValue("version.google.dagger") as String

    plugins {
        id ("com.android.application") version androidGradleVersion apply false
        id ("com.android.library") version androidGradleVersion apply false
        id("org.jetbrains.kotlin.android") version kotlinVersion apply false
        id("com.google.dagger.hilt.android") version daggerHiltVersion apply false
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.51.0"
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
include(":presentation")
include(":core")
include(":features:auth")
include(":features:home")
