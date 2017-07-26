# Bus Route Sample Service

About: creates two docker instances, one with MySQL, one with a Spring Boot (JPA) app, which serves its services via REST.

For execution, with docker try

	$ service.sh docker_build
	$ service.sh docker_run
	
in a different window, try the tests
	$ service.sh docker_smoke