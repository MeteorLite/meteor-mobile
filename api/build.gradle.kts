@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.api"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}


dependencies {
    implementation(project(":annotations"))
    implementation(project(":awt"))
    implementation(project(":logger"))
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-android")
    implementation(group = "org.apache.commons", name = "commons-text", version = "1.10.0")
    implementation(group = "com.google.code.findbugs", name = "jsr305", version = "3.0.2")
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}