plugins { id("com.android.application") }

android {
    namespace = "com.rjdigitalsolutions.splytto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.rjdigitalsolutions.splytto"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "1.2.0"
    }

    signingConfigs {
        create("release") {
            val storeFilePath = System.getenv("SPLYTTO_KEYSTORE")
            if (!storeFilePath.isNullOrBlank()) {
                storeFile = file(storeFilePath)
                storePassword = System.getenv("SPLYTTO_STORE_PASSWORD")
                keyAlias = System.getenv("SPLYTTO_KEY_ALIAS")
                keyPassword = System.getenv("SPLYTTO_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            if (!System.getenv("SPLYTTO_KEYSTORE").isNullOrBlank()) signingConfig = signingConfigs.getByName("release")
        }
    }
}


dependencies {
    implementation("com.google.mlkit:text-recognition:16.0.1")
}
