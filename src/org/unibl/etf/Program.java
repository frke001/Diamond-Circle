package org.unibl.etf;

import org.unibl.etf.gui.StartupFrame;

public class Program {
	
	public static final String LOG_FILE_DESTINATION = "./logFile.txt";
	public static MyLogger myLogger = new MyLogger(LOG_FILE_DESTINATION);
	
	public static void main(String args[]) {
		try {
			StartupFrame frame = new StartupFrame();
			frame.setVisible(true);
		} catch (Exception ex) {
			Program.myLogger.logger.warning(ex.getMessage());
		}
	}

}
