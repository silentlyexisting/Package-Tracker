start:
	gradle bootRun

clean:
	./gradlew clean

build:
	./gradlew clean build

check-updates:
	./gradlew dependencyUpdates

make api-doc:
	gradle clean generateOpenApiDocs