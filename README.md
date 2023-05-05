 # Code Test
 [![Platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
 [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
 [![Gradle Version](https://img.shields.io/badge/gradle-4.0-green.svg)](https://docs.gradle.org/current/release-notes)

 A Sample Music App written in Kotlin using Android Architecture Components, MVVM, etc.
 
 # Instruction

## Gradle


plugins {
    id 'com.android.application' version '7.2.1' apply false
    id 'com.android.library' version '7.2.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.0' apply false
    id 'org.jetbrains.kotlin.jvm' version '1.8.0' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}


dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.5.0'
    implementation "androidx.compose.ui:ui:1.2.1"
    implementation "androidx.compose.material:material:1.2.1"
    implementation "androidx.compose.ui:ui-tooling-preview:1.2.1"
    implementation 'com.google.android.material:material:1.1.0'
    implementation "com.squareup.retrofit2:retrofit:2.6.1"
    implementation "com.squareup.retrofit2:converter-gson:2.6.1"
    implementation "com.squareup.retrofit2:converter-scalars:2.6.1"
    implementation "com.squareup.okhttp3:logging-interceptor:3.12.1"
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.2.0'
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    //ViewModels delegation extensions for activity
    implementation "androidx.activity:activity-ktx:1.4.0"
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    androidTestImplementation 'junit:junit:4.12'

    // Annotation processor
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.2.0")

    implementation 'androidx.activity:activity-compose:1.5.1'
    testImplementation 'junit:junit:4.13.2'
    // Testing dependencies
    testImplementation 'com.squareup.okhttp3:mockwebserver:4.10.0'
    // Google truth for assertion
    androidTestImplementation "androidx.arch.core:core-testing:2.1.0"
    androidTestImplementation "androidx.test.espresso:espresso-contrib:3.5.1"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
    testImplementation "com.google.truth:truth:1.1.3"
    testImplementation 'io.mockk:mockk:1.13.4'
    testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    testImplementation "androidx.arch.core:core-testing:2.2.0"
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'
    androidTestImplementation 'androidx.test:core:1.1.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation "com.android.support:support-annotations:27.1.1"
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test:rules:1.0.2'
    debugImplementation "androidx.compose.ui:ui-test-manifest:1.2.1"
    androidTestImplementation "androidx.compose.ui:ui-test:1.2.1"
    androidTestImplementation"androidx.compose.ui:ui-test-junit4:1.2.1"


}

## Notes
  App will request music festival api data. If the api data is empty string and failure, it will show popup error dialog with try again messgae and too many request message. If user click the ok , again app will request api call and if it success, the Music Festival data showing in the list. Based the band record lable list, below it will display list of all bands under their record label management.And below that it will display which festival they have attended. It has sorted alphabetically and its shows even if record label empty. 
  
 

### Application Architecture
---
- IDE: Android Studio
- API: Festival API
- Arthitecture: MVVM
- Programming Language: Kotlin
- Third Party Libraries: Retrofit, Gson,Mock
