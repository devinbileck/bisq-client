import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.mppCompose
}

kotlin {
    js(IR) {
        browser {
            useCommonJs()
            binaries.executable()
        }
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.web.widgets)
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(npm("copy-webpack-plugin", "9.0.1"))
                implementation(npm("react", "17.0.2"))
                implementation(npm("react-dom", "17.0.2"))
                implementation(npm("@material-ui/core", "4.12.3"))
                implementation(npm("@material-ui/icons", "4.11.2"))
            }
        }
    }
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().versions.webpackCli.version = "4.9.0"
}

// Temporary workaround for a bug in jsRun invocation
// See https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
    }
}
