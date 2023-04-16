@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.compose") version "1.3.1"
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "meteor.app.desktop"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

compose {
    kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.4")

    desktop {
        application {
            mainClass = "meteor.DesktopMain"
            nativeDistributions {
                version = "1.5.10"
                targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Exe, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
                includeAllModules = true
                windows {
                    console = true
                    upgradeUuid = "9df19035-e962-4bb4-90c0-74330a07082b"
                    iconFile.set(project.file("src/main/resources/Meteor.ico"))
                }
            }
            jvmArgs(
                    // This fixes a rare bug exclusive to windows when using AA in GPU or GPU HD
                    // I would like to separate jvm arguments by platform, but this is a limitation of Compose packaging
                    // If you have an issue with scaling, or performance on linux/macOs, remove these two lines
                    //"-Dsun.java2d.dpiaware=false,",
                    //"-Dsun.java2d.uiScale=1.0",

                    "-ea",
                    "-XX:+UseDynamicNumberOfGCThreads",
                    "-XX:+UseZGC",
                    "-Xmx2048m",
                    "--add-exports", "java.base/java.lang=ALL-UNNAMED",
                    "--add-opens", "java.base/java.net=ALL-UNNAMED",
                    "--add-exports", "java.desktop/sun.awt=ALL-UNNAMED",
                    "--add-exports", "java.desktop/sun.java2d=ALL-UNNAMED",
                    "--add-opens", "java.desktop/java.awt=ALL-UNNAMED",
                    "--add-opens", "java.desktop/java.awt.color=ALL-UNNAMED",
            )
        }
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

    implementation(compose.desktop.currentOs)
}