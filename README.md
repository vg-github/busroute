# Bus Route Sample Service

Stack: Spring Boot, JPA, REST, Docker, MySQL (Dockerised)

## How to run
Install:
 * Docker
 * clone this repo into a folder

To Run:
    $ cd /your/repo/clone/path/
	$ service.sh docker_build
	$ service.sh docker_run
	
To Test (new terminal):
	$ service.sh docker_smoke
	
## Import a different source

Just edit the `Dockerfile`, this line's 3rd element:

    CMD ["/src/service.sh", "dev_run", "data/example"]
   
If you don't want to rebuild everything to reimport a file, just do:

    $ docker exec -ti busrouteapp /bin/bash
    $ wget http://your.bus.route/url/newFilename
    # trigger the import
    $ wget http://localhost:8088/api/import?file_path=newFilename