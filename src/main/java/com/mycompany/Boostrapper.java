package com.mycompany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boostrapper
{
	protected static final Logger logger = LoggerFactory.getLogger( Boostrapper.class );

	public static void main(String[] args) {
		try {
			SpringApplication.run( Boostrapper.class, args );
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

}
