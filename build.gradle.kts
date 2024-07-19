plugins {
    jacoco
    id("application")
    java
    id("org.springframework.boot") version "3.2.3" apply false
    id("io.spring.dependency-management") version "1.1.4"
    id("org.sonarqube") version "5.0.0.4638"
}

group = "com.qa.blog"
version = "1.0.0"
description = "Kata"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

tasks.register<JacocoReport>("codeCoverageReport") {
    group = "Coverage"
    description = "Coverage report aggregation"
    // If a subproject applies the 'jacoco' plugin, add the result it to the report
    subprojects {
        val subproject = this
        subproject.plugins.withType<JacocoPlugin>().configureEach {
            subproject.tasks.matching({ it.extensions.findByType<JacocoTaskExtension>() != null }).configureEach {
                val testTask = this
                sourceSets(subproject.sourceSets.main.get())
                executionData(testTask)
            }

            // To automatically run `test` every time `./gradlew codeCoverageReport` is called,
            // you may want to set up a task dependency between them as shown below.
            // Note that this requires the `test` tasks to be resolved eagerly (see `forEach`) which
            // may have a negative effect on the configuration time of your build.
            subproject.tasks.matching({ it.extensions.findByType<JacocoTaskExtension>() != null }).forEach {
                rootProject.tasks["codeCoverageReport"].dependsOn(it)
            }
        }
    }

    // enable the different report types (html, xml, csv)
    reports {
        // xml is usually used to integrate code coverage with
        // other tools like SonarQube, Coveralls or Codecov
        xml.required = true

        // HTML reports can be used to see code coverage
        // without any external tools
        html.required = true
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("jacoco")
        plugin("application")
        plugin("java")

        plugin("io.spring.dependency-management")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.3")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("codeCoverageReport"))
}

sonar {
    properties {
        property("sonar.projectKey", "blog")
        property("sonar.projectName", "Blog")
        property("sonar.exclusions", "**/BlogApplication.java,**/ApplicationConfig.java,**/build.gradle.kts")
        property("sonar.coverage.exclusions", "**/ApplicationConfig.java,**/BlogApplication.java,**/build.gradle.kts")
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.projectDir}/build/reports/jacoco/codeCoverageReport/codeCoverageReport.xml")
        property ("sonar.gradle.skipCompile", "true")
    }
}
