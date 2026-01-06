plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")

}

android {
    namespace = "com.example.moviesdbapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.moviesdbapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
//    kapt {
//        javacOptions {
//            // Increase the max count of errors from annotation processors.
//            // Default is 100.
//            option("-Xmaxerrs", 500)
//        }
//    }
}

dependencies {

    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    
    // Room Paging
    implementation("androidx.room:room-paging:2.6.1")

    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.navigation.compose)
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("io.coil-kt:coil-compose:2.0.0")
    implementation("io.coil-kt:coil-svg:2.0.0")
    implementation("io.coil-kt:coil:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")

    //Retrofit and GSON
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Server Sent Events
    implementation("com.squareup.okhttp3:okhttp-sse:4.12.0")
//    implementation(libs.androidx.startup.runtime)
//    implementation(libs.androidx.navigation.compose)
    testImplementation("com.squareup.okhttp3:okhttp-sse:4.12.0")
    //Koin DI
    implementation("io.insert-koin:koin-android:3.2.0")
    testImplementation("io.insert-koin:koin-test-junit4:3.2.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.paging:paging-compose:3.3.6")
    implementation("androidx.paging:paging-runtime:3.3.6")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}