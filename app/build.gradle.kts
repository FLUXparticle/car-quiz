import com.example.flavors.versionsFlavor

buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("com.example:flavors:1.0-SNAPSHOT")
    }
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.carquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.carquiz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions += listOf("cluster", "brand")
    productFlavors {
        create("daimler") {
            dimension = "cluster"
        }
        create("bmwGroup") {
            dimension = "cluster"
        }

        create("bmw") {
            dimension = "brand"
        }
        create("mercedes") {
            dimension = "brand"
        }
    }

    versionsFlavor()

    val allowedCombinations = mapOf(
        "volkswagen" to listOf("vw", "audi", "bentley", "bugatti", "porsche", "seat", "skoda"),
        "daimler" to listOf("maybach", "mercedes", "smart"),
        "bmwGroup" to listOf("bmw", "mini", "rollsRoyce"),
    )

    androidComponents {
        beforeVariants { variantBuilder ->
            // To check for a certain build type, use variantBuilder.buildType == "<buildType>"
            val dimensions = variantBuilder.productFlavors.associate { it }

            val cluster = dimensions["cluster"]
            val brand = dimensions["brand"]
            
            val allowedBrands = allowedCombinations[cluster]

            variantBuilder.enable = allowedBrands?.contains(brand) ?: false
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(project(":ads"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
