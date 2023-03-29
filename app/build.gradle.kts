@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("multiplatform")
}

var projectVersion = "0.3.2"
val compose_version = "1.4.3"

android {
    namespace = "meteor"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    compileSdk = 33
    defaultConfig {
        applicationId = "meteor.mobile"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "33.0.2"

    packagingOptions {
        resources.excludes.add("META-INF/versions/9/previous-compilation-data.bin")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf(
                "-Xjvm-default=all")
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
    implementation(project(":http"))
    implementation(project(":logger"))
    runtimeOnly(project(":scripts"))
    implementation(files("./libs/injected-client.jar"))
    implementation("androidx.compose.material3:material3:1.1.0-alpha08")

    implementation("com.godaddy.android.colorpicker:compose-color-picker-jvm:0.7.0")
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "5.0.0-alpha.11")
    implementation("org.bouncycastle:bcpkix-jdk15to18:1.64")
    implementation("org.bouncycastle:bcprov-jdk15to18:1.64")
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.12.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-android")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.8.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.jaredrummler:android-device-names:2.1.1")
    implementation("br.com.devsrsouza.compose.icons.android:octicons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.android:tabler-icons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.android:line-awesome:1.0.0")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
