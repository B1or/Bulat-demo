plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tech.droi.bulat_demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.droi.bulat_demo"
        minSdk = 26
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 27
        versionCode = 5
        versionName = "1.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0-alpha05")
    implementation("androidx.compose.ui:ui:1.6.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("androidx.camera:camera-core:1.4.0-alpha04")
    implementation ("androidx.camera:camera-camera2:1.4.0-alpha04")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha04")
    implementation ("androidx.camera:camera-video:1.4.0-alpha04")
    implementation ("androidx.camera:camera-view:1.4.0-alpha04")
    implementation ("androidx.camera:camera-extensions:1.4.0-alpha04")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}