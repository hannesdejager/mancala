package com.cloudinvoke.mancala;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Where execution starts or 'Main' if you like.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class EntryPoint {
	
	private static final Logger LOG = LoggerFactory.getLogger(EntryPoint.class);
	
	public static void main(String[] args) {
		LOG.info("Starting Mancala.");
		configureWebserver();
		registerRestResources();
	}

	private static void configureWebserver() {
		port(8080);
		staticFiles.location("/public");
		// Note that HTML templates reside in src/main/resources/spark/template/**
	}
	
	private static void registerRestResources() {
		LOG.info("Registering REST resources.");
		GameResource.register();		
	}

}
