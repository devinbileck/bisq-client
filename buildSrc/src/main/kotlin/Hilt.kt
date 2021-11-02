object Hilt {
    const val version = "2.39.1"
    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"

    private const val hiltNavigationVersion = "1.0.0"
    const val hiltNavigation = "androidx.hilt:hilt-navigation:$hiltNavigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0-alpha03"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val hiltLifecycleViewmodelVersion = "1.0.0-alpha03"
    const val hiltLifecycleViewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltLifecycleViewmodelVersion"
}