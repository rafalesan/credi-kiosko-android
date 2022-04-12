plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

apply(from = "android.gradle")

android {

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {

    implementation(project(":presentation"))

}