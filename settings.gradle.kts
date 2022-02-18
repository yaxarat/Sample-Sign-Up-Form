pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SignUpForm"
include(":app")
include(":common")
include(":common-ui")
include(":confirmation")
include(":data-source")
include(":domain")
include(":interactor")
include(":registration")
include(":data-source:live")
include(":data-source:test")
