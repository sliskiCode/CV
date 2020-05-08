plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
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
        applicationId = "com.slesarew.cv"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    testOptions {
        with(unitTests) {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    kotlin()
    android()
    project()
    library()
    test()
}

fun DependencyHandlerScope.kotlin() =
    with(ProjectDependencies) {
        implementation(kotlinStdlib)
    }

fun DependencyHandlerScope.android() = with(ProjectDependencies) {
    implementation(appcompat)
    implementation(recyclerview)
    implementation(viewModelKtx)
    implementation(constraintLayout)
    implementation(cardView)
}

fun DependencyHandlerScope.project() = implementation(project(":mvi"))

fun DependencyHandlerScope.library() =
    with(ProjectDependencies) {
        implementation(retrofit)
        implementation(retrofitConverterGson)
        implementation(glide)
        implementation(koin)
        implementation(groupie)
        implementation(groupieKotlin)
    }

fun DependencyHandlerScope.test() =
    with(TestDependencies) {
        testImplementation(junitJupiterApi)
        testRuntimeOnly(junitJupiterEngine)
        testRuntimeOnly(junitJupiterVintageEngine)
        testImplementation(junitJupiterParams)
        testImplementation(googleTruth)
        testImplementation(mockito)
        testImplementation(mockitoKotlin)
        testImplementation(coroutinesTest)
        testImplementation(androidTestRunner)
        testImplementation(androidTestCore)
        testImplementation(androidTestExtJUnit)
        testImplementation(androidTestExtTruth)
        testImplementation(junit4)
        testImplementation(robolectric)
    }
