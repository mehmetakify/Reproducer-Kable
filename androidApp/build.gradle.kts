group = "com.produce.project"
version = "1.0"

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version Versions.mppCompose
}

dependencies {
    implementation(project(":composables"))
    implementation(project(":shared"))
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.0")
    implementation("androidx.lifecycle:lifecycle-process:2.6.1")
    implementation("androidx.compose.material:material:1.4.3")
    implementation(compose.runtime)
}

kotlin {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
}

android {
    configurations.all {
        resolutionStrategy {
            force("com.guardsquare:proguard-gradle:${Versions.proguard}")
        }
    }
    signingConfigs {
        create("release") {
            keyAlias = "key"
            keyPassword = "asdasdqwe*"
            storeFile = file("../../produce.jks")
            storePassword = "asdasdqwe*"
        }
    }
    compileSdk = Versions.compile_sdk
    defaultConfig {
        applicationId = "com.produce.project"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations.addAll(listOf("en"))
        signingConfig = signingConfigs.getByName("release")
    }
    kotlinOptions {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
        jvmTarget = JavaVersion.VERSION_19.majorVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.produce.project"
}