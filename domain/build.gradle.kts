plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}


android {
    compileSdk = findProperty("compileSdkVersion").toString().toInt()

    defaultConfig {
        minSdk = findProperty("minSdkVersion").toString().toInt()
        targetSdk = findProperty("targetSdkVersion").toString().toInt()

        testInstrumentationRunner = findProperty("testInstrumentationRunner").toString()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junitKtx)
    androidTestImplementation(AndroidX.test.espresso.core)

}