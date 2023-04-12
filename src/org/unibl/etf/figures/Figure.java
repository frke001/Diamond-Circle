package org.unibl.etf.figures;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.unibl.etf.Color;
import org.unibl.etf.Simulation;

public abstract class Figure {
	public static String NAME_PREFIX = "Figure";
	public static int counter = 0;
	protected Color figureColor;
	protected String name;
	protected boolean finished;
	protected int diamonds;
	protected ArrayList<Integer> traveledPath;
	protected int currentPossition;
	protected long timeSpentPlaying;
	protected long beginRunning;
	protected long endRunning;
	
	public Figure() {
		counter++;
		this.traveledPath = new ArrayList<>();
		this.name = NAME_PREFIX + counter;
		this.figureColor = null;
		this.finished = false;
		//this.diamonds = 0;
		this.currentPossition = 0;
	}
	public Figure(Color figureColor) {
		counter++;
		this.traveledPath = new ArrayList<>();
		this.name = NAME_PREFIX + counter;
		this.figureColor = figureColor;
		this.finished = false;
		this.diamonds = 0;
		this.currentPossition = 0;
		this.traveledPath.add(Simulation.path.get(0));
	}
//	public void increaseDiamonds() {
//		diamonds++;
//	}
//	public void clearDiamonds() {
//		this.diamonds = 0;
//	}
	public Color getFigureColor() {
		return figureColor;
	}
	public boolean isFinished() {
		return finished;
	}
//	public int getDiamods() {
//		return diamonds;
//	}
	public String getName() {
		return name;
	}
	public ArrayList<Integer> getTraveledPath(){
		return traveledPath;
	}
	public int getCurrentPossition() {
		return currentPossition;
	}
	public long getBeginRunning() {
		return beginRunning;
	}
	public long getEndRunning() {
		return endRunning;
	}
	public void setFigureColor(Color figureColor) {
		this.figureColor = figureColor;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
//	public void setDiamonds(int diamonds) {
//		this.diamonds = diamonds;
//	}
	public void setName(String name) {
		this.name = name + counter;
	}
	public void setTraveledPath(ArrayList<Integer> traveledPath){
		this.traveledPath.addAll(traveledPath);
	}
	public void setCurrentPossition(int currentPossition) {
		this.currentPossition = currentPossition;
		traveledPath.add(Simulation.path.get(currentPossition));
	}
	public void setTimeSpentPlaying() {
		long temp = endRunning - beginRunning;
		long time = TimeUnit.MILLISECONDS.toSeconds(temp);
		timeSpentPlaying = time;
	}
	public void setBeginRunning(long beginRunning) {
		this.beginRunning = beginRunning;
	}
	public void setEndRunning(long endRunning) {
		this.endRunning = endRunning;
	}
	@Override
	public String toString() {
		StringBuilder path = new StringBuilder();
		for(var el : traveledPath) {
			if(traveledPath.indexOf(el) != traveledPath.size() - 1)
				path.append(el + "-");
			else
				path.append(el);
		}
		return " - predjeni put (" + path + ") - stigla do cilja - " + (finished?"[da]":"[ne]") + " vrijeme kretanja " + timeSpentPlaying + "s";
	}
	@Override
	public boolean equals(Object other) {
		if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        final Figure otherFigure = (Figure) other;
        if (!this.name.equals(otherFigure.name)) {
            return false;
        }
        return true;
	}
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + name.hashCode();
		hash = 7 * hash + figureColor.hashCode();
		return hash;
	}
}
