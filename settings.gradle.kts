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
        google() {
            content { excludeGroupByRegex("com\\.github.zeeeeej.*") }
        }
        mavenCentral() {
            content { excludeGroupByRegex("com\\.github.zeeeeej.*") }
        }

        maven("https://jitpack.io") {
            content {
                includeGroup("com.github.zeeeeej") // https://docs.jitpack.io/
            }
        }
    }
}

rootProject.name = "ComposeKit"
include(":app")
include(":common")
