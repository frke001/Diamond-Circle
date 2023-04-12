package org.unibl.etf.figures;

import java.util.ArrayList;
import java.util.Random;

import javax.management.ValueExp;

import org.unibl.etf.Diamond;
import org.unibl.etf.Program;
import org.unibl.etf.Simulation;
import org.unibl.etf.gui.GUI;

public class Ghost extends Thread{
	
	private static final int LOWER_LIMIT = 2;
	//private ArrayList<Integer> positionOfDiamondsArrayList;
	
	public Ghost() {
		super();
		//this.positionOfDiamondsArrayList = new ArrayList<>();
	}
	
	@Override
	public void run() {
		Random random = new Random();
		int br = 1;
		while(br < 2) { //Simulation.ongoingGame
			if(Simulation.pause) {
				synchronized (Simulation.lockForNotifyObject) {
					try {
						Simulation.lockForNotifyObject.wait();
					}catch (InterruptedException ex) {
						Program.myLogger.logger.warning(ex.getMessage());
					}
					
				}
			}
			clearDiamonds();
			int numberOfDiamonds = generateNumberOfDiamonds();
			while(numberOfDiamonds > 0) {
				synchronized (Simulation.diamondCircleMap) {
					int value = getRandomValue();
					if(Simulation.getMapFromPossition(value) == null) {
						Simulation.setMapOnPossition(value, new Diamond());
						Simulation.guiEdittor.diamondCircleMap.diamondPlacement(value);
						System.out.println("Duh je postavio dijamant na poziciju " + value);
						numberOfDiamonds--;
					}
				}
			}
			try {
				Thread.sleep(5000);
			}catch (InterruptedException ex) {
				Program.myLogger.logger.warning(ex.getMessage());
			}
			br++;
		}
	}
	
	private void clearDiamonds() {
		for(int i = 0; i < Simulation.mapDimension; i++)
			for(int j = 0 ; j < Simulation.mapDimension; j++) {
				if(Simulation.diamondCircleMap[i][j] instanceof Diamond) {
					Simulation.diamondCircleMap[i][j] = null;
					Simulation.guiEdittor.diamondCircleMap.unplacement(i*Simulation.mapDimension + j + 1);
				}
			}
	}
	private int generateNumberOfDiamonds() {
		Random random = new Random();
		int result = random.nextInt(Simulation.mapDimension - LOWER_LIMIT) + LOWER_LIMIT; // npr 2 do 7
		return result;
	}
	private int getRandomValue() {
		Random random = new Random();
		int position = random.nextInt(Simulation.path.size() - 1);
		int value = Simulation.path.get(position);
		return value;
	}
}
