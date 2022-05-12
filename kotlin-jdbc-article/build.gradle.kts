plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("mysql:mysql-connector-java:6.0.3")
    implementation ("com.zaxxer:HikariCP:5.0.0")
}