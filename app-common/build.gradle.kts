@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val compose_version = "1.4.3"

android {
    namespace = "meteor.app.common"
    compileSdk = 33
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    mavenCentral()
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
    compileOnly(files("./libs/injected-client.jar"))
    implementation("androidx.compose.material3:material3:1.1.0-beta02")

    implementation("com.godaddy.android.colorpicker:compose-color-picker-jvm:0.7.0")
    compileOnly(group = "com.squareup.okhttp3", name = "okhttp", version = "4.9.1")
    implementation("org.bouncycastle:bcpkix-jdk15to18:1.64")
    implementation("org.bouncycastle:bcprov-jdk15to18:1.64")
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.12.0")
    implementation("androidx.activity:activity-compose:1.7.0")
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
    implementation(group = "org.projectlombok", name = "lombok", version = "1.18.26")
}