package com.goeuro.controllers;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goeuro.entities.PublicStop;
import com.goeuro.entities.Response;
import com.goeuro.entities.RouteResponse;
import com.goeuro.services.PublicStopService;

@RestController
@RequestMapping("/api")
public class RouteController {
	
	@Value("${app.path}")
	String appPath;
	
	@Autowired
	PublicStopService publicStopService;
	
    @RequestMapping("/direct")
    public RouteResponse direct(@RequestParam(value="dep_sid") String departureStopNo, @RequestParam(value="arr_sid") String arrivalStopNo ) {

    	if ( publicStopService.routeExists(departureStopNo, arrivalStopNo ) )
    	{			
          	return new RouteResponse(departureStopNo, arrivalStopNo, true);
    	}

      	return new RouteResponse(departureStopNo, arrivalStopNo, false);
    }

    @RequestMapping("/import")
    public Response importData(@RequestParam(value="file_path") String filePath) {
    	
    	String fullImportPath = appPath + File.separator + filePath;
    	System.out.println("Will import "+ fullImportPath);
    	
    	File f = new File( fullImportPath );
    	
    	if(!f.exists() || f.isDirectory()) 
    	{ 
    		return new Response("complete", "false", "Specified file path does not exist in your SPRING project folder.");
    	}

    	publicStopService.cleanUp( );

		try {
			Scanner linReader = new Scanner(f);
			Long lineCnt = 1L;
			Long lineCntSrc = 0L;
	        while (linReader.hasNext())
	        {
	            String line = linReader.nextLine();

				if ( lineCnt == 1 )
				{
					lineCntSrc = new Long( line );
				}
				else
				{
					String[] lineBits = line.split(" ");

					if ( lineBits.length < 4 )
					{
						System.out.println( "Invalid line, at least a [routeID] and three stops are required per line:" );
						System.out.println( line );
					}
					else
					{
						for (int i = 1; i< lineBits.length; i++)
						{
							PublicStop publicStop = new PublicStop( lineBits[i], lineBits[0], i );
							publicStopService.save(publicStop);
						}
					}
				}				

				lineCnt++;
	        }
			
	        linReader.close();
		} catch (FileNotFoundException e) {
			return new Response("complete", "false", "Exception: "+ e.getMessage());
		}

      	return new Response("complete", "true", "Data imported!");
    }
}
