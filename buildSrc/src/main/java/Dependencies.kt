object AppConfig {
    const val compileSdk = 32
    const val applicationId = "dev.atajan.signupform"
    const val minSdk = 28
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
}

object AppCompat {
    private const val appCompatVersion = "1.4.1"

    const val appCompact = "androidx.appcompat:appcompat:$appCompatVersion"
}

object Build {
    const val androidBuildTools = "com.android.tools.build:gradle:7.0.4"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
    const val sqldelightGradlePlugin = "com.squareup.sqldelight:gradle-plugin:${SqlDelight.sqlDelightVersion}"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"
}

object Core {
    private const val coreVersion = "1.7.0"

    const val coreKtx = "androidx.core:core-ktx:$coreVersion"
}

object Compose {
    private const val activityVersion = "1.4.0"
    private const val composeVersion = "1.1.0"

    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val activityCompose = "androidx.activity:activity-compose:$activityVersion"

    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
    const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
}

object Hilt {
    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltVersion = "2.40.5"

    const val android = "com.google.dagger:hilt-android:$hiltVersion"
    const val compiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:$hiltVersion"
}

object Kotlinx {
    private const val coroutinesCoreVersion = "1.6.0"

    const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion"
}

object Lifecycle {
    private const val lifecycleVersion = "2.4.1"

    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
}

object Navigation {
    private const val navigationVersion = "2.4.1"
    const val navigationCompose = "androidx.navigation:navigation-compose:$navigationVersion"
}

object Result {
    private const val resultVersion = "1.1.14"

    const val kotlinResult = "com.michael-bull.kotlin-result:kotlin-result:$resultVersion"
}

object SqlDelight {
    const val sqlDelightVersion = "1.5.3"

    const val runtime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
    const val androidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"

    const val plugin = "com.squareup.sqldelight"
}