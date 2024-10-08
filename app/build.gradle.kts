plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.dishdash_foodplanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dishdash_foodplanner"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit and Gradle
    implementation (libs.retrofit)
    implementation (libs.gson)
    implementation (libs.converter.gson)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    // Room
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    // SnackBar
    implementation (libs.material.v140)
    // Lottie
    implementation (libs.lottie)
    // Gson for Meal type conversion
    implementation (libs.gson.v288)

    implementation(libs.cardview)
}