apply {
    from("$rootDir/common-java-library-build.gradle")
}

dependencies {
    "implementation"(Result.kotlinResult)
    "implementation"(project(Modules.dataSourceLive))
    "implementation"(project(Modules.domain))
}