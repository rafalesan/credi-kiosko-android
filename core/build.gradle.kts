import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.rafalesan.credikiosko.core"
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

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    coreLibraryDesugaring(Android.tools.desugarJdkLibs)

    implementation(Square.Retrofit2.retrofit)
    implementation(Square.OkHttp3.okHttp)
    implementation(Square.moshi)
    ksp(Square.moshi.kotlinCodegen)

    implementation(KotlinX.coroutines.android)
    implementation(JakeWharton.timber)

    implementation(AndroidX.dataStore.preferences)

    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(Google.android.material)

    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.lifecycle.viewModelKtx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)

    implementation(JakeWharton.timber)

    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.material3)
    implementation(AndroidX.compose.material.icons.extended)
    implementation(AndroidX.compose.ui.tooling)
    implementation(AndroidX.compose.ui.toolingPreview)
    implementation(AndroidX.lifecycle.runtime.compose)

    implementation(AndroidX.hilt.navigationCompose)

    implementation(AndroidX.core.splashscreen)

    implementation(AndroidX.room.runtime)
    implementation(AndroidX.room.ktx)
    implementation(AndroidX.room.paging)
    ksp(AndroidX.room.compiler)

    testImplementation(Testing.junit4)
    testImplementation(Testing.MockK)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.test.ext.truth)
    testImplementation(Square.OkHttp3.mockWebServer)
    testImplementation(Square.Retrofit2.converter.moshi)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)
}