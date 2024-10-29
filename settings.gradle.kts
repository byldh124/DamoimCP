pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        //maven { url = uri("https://naver.jfrog.io/artifactory/maven/") }
        maven {url = uri("https://repository.map.naver.com/archive/maven")}
    }
    /*versionCatalogs {
        create("libs") {
            from(files("gradle/comm.versions.toml"))
        }
    }*/
}

rootProject.name = "DamoimCP"
include(":app")
include(":data")
include(":domain")
include(":common")
