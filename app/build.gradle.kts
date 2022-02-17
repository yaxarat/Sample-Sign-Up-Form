plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dev.atajan.signupform"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            postprocessing {
                isRemoveUnusedCode = true
                isRemoveUnusedResources = true
                isObfuscate = false
                isOptimizeCode = true
            }
        }
        debug { }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc02"
    }
    packagingOptions { // https://stackoverflow.com/a/47509465/8685398
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

kotlin.sourceSets.all {
    languageSettings.apply {
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn("androidx.compose.ui.ExperimentalComposeUiApi")
        optIn("androidx.compose.foundation.ExperimentalFoundationApi")
    }
}

dependencies {
    implementation(Compose.activityCompose)
    implementation(Compose.material)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Core.coreKtx)
    implementation(Lifecycle.lifecycleRuntimeKtx)

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(Compose.uiTestJunit4)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)
}