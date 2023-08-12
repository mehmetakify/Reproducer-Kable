import org.jetbrains.compose.compose

group = "com.produce"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.mppCompose
}

kotlin {
    android ()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_19.majorVersion
            kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
    }
    jvmToolchain(19)
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose("org.jetbrains.compose.ui:ui-tooling"))
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.7.2")
                implementation("com.google.accompanist:accompanist-permissions:0.28.0")
                implementation("androidx.compose.material:material:1.4.3")
            }
        }
    }
}
android {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "19"
        }
    }
    kotlin { jvmToolchain(19) }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    compileSdk = Versions.compile_sdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs(
        "src/commonMain/resources",
        "src/androidMain/resources"
    )
    defaultConfig {
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    namespace = "com.produce.project.composables"
}
dependencies {
    implementation("androidx.compose.ui:ui-unit:1.4.3")
    implementation("androidx.compose.ui:ui-text:1.4.3")
}
