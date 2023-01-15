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

    plugins {
        id ("com.android.application") version androidGradleVersion apply false
        id ("com.android.library") version androidGradleVersion apply false
        id("org.jetbrains.kotlin.android") version kotlinVersion apply false
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
include(":domain")
include(":data")
include(":core")
