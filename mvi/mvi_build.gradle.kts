plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Versions.androidCompileSdk)

    defaultConfig {
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidTargetSdk)
        versionCode = Versions.appVersionCode
        versionName = Versions.appVersionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        isAbortOnError = false
        isWarningsAsErrors = true
    }
}

dependencies {
    kotlin()
    android()
    test()
}

fun DependencyHandlerScope.kotlin() =
    with(ProjectDependencies) {
        implementation(kotlinStdlib)
        implementation(kotlinReflect)
        implementation(kotlinCoroutines)
    }

fun DependencyHandlerScope.android() =
    with(ProjectDependencies) {
        implementation(viewModelKtx)
    }

fun DependencyHandlerScope.test() =
    with(TestDependencies) {
        testImplementation(junitJupiterApi)
        testRuntimeOnly(junitJupiterEngine)
        testImplementation(googleTruth)
        testImplementation(mockito)
        testImplementation(mockitoKotlin)
    }
