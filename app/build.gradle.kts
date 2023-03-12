@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("multiplatform")
}

var projectVersion = "0.3.2"

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "meteor.mobile"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        disable.add("ExpiredTargetSdkVersion")
    }
    buildToolsVersion = "33.0.2"

    packagingOptions {
        resources.excludes.add("META-INF/versions/9/previous-compilation-data.bin")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

kotlin {
    android()
}

repositories {
    maven { url = uri("https://raw.githubusercontent.com/MeteorLite/hosting/main/repo/") }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":api-rs"))
    implementation(project(":annotations"))
    implementation(project(":awt"))
    implementation(project(":eventbus"))
    implementation(files("./libs/injected-client.jar"))

    implementation("org.bouncycastle:bcprov-jdk15on:1.64")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.8.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.jaredrummler:android-device-names:2.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}