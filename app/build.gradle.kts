plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId("com.weesnerdevelopment.serialcabinet")
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        debug {
            //applicationIdSuffix = ".debug"
        }
        release {
            minifyEnabled(false)
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
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
        kotlinCompilerVersion = "1.4.0"
    }
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree("libs") { include("*.aar", "*.jar") })

    implementation("androidx.core:core-ktx:1.5.0-alpha05")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.compose.compiler:compiler:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.compose.material:material:${Versions.compose}")
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")
    implementation("com.google.dagger:hilt-android:2.28-alpha")
    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.7.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation("com.github.Inkapplications.kimchi:kimchi:1.0.2")
    implementation("com.google.firebase:firebase-analytics-ktx:18.0.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:17.3.0")
    implementation("com.google.firebase:firebase-perf:19.0.10")

    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha02")
    kapt("com.google.dagger:hilt-android-compiler:2.28-alpha")

    testImplementation("junit:junit:4.13.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
