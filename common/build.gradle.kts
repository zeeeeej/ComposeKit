import org.jetbrains.kotlin.cli.jvm.main

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

group = "com.github.zeeeeej"
version = "0.0.6"

android {
    namespace = "com.zeeeeej.compose.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}

afterEvaluate {
    publishing {
        publications {
//            create("release", MavenPublication::class) {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.zeeeeej"
                artifactId = "common" // 与lib module名称一样
                version = "0.0.6"
            }
        }
    }
}

val sourceFiles = android.sourceSets.getByName("main").java.getSourceFiles()

tasks.register<Javadoc>("withJavadoc") {
    isFailOnError = false
    dependsOn(tasks.named("compileDebugSources"), tasks.named("compileReleaseSources"))

    // add Android runtime classpath
    android.bootClasspath.forEach { classpath += project.fileTree(it) }

    // add classpath for all dependencies
    android.libraryVariants.forEach { variant ->
        variant.javaCompileProvider.get().classpath.files.forEach { file ->
            classpath += project.fileTree(file)
        }
    }

    source = sourceFiles
}

tasks.register<Jar>("withJavadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.named("withJavadoc"))
    val destination = tasks.named<Javadoc>("withJavadoc").get().destinationDir
    from(destination)
}

tasks.register<Jar>("withSourcesJar") {
    archiveClassifier.set("sources")
    from(sourceFiles)
}