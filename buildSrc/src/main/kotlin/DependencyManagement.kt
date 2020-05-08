object Versions {

    // Application
    const val appVersionCode = 1
    const val appVersionName = "1.0"

    // Framework
    const val androidCompileSdk = 29
    const val androidTargetSdk = 29
    const val androidMinSdk = 23

    // Environment
    const val androidGradlePluginVersion = "3.6.3"

    // Kotlin
    const val kotlinVersion = "1.3.61"
    const val kotlinCoroutinesVersion = "1.3.5"

    // Android
    const val appcompatVersion = "1.2.0-beta01"
    const val recyclerviewVersion = "1.2.0-alpha03"
    const val viewModelVersion = "2.2.0"
    const val constraintLayoutVersion = "2.0.0-beta4"
    const val cardViewVersion = "1.0.0"
    const val browserVersion = "1.2.0"

    // Library
    const val retrofitVersion = "2.6.0"
    const val glideVersion = "4.11.0"
    const val koinVersion = "2.1.5"
    const val groupieVersion = "2.8.0"

    // Util
    const val detektVersion = "1.8.0"

    // Test
    const val androidJunit5Version = "1.6.2.0"
    const val junit4Version = "4.13"
    const val junitJupiterVersion = "5.6.2"
    const val mockitoVersion = "2.2.0"
    const val mockitoInlineVersion = "3.3.3"
    const val coroutinesTestVersion = "1.3.5"
    const val robolectricVersion = "4.3.1"
    const val androidTestRunnerVersion = "1.2.0"
    const val androidTestCoreVersion = "1.2.0"
    const val androidTestExtVersion = "1.0.0"
    const val googleTruthVersion = "0.42"
}

object ProjectDependencies {

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesVersion}"

    // Android
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerviewVersion}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val browser = "androidx.browser:browser:${Versions.browserVersion}"

    // Library
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val koin = "org.koin:koin-android-viewmodel:${Versions.koinVersion}"
    const val groupie = "com.xwray:groupie:${Versions.groupieVersion}"
    const val groupieKotlin = "com.xwray:groupie-kotlin-android-extensions:${Versions.groupieVersion}"

    // Util
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detektVersion}"
}

object TestDependencies {

    const val junit4 = "junit:junit:${Versions.junit4Version}"
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiterVersion}"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiterVersion}"
    const val junitJupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiterVersion}"
    const val junitJupiterVintageEngine = "org.junit.vintage:junit-vintage-engine:${Versions.junitJupiterVersion}"
    const val googleTruth = "com.google.truth:truth:${Versions.googleTruthVersion}"
    const val mockito = "org.mockito:mockito-inline:${Versions.mockitoInlineVersion}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoVersion}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTestVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunnerVersion}"
    const val androidTestCore = "androidx.test:core-ktx:${Versions.androidTestCoreVersion}"
    const val androidTestExtJUnit = "androidx.test.ext:junit:${Versions.androidTestExtVersion}"
    const val androidTestExtTruth = "androidx.test.ext:truth:${Versions.androidTestExtVersion}"
}

object PluginDependencies {

    const val android = "com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektVersion}"
    const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
}
