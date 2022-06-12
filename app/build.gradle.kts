import java.net.InetAddress
import java.net.NetworkInterface

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
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

    implementation(Koin.android)
    implementation(Square.Retrofit2.retrofit)
    implementation(Square.Retrofit2.converter.moshi)
    implementation(Square.moshi.adapters)
    kapt(Square.moshi.kotlinCodegen)
    implementation(Square.OkHttp3.loggingInterceptor)
    implementation(Chucker.library)
    implementation(JakeWharton.timber)

    implementation(AndroidX.dataStore.preferences)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit.ktx)
    androidTestImplementation(AndroidX.test.espresso.core)

    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

}