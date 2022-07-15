plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {

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

    implementation(Square.Retrofit2.retrofit)
    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)

    implementation(KotlinX.coroutines.android)
    implementation(JakeWharton.timber)

    implementation(AndroidX.dataStore.preferences)

    testImplementation(Testing.junit4)
    testImplementation(Testing.MockK)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.test.ext.truth)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)

    implementation(project(":domain"))

}