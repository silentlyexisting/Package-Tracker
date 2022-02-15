start:
	gradle bootRun

clean:
	./gradlew clean

build:
	./gradlew clean build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

check-updates:
	./gradlew dependencyUpdates

make api-doc:
	gradle clean generateOpenApiDocs
