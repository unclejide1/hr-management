build:
	docker build -t hr/hr-builder:latest --cache-from hr/hr-builder:latest . -f Dockerfile-builder

ddev:
	docker-compose  -f docker-compose.yml up --build

dev:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

test:
	./mvnw test -Dspring.profiles.active=test