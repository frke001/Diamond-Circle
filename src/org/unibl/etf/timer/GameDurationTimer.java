package org.unibl.etf.timer;

import java.util.spi.AbstractResourceBundleProvider;

import org.unibl.etf.Program;
import org.unibl.etf.Simulation;

public class GameDurationTimer extends Thread{
	
	public int seconds = 0; 
	@Override
	public void run() {
//		int seconds = 0;
		while(Simulation.ongoingGame) {
			if(Simulation.pause) {
				synchronized (Simulation.lockForNotifyObject) {
					try {
						Simulation.lockForNotifyObject.wait();
					}catch (InterruptedException ex) {
						Program.myLogger.logger.warning(ex.getMessage());
					}
				}
			}
			Simulation.guiEdittor.setGameDurationTextField(seconds);
			try {
				currentThread().sleep(1000);
			}catch (InterruptedException ex) {
				Program.myLogger.logger.warning(ex.getMessage());
			}
			seconds++;
		}
	}
	

}