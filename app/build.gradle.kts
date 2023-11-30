plugins {
    id("com.android.application")
    //id("com.google.secrets_gradle_plugin") version("0.6") // agregar al proyecto el secrets plugin
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.frael.projectfindyourfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.frael.projectfindyourfood"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.libraries.places:places:3.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

secrets {
    // To add your Google Maps Platform API key to this project:
    // 1. Create or open file local.properties in this folder, which will be read by default
    //    by secrets_gradle_plugin
    // 2. Add this line, replacing YOUR_API_KEY with a key from a project with Places API enabled:
    //        PLACES_API_KEY=YOUR_API_KEY
    // 3. Add this line, replacing YOUR_API_KEY with a key from a project with Maps SDK for Android
    //    enabled (can be the same project and key as in Step 2):
    //        MAPS_API_KEY=YOUR_API_KEY
    defaultPropertiesFileName = "local.properties"
}