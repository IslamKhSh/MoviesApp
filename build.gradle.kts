import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.detekt)

    // just apply in the module level with id only
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

tasks.withType<Detekt> { jvmTarget = "1.8" }

// run detekt over all modules and get one report for all modules
val detektAll by tasks.registering(Detekt::class) {
    description = "Run Detekt for all projects"

    parallel = true
    autoCorrect = true
    setSource(rootDir)
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    config.setFrom(files("$rootDir/detekt-config.yml"))
    buildUponDefaultConfig = false
}
