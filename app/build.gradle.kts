plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.commoneydjdjmemo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.commoneydjdjmemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //GSON
    implementation("com.google.code.gson:gson:2.13.2")
    // XML 解析工具 (必須加上 exclude，否則 Android 會直接閃退！)
    implementation("com.subshell.simpleframework:simple-xml:2.9.0") {
        exclude(group = "stax", module = "stax-api")
        exclude(group = "stax", module = "stax")
        exclude(group = "xpp3", module = "xpp3")
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}