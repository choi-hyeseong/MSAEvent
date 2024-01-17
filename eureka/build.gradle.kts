dependencies {
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:3.1.8")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }

}


