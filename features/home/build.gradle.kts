import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.rafalesan.credikiosko.home"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
}

dependencies {
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(Google.android.material)

    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.lifecycle.viewModelKtx)

    implementation(AndroidX.hilt.navigationCompose)
    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)

    implementation(JakeWharton.timber)

    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    implementation(AndroidX.compose.ui.toolingPreview)
    implementation(AndroidX.compose.ui.tooling)
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.material3)
    implementation(AndroidX.compose.material.icons.extended)
    implementation(AndroidX.constraintLayout.compose)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)

    implementation(project(":core"))
}