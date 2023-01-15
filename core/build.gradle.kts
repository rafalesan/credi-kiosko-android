plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
}

dependencies {

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)
}