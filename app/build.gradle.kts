plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}
android {
    namespace = "com.example.travelticker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.travelticker"
        minSdk = 24
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
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.viewpager2)
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-database")
    implementation ("com.google.firebase:firebase-storage:20.2.0")
    implementation ("com.google.firebase:firebase-firestore:24.2.0")
    implementation ("com.google.android.gms:play-services-maps:17.0.0")

    implementation ("com.github.bumptech.glide:glide:4.15.1") // Replace with the latest version
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1") // For Glide's annotation processor

}