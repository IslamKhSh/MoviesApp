import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlinx.serialization.get().pluginId)
}

android {
    namespace = "com.yassir.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yassir.movies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // you must add your key in local.properties file `API_KEY=......`
        val apiKey = "${gradleLocalProperties(rootDir)["API_KEY"]}"

        // init this field in defaultConfig as it doesn't change from build type to another
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "BASE_URL", "\"https://developers.themoviedb.org/3/\"")
        buildConfigField("String", "BASE_IMG_URL", "\"https://image.tmdb.org/t/p/w500/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_7
        targetCompatibility = JavaVersion.VERSION_1_7
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures.compose = true
}

dependencies {

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.bundles.ktor)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.bundles.archComponents)
    implementation(libs.androidx.core.ktx)

    implementation(libs.coil.compose)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.bundles.unitTest)
    testImplementation(platform(libs.junit))
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}