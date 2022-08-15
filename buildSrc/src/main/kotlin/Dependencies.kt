package org.michaelbel.moviemade

@Deprecated("")
object Dependencies {
    private const val GooglePlayServicesVersion = "4.3.10"
    private const val CoreVersion = "1.7.0"
    private const val CoreSplashScreenVersion = "1.0.0-beta01"
    private const val LifecycleVersion = "2.4.0"
    private const val PagingComposeVersion = "1.0.0-alpha14"
    private const val RoomVersion = "2.4.1"
    private const val StartupVersion = "1.1.0"
    private const val AccompanistVersion = "0.20.3"
    private const val PlayCoreVersion = "1.8.1"
    private const val GmsAdsVersion = "20.5.0"
    private const val FirebaseAnalyticsVersion = "20.0.2"
    private const val FirebaseAppDistributionPluginVersion = "3.0.1"
    private const val FirebaseConfigVersion = "21.0.1"
    private const val FirebaseCoreVersion = "20.0.2"
    private const val FirebaseCrashlyticsVersion = "18.2.6"
    private const val FirebaseCrashlyticsPluginVersion = "2.8.1"
    private const val TimberVersion = "5.0.1"
    private const val RetrofitVersion = "2.9.0"
    private const val RetrofitConverterSerializationVersion = "0.8.0"

    const val GooglePlayServicesPlugin = "com.google.gms:google-services:$GooglePlayServicesVersion"
    const val Core = "androidx.core:core-ktx:$CoreVersion"
    const val CoreSplashScreen = "androidx.core:core-splashscreen:$CoreSplashScreenVersion"
    const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$LifecycleVersion"
    const val PagingCompose = "androidx.paging:paging-compose:$PagingComposeVersion"
    const val Room = "androidx.room:room-ktx:$RoomVersion"
    const val RoomCompiler = "androidx.room:room-compiler:$RoomVersion"
    const val Startup = "androidx.startup:startup-runtime:$StartupVersion"
    const val AccompanistInsets = "com.google.accompanist:accompanist-insets:$AccompanistVersion"
    const val AccompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:$AccompanistVersion"
    const val AccompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$AccompanistVersion"
    const val PlayCore = "com.google.android.play:core-ktx:$PlayCoreVersion"
    const val GmsAds = "com.google.android.gms:play-services-ads:$GmsAdsVersion"
    const val FirebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:$FirebaseAnalyticsVersion"
    const val FirebaseAppDistributionPlugin = "com.google.firebase:firebase-appdistribution-gradle:$FirebaseAppDistributionPluginVersion"
    const val FirebaseConfig = "com.google.firebase:firebase-config-ktx:$FirebaseConfigVersion"
    const val FirebaseCore = "com.google.firebase:firebase-core:$FirebaseCoreVersion"
    const val FirebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:$FirebaseCrashlyticsVersion"
    const val FirebaseCrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:$FirebaseCrashlyticsPluginVersion"
    const val Timber = "com.jakewharton.timber:timber:$TimberVersion"
    const val Retrofit = "com.squareup.retrofit2:retrofit:$RetrofitVersion"
    const val RetrofitConverterSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$RetrofitConverterSerializationVersion"
}