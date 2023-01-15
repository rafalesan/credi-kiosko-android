plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.rafalesan.credikiosko.core"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                          "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(Square.Retrofit2.retrofit)
    implementation(Square.OkHttp3.okHttp)
    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)

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

    implementation(Koin.android)

    implementation(JakeWharton.timber)

    testImplementation(Testing.junit4)
    testImplementation(Testing.MockK)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.test.ext.truth)
    testImplementation(Square.OkHttp3.mockWebServer)
    testImplementation(Square.Retrofit2.converter.moshi)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)
}