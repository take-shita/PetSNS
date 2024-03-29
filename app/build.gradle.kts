import org.jetbrains.kotlin.cli.jvm.main

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.petsns"
    compileSdk = 34

//    sourceSets {
//        main {
//            res.Dirs = [
//                "src/main/res/layouts/Board",
//                "src/main/res/layouts/Contest",
//                "src/main/res/layouts",
//                "src/main/res"]
//        }
//    }

    defaultConfig {
        applicationId = "com.example.petsns"
        minSdk = 30
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
    buildFeatures {
        viewBinding = true
    }

}
dependencies {
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("org.jetbrains:annotations:15.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-firestore:24.10.0")

    implementation ("com.google.android.gms:play-services-maps:17.0.1")
    implementation ("com.google.android.gms:play-services-places:17.0.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
//    recycleviewの追記
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.google.maps:google-maps-services:0.15.0")
    implementation ("com.google.firebase:firebase-database:20.0.0")

}