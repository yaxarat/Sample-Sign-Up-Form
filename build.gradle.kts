buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.sqldelightGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
    }
}