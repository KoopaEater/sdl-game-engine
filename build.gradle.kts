plugins {
    id("java")
}

group = "dk.maxkandersen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.github.libsdl4j:libsdl4j:2.28.4-1.6")
}

tasks.test {
    useJUnitPlatform()
}