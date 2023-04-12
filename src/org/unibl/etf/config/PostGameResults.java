package org.unibl.etf.config;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.unibl.etf.Program;
import org.unibl.etf.Simulation;

public class PostGameResults {
	
	private static final String FOLDER_PATH = "./results";
	public static final String PREFIX = "IGRA";
	public static final String EXTENSION = ".txt";
	
	public PostGameResults() {
		File file = new File(FOLDER_PATH);
		if(!file.exists())
		   file.mkdir();
	}
	
	public static void writeResults() {
		PrintWriter printWriter = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");  
		LocalDateTime now = LocalDateTime.now();  
		String suffix = dtf.format(now);
		try {
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FOLDER_PATH + File.separator + PREFIX + suffix + EXTENSION)));
			for(var player : Simulation.playersInGame) {
				printWriter.println(player);
			}
			printWriter.println("Vrijeme trajanja igre: " + Simulation.gameDurationTimer.seconds + "s");
		}catch (IOException ex) {
			Program.myLogger.logger.warning(ex.getMessage());
		}finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	public static File[] getAllFiles() {
		File file = new File(FOLDER_PATH);
		if(file.exists())
			return file.listFiles();
		else {
			return null;
		}
	}
	
}
