/*
 * Copyright VastGui 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.govast.plugin.version.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("cn.govast.plugin.version")
}

android {
    compileSdk = Version.compile_sdk_version

    defaultConfig {
        minSdk = Version.min_sdk_version
        targetSdk = Version.target_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = Version.java_version
        targetCompatibility = Version.java_version
    }

    kotlinOptions {
        jvmTarget = Version.java_version.toString()
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
    namespace = "cn.govast.starbar"
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "io.github.SakurajimaMaii"
                artifactId = "starbar"
                version = "0.0.6"
            }
        }
    }
}

dependencies {
    implementation(AndroidX.core_ktx)
    implementation(AndroidX.appcompat)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
    implementation(Google.material)
    testImplementation(Libraries.junit)
}