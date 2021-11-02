import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin(KotlinPlugins.multiplatform)
    kotlin(KotlinPlugins.cocoapods)
    id(Plugins.androidLibrary)
    id(Plugins.sqlDelight)
}

// CocoaPods requires the podspec to have a version
version = Application.versionName

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Bisq"
        homepage = "https://github.com/bisq-network/bisq-client"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
        podfile = project.file("../iosApp/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Kotlinx.datetime)
                implementation(Kotlinx.coroutines)
                implementation(SQLDelight.runtime)
                implementation(Kotlin.reflect)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(SQLDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(SQLDelight.nativeDriver)
            }
        }
        val iosTest by getting {
            dependencies {
            }
        }
    }
}

android {
    compileSdk = Application.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    database("NotificationDatabase") {
        packageName = "bisq.client.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}
