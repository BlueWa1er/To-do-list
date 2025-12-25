plugins {
    alias(libs.plugins.android.application)   // если используешь версии из libs.versions.toml
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Если не используешь alias, можно заменить на:
    // id("com.android.application")
    // id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0") // Для иконок

    // Анимации в Compose
    implementation("androidx.compose.animation:animation:1.6.0")

    // Для использования Foundation (если требуется)
    implementation("androidx.compose.foundation:foundation:1.6.0")
}
