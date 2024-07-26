dependencies {
    implementation(project(":core"))
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("com.flipkart.zjsonpatch:zjsonpatch:0.4.16")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}