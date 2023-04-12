package org.unibl.etf;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.table.DefaultTableCellRenderer;

public class MyLogger {
	
	public Logger logger;
	public FileHandler fileHandler;
	
	public MyLogger(String fileName) {
		File file = new File(fileName);
		try {
		fileHandler = new FileHandler(fileName,true);
		}catch (IOException ex) {
			System.exit(-1);
		}
		logger = Logger.getLogger(fileName);
		logger.addHandler(fileHandler);
		SimpleFormatter simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		logger.setLevel(Level.WARNING);
			//logger.setUseParentHandlers(false);
	}

}