import java.net.InetAddress
import java.net.NetworkInterface

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "android.gradle")

fun getIp(): String {
    var result: InetAddress? = null
    val interfaces = NetworkInterface.getNetworkInterfaces()
    while(interfaces.hasMoreElements()) {
        val addresses = interfaces.nextElement().inetAddresses
        while(addresses.hasMoreElements()) {
            val address = addresses.nextElement()
            if(!address.isLoopbackAddress) {
                if(address.isSiteLocalAddress) {
                    return address.hostAddress
                } else if(result == null) {
                    result = address
                }
            }
        }
    }
    return (result ?: run { InetAddress.getLocalHost() } ).hostAddress
}

@Suppress("UnstableApiUsage")
android {

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    flavorDimensions.add("default")

    productFlavors {
        create("dev") {
            dimension = "default"
            applicationIdSuffix = ".dev"
            buildConfigField("String", "API_BASE_URL", "\"http://${getIp()}/api/\"")
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

    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(Google.android.material)

    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.lifecycle.viewModelKtx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)

    implementation(AndroidX.dataStore.preferences)

    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)

    implementation(project(":core"))
    implementation(project(":features:auth"))
    implementation(project(":presentation"))

}