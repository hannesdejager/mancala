package com.cloudinvoke.mancala;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

/**
 * Where execution starts or 'Main' if you like.
 * 
 * @author Hannes de Jager
 * @since 24 April 2018
 */
public class EntryPoint {
	
	public static void main(String[] args) {
		configureWebserver();
		registerRestResources();
	}

	private static void configureWebserver() {
		port(8080);
		staticFiles.location("/public");
		// Note that HTML templates reside in src/main/resources/spark/template/**
	}
	
	private static void registerRestResources() {
		GameResource.register();		
	}

}
