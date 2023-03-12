@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

repositories {
    mavenCentral()
    maven { url = uri("https://raw.githubusercontent.com/MeteorLite/hosting/main/repo/") }
}

android {
    namespace = "meteor.deobfuscator"
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
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.26")

    implementation(project(":annotations"))
    implementation(project(":api"))
    implementation(project(":api-rs"))
    implementation(project(":cache"))
    implementation(project(":logger"))

    implementation(group = "org.jetbrains", name = "annotations", version = "24.0.0")
    implementation(group = "org.ow2.asm", name = "asm", version = "9.4")
    implementation(group = "org.ow2.asm", name = "asm-util", version = "9.4")
    implementation(files("./libs/fernflower-07082019.jar"))
    implementation(group = "com.google.code.gson", name = "gson", version = "2.10.1")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-android")
    implementation(group = "org.projectlombok", name = "lombok", version = "1.18.26")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}