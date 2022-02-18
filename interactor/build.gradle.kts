apply {
    from("$rootDir/common-java-library-build.gradle")
}

dependencies {
    "api"(Result.kotlinResult)

    "implementation"(project(Modules.common))
    "implementation"(project(Modules.dataSourceLive))
    "implementation"(project(Modules.domain))
    "implementation"(Kotlinx.kotlinxCoroutinesCore)

    "testImplementation"(project(Modules.dataSourceTest))
}