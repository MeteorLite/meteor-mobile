@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.mixins"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

dependencies {
    compileOnly(project(":annotations"))
    compileOnly(project(":api-rs"))
    compileOnly(project(":awt"))
    compileOnly(project(":eventbus"))
    compileOnly(project(":osrs"))
    compileOnly(project(":api"))
    compileOnly(project(":logger"))
    compileOnly(project(":cache"))

    compileOnly(group = "com.google.guava", name = "guava", version = "31.1-android")
    compileOnly(group = "javax.inject", name = "javax.inject", version = "1")
    compileOnly(group = "commons-io", name = "commons-io", version = "2.11.0")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}