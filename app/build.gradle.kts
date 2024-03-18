import de.fayard.refreshVersions.core.versionFor
import java.io.FileInputStream
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

apply(from = "android.gradle")

fun getIp(): String {
    val ip4s = mutableListOf<String>()
    NetworkInterface.getNetworkInterfaces()
        .toList()
        .filter { it.isUp && !it.isLoopback && !it.isVirtual && !it.name.contains("bridge") }
        .forEach {
            it.inetAddresses
                .toList()
                .filter { inetAddress ->
                    !inetAddress.isLoopbackAddress && inetAddress is Inet4Address
                }
                .forEach { inetAddress ->
                    ip4s.add(inetAddress.hostAddress)
                }
        }
    return ip4s.first()
}


android {

    namespace = "com.rafalesan.credikiosko"

    val releaseKeystorePropertiesFile = rootProject.file("local.properties")
    val releaseKeystoreProperties = Properties()
    releaseKeystoreProperties.load(FileInputStream(releaseKeystorePropertiesFile))

    signingConfigs {
        create("release") {
            storeFile = file(releaseKeystoreProperties.getValue("storeFile"))
            storePassword = releaseKeystoreProperties.getValue("storePassword") as String
            keyAlias = releaseKeystoreProperties.getValue("keyAlias") as String
            keyPassword = releaseKeystoreProperties.getValue("keyPassword") as String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    flavorDimensions.add("default")

    productFlavors {
        create("dev") {
            dimension = "default"
            applicationIdSuffix = ".dev"
            buildConfigField("String", "API_BASE_URL", "\"http://${getIp()}/api/\"")
        }
        create("production") {
            dimension = "default"
            buildConfigField("String", "API_BASE_URL", "\"http://rafalesan.com/credikiosko/api/\"")
        }
    }

}

dependencies {

    implementation(Square.Retrofit2.retrofit)
    implementation(Square.Retrofit2.converter.moshi)
    implementation(Square.moshi.adapters)
    kapt(Square.moshi.kotlinCodegen)
    implementation(Square.OkHttp3.loggingInterceptor)
    implementation(Chucker.library)
    implementation(JakeWharton.timber)

    implementation(AndroidX.hilt.navigationCompose)
    implementation(AndroidX.activity.compose)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(Google.android.material)
    implementation(AndroidX.compose.material3)

    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.lifecycle.viewModelKtx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)

    implementation(AndroidX.navigation.compose)

    implementation(AndroidX.dataStore.preferences)

    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    implementation(AndroidX.core.splashscreen)

    implementation(AndroidX.compose.ui.toolingPreview)
    implementation(AndroidX.compose.ui.tooling)

    implementation(platform(Firebase.bom))
    implementation(Firebase.analytics)
    implementation(Firebase.crashlytics)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)

    implementation(project(":core"))
    implementation(project(":features:auth"))
    implementation(project(":features:onboarding"))
    implementation(project(":features:home"))
    implementation(project(":features:products"))
    implementation(project(":features:customers"))
    implementation(project(":features:credits"))

}