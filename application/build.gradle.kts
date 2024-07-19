apply {
    plugin("org.springframework.boot")
}
dependencies {
    implementation(project(":core"))
    implementation(project(":mariadb"))
    implementation(project(":spring-web"))

    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.16")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    testImplementation("io.reactivex.rxjava2:rxjava:2.1.14")
    testImplementation("org.junit.platform:junit-platform-suite-engine")
    testImplementation("io.cucumber:cucumber-java:7.15.0")
    testImplementation("io.cucumber:cucumber-spring:7.15.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.15.0")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mariadb")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}