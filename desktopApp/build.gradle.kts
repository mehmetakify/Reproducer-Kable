import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.mppCompose
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_19.majorVersion
        }
    }
    jvmToolchain(19)
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":composables"))
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.coroutines}")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "Produce"
            packageVersion = "1.0.0"
            outputBaseDir.set(project.buildDir.resolve("installers"))
            modules("java.compiler", "java.instrument", "jdk.unsupported", "java.naming")
            macOS {
                iconFile.set(project.file("src/jvmMain/resources/Logo.icns"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/Logo.ico"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/Logo.png"))
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from(project.file("proguard-rules.pro"))
        }
        buildTypes.release.proguard {
            obfuscate.set(true)
        }
    }
}