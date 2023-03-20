plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.http"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

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
    implementation(project(":api"))
    implementation(project(":awt"))
    implementation(project(":logger"))

    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.26")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.10.1")
    implementation(group = "com.google.guava", name = "guava", version = "31.1-android")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation(group = "org.apache.commons", name = "commons-csv", version = "1.10.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}