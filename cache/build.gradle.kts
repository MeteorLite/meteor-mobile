@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.cache"
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
    implementation(project(":awt"))
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.24")

    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.24")

    implementation(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    implementation(group = "com.google.guava", name = "guava", version = "30.1.1-jre")
    implementation(group = "commons-cli", name = "commons-cli", version = "1.4")
    implementation(group = "io.netty", name = "netty-buffer", version = "4.1.54.Final")
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.9.1")
    implementation(group = "org.antlr", name = "antlr4-runtime", version = "4.8-1")
    implementation(group = "org.apache.commons", name = "commons-compress", version = "1.21")
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.32")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}