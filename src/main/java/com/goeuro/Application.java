package com.goeuro;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.goeuro.controllers.RouteController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	
    	if  (args.length <= 1 )
    	{
    		System.out.println( "- arg1 missing, we need a file to work with." );
    		return;
    	}
    	
    	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    	System.out.println("Importing <"+ args[1] +">...");
    	System.out.println(context.getBean(RouteController.class).importData(args[1]));
    }
}
