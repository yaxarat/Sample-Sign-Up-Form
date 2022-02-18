plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
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

        testInstrumentationRunner = "dev.atajan.signupform.flows.HiltTestRunner"
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
    implementation(Accompanist.systemuicontroller)
    implementation(Compose.activityCompose)
    implementation(Compose.material)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Core.coreKtx)
    implementation(Hilt.android)
    implementation(Hilt.hiltNavigationCompose)
    implementation(Lifecycle.lifecycleRuntimeKtx)
    implementation(Navigation.navigationCompose)
    implementation(SqlDelight.androidDriver)
    implementation(project(Modules.common))
    implementation(project(Modules.domain))
    implementation(project(Modules.interactor))

    kapt(Hilt.compiler)

    testImplementation(Junit.junit4)
    testImplementation(project(Modules.dataSourceTest))

    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(Compose.uiTestJunit4)
    androidTestImplementation(Hilt.hiltAndroidTesting)
    androidTestImplementation(Junit.junit4)
    androidTestImplementation(project(Modules.dataSourceTest))
    androidTestImplementation(project(Modules.dataSourceLive))

    kaptAndroidTest(Hilt.compiler)

    debugImplementation(Compose.uiTooling)
    debugImplementation(Compose.uiTestManifest)
}