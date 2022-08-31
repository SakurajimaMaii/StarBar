import com.gcode.plugin.version.AndroidX
import com.gcode.plugin.version.Libraries
import com.gcode.plugin.version.Version

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("com.gcode.plugin.version")
}

android {
    compileSdk = Version.compile_sdk_version

    defaultConfig {
        applicationId = "com.gcode.starbarproj"
        minSdk = Version.min_sdk_version
        targetSdk = Version.target_sdk_version
        versionCode = Version.version_code
        versionName = Version.version_name
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(project(":StarBar"))
    implementation(Libraries.junit)
    implementation(Libraries.material)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.constraintlayout)
}
