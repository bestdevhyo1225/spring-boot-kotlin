import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
	/* JPA Proxy 객체를 만들기 위해서 반드시 적용해야 함 */
	kotlin("plugin.allopen") version "1.3.72"
	/* [ querydsl 설정을 위한 추가 ]
	 * Java Compiler가 Annotation Processing을 실행하는 과정에서 Kotlin 코드를 인지할 수 없어서
	 * JPA Entity class의 쿼리 타입을 생성할 수 없다. 따라서 Kotlin 코드의 Annotation Process을 지원하는 kapt 플로그인을 적용한다.
	 * */
	kotlin("kapt") version "1.3.72"
}

// JPA Proxy 객체를 만들기 위해서 반드시 적용해야 함 - 상속 가능하도록 Open 처리
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

group = "com.hyoseok"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6")

	/* querydsl 추가 시작 */
	implementation("com.querydsl:querydsl-jpa")
	kapt("com.querydsl:querydsl-apt")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
	/* querydsl 추가 끝 */

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

/* querydsl 추가 */
sourceSets["main"].withConvention(KotlinSourceSet::class){
	kotlin.srcDir("$buildDir/generated/querydsl")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
