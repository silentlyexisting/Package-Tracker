start:
	gradle bootRun

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

check-updates:
	./gradlew dependencyUpdates

make api-doc:
	gradle clean generateOpenApiDocs