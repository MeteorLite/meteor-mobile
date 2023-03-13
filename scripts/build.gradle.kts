plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.scripts"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

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
    annotationProcessor(group = "org.eclipse.sisu", name = "org.eclipse.sisu.inject", version = "0.9.0.M1")

    compileOnly(group = "org.apache.maven.plugin-tools", name = "maven-plugin-annotations", version = "3.7.1")
    implementation(project(":cache"))
    implementation(project(":api"))
    implementation(project(":logger"))
    implementation("com.google.guava:guava:31.1-jre")
    //runtimeOnly(group = "org.apache.maven", name = "maven-plugin-api", version = "4.0.0-alpha-4")
    implementation("org.slf4j:slf4j-simple:2.0.6")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
}