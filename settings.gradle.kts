rootProject.name = "CV"

include(
    "app",
    "mvi"
)

// Will rename every module's build.gradle file to use its name instead of `build`.
// E.g. `app/build.gradle` will become `app/app_build.gradle`
// The root build.gradle file remains untouched
rootProject.children.forEach { project ->
    project.buildFileName = "${project.name}_build.gradle.kts"
}
