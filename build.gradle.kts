// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.barfuin.gradle.taskinfo") version "2.1.0"
    id("com.example.greeting") version "1.0-SNAPSHOT"
}

greeting {
    name = "Sven"
}

val convert by tasks.registering(Convert::class) {
    group = "custom"
    xsltFile = file("rechnung.xsl")
    inputDir = file("xml")
    outputDir = file("fo")
}
