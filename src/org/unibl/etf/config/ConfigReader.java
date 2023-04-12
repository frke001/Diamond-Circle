package org.unibl.etf.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.unibl.etf.Program;
import org.unibl.etf.exceptions.ConfigFileException;

public class ConfigReader {
	public static final String CONFIG_FILE_PATH = "./src/config.txt";
	public int mapDimension;
	public int numberOfHoles;
	public ArrayList<Integer> path;
	public ConfigReader(int mapDimension) {
		path = new ArrayList<>();
		this.mapDimension = mapDimension;
	}
	public void readConfig() throws ConfigFileException,IOException {
		BufferedReader configReader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
		String line;
		int br = 0;
		boolean status = true;
		while((line = configReader.readLine()) != null && status) {
			if(br == 0) {
				try {
				numberOfHoles = Integer.parseInt(line);
				br++;
				}catch (NumberFormatException ex) {
					Program.myLogger.logger.warning(ex.getMessage());
				}
			}
			else {
				try {
					String elements[] = line.split(",");
					String dimensionElementString [] = elements[0].split("-");
					int dimension = 0;
					try {
						dimension  = Integer.parseInt(dimensionElementString[1]);
						}catch (NumberFormatException ex) {
							Program.myLogger.logger.warning(ex.getMessage());
						}
					if(dimension == mapDimension) {
						status = false;
						String pathElements[] = elements[1].split("-");
						for(var el : pathElements) {
							path.add(Integer.parseInt(el));
						}
					}
					}catch (IndexOutOfBoundsException e) {
						throw new ConfigFileException("Odstupanje od formata config fajla!");
					}
			}
			
		}
	}
}
