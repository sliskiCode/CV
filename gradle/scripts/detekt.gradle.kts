import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin

buildscript {
    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(PluginDependencies.detekt)
    }
}

repositories {
    jcenter()
}

apply<DetektPlugin>()

tasks.named("detekt", Detekt::class.java).configure {
    setSource(files(rootProject.projectDir))

    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")

    parallel = true

    autoCorrect = true
    buildUponDefaultConfig = true
    config.setFrom(files("${rootProject.projectDir}/gradle/scripts/detekt.yml"))

    reports {
        xml {
            enabled = true
            destination = file("build/reports/detekt/detekt.xml")
        }
        html {
            enabled = true
        }
    }
}

dependencies {
    "detektPlugins"(ProjectDependencies.detektFormatting)
}
