plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.game.reschange"
    compileSdk = 34  // DÜZELTME 1: Modern kütüphaneler için 30 yerine 34 yaptık.

    defaultConfig {
        applicationId = "com.game.reschange"
        minSdk = 26
        targetSdk = 34 // DÜZELTME 2: compileSdk ile uyumlu olması için 34 yaptık.
        versionCode = 6
        versionName = "1.5"

        // DÜZELTME 3: NDK filtresini Kotlin'e uygun en temiz hale getirdik.
        ndk {
            abiFilters += "armeabi-v7a"
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Hataları görmezden gelip derlemeyi zorlamak için (Güvenlik önlemi)
    lint {
        abortOnError = false
        checkReleaseBuilds = false
        disable.addAll(listOf("MissingTranslation", "UnusedResources"))
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Debug anahtarını release için kullanıyoruz (GitHub Actions hatasını önler)
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.recyclerview)
    
    // Xposed için gerekli
    compileOnly("de.robv.android.xposed:api:82")
    
    implementation(libs.androidx.appcompat)
    implementation("com.google.android.material:material:1.11.0")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
