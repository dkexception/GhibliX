import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.skie)
//    alias(libs.plugins.androidLint)
}

kotlin {

    jvmToolchain(24)

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "io.github.dkexception.ghiblix.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            export(libs.androidx.lifecycle.viewmodel)
            baseName = "SharedKit"
        }
    }

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {

        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api(libs.kotlinx.collectionsImmutable)

                implementation(libs.skie.annotations)

                api(libs.androidx.lifecycle.viewmodel)

                api(libs.koin.core)
                api(libs.koin.compose.viewmodel)

                api(projects.api)
                api(projects.storage)
                api(projects.images)
            }
        }
    }
}

skie {
    features {
        // https://skie.touchlab.co/features/flows-in-swiftui
        enableSwiftUIObservingPreview = true
        enableFlowCombineConvertorPreview = true
    }

    analytics {
        enabled.set(false)
    }
}
