@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.injector"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    compileOnly("org.projectlombok:lombok:1.18.26")

    implementation(project(":annotations"))
    implementation(project(":deobfuscator"))
    implementation(project(":api-rs"))
    implementation(project(":logger"))
    implementation(project(":cache"))
    implementation(project(":mixins"))
    implementation(project(":awt"))
    implementation("javax.inject:javax.inject:1")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.10.1")

    implementation(group = "org.ow2.asm", name = "asm", version = "9.4")
    implementation(group = "org.ow2.asm", name = "asm-util", version = "9.4")
    implementation(group = "org.jetbrains", name = "annotations", version = "24.0.0")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-jre")
    implementation(group = "net.sf.jopt-simple", name = "jopt-simple", version = "6.0-alpha-3")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}