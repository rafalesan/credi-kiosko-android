// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("com.android.application") apply false
    id ("com.android.library") apply false
    id ("org.jetbrains.kotlin.android") apply false
    id("com.google.dagger.hilt.android") apply false
    id("com.google.devtools.ksp") apply false
    id("com.google.gms.google-services")apply false
    id("com.google.firebase.crashlytics") apply false
}
//https://www.droidcon.com/2022/03/11/reducing-gradle-boilerplate-in-multi-module-android-projects/
fun BaseExtension.baseConfig() {

    compileSdkVersion(findProperty("compileSdkVersion").toString().toInt())

    defaultConfig.apply {
        minSdk = findProperty("minSdkVersion").toString().toInt()
        targetSdk = findProperty("targetSdkVersion").toString().toInt()
        testInstrumentationRunner = findProperty("testInstrumentationRunner").toString()
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

/**
 * Apply configuration settings that are shared across all modules.
 */
fun PluginContainer.applyBaseConfig(project: Project) {
    whenPluginAdded {

        if (this is com.android.build.gradle.LibraryPlugin) {
            project.extensions
                    .getByType<com.android.build.gradle.LibraryExtension>()
                    .apply {
                        baseConfig()
                    }
        }
    }

}

subprojects {
    project.plugins.applyBaseConfig(project)
}

tasks.register("clean", Delete::class){
    delete(project.layout.buildDirectory)
}
