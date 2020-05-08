buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(PluginDependencies.android)
        classpath(PluginDependencies.kotlin)
        classpath(PluginDependencies.junit5)
    }
}

apply(from = "gradle/scripts/detekt.gradle.kts")

subprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
