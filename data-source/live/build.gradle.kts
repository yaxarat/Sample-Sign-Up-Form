apply {
    from("$rootDir/common-java-library-build.gradle")
}

plugins {
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(Kotlinx.kotlinxCoroutinesCore)
    "implementation"(Result.kotlinResult)
    "implementation"(SqlDelight.runtime)
    "implementation"(project(Modules.common))
    "implementation"(project(Modules.domain))

    "testImplementation"(SqlDelight.testDriver)
}

sqldelight {
    database("AppDatabase") {
        packageName = "dev.atajan.live.database"
        sourceFolders = listOf("sqldelight")
    }
}