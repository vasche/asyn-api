plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	java
	id("org.openapi.generator") version "5.4.0"
}



group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

sourceSets{
	main{
		java {
			srcDirs("$buildDir/generated/src/main/java")
		}
	}
}

configurations {
	compileOnly {
		extendsFrom (configurations.annotationProcessor.get())
	}
}

springBoot {
	mainClass.set("com.example.asynapi.AsynApiApplication")
}

repositories {
	mavenCentral()
}

dependencies {
				implementation("org.springframework.boot:spring-boot-starter-actuator")
				implementation("org.springframework.boot:spring-boot-starter-web")
				implementation("org.springdoc:springdoc-openapi-ui:1.6.5")
				implementation("org.openapitools:jackson-databind-nullable:0.2.2")
				compileOnly("org.projectlombok:lombok")
				annotationProcessor("org.projectlombok:lombok")
				testImplementation("org.springframework.boot:spring-boot-starter-test")
		}

openApiGenerate {
	verbose.set(false)
	generatorName.set("spring")
	inputSpec.set("$rootDir/src/main/resources/openapi.yaml")
	modelPackage.set("com.example.asynapi.model")
	apiPackage.set("com.example.asynapi.api")
	invokerPackage.set("com.example.asynapi.invoker")
	packageName.set("com.example.asynapi")
	typeMappings.put("OffsetDateTime", "LocalDateTime")
	importMappings.put("java.time.OffsetDateTime","java.time.LocalDateTime")

	outputDir.set("$buildDir/generated")
	generateApiDocumentation.set(false)
	generateApiTests.set(false)
	generateModelTests.set(false)
	generateModelDocumentation.set(false)

	configOptions.set(mapOf (
		"library" to "spring-boot",
		"documentationProvider" to "none"))
    globalProperties.set(mapOf(
		"modelDocs" to "false",
		"apiDocs" to "false",
		"models" to "",
		"modelDocs" to "false",
		"apis" to "false",
		"supportingFiles" to "false"))
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
