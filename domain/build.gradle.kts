plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    implementation ("com.google.dagger:dagger:2.51")
    implementation("com.google.code.gson:gson:2.10.1")
}