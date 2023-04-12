package org.unibl.etf;

import java.util.ArrayList;

import org.unibl.etf.exceptions.*;
import org.unibl.etf.figures.Figure;

public class Player {
	public static final String NAME_PREFIX = "Player";
	public static final int NUMBER_OF_FIGURES = 4;
	public static final String ILLEGAL_COLOR_EXCEPTION_MESS = "Neodgovarajuca boja figure";
	public static int counter = 0; 
	private String name;
	private Color playerColor;
	private ArrayList<Figure> figures;
	private ArrayList<Figure> permanentFigures;
	private Figure currentFigure;
	
	public Player() {
		counter++;
		this.name = NAME_PREFIX + counter;
		this.playerColor = null;
		this.figures = new ArrayList<>(NUMBER_OF_FIGURES);
		this.permanentFigures = new ArrayList<>(NUMBER_OF_FIGURES);
		this.currentFigure = null;
	}
	public Player(Color playerColor) {
		counter++;
		this.name = NAME_PREFIX + counter;
		this.playerColor = playerColor;
		this.figures = new ArrayList<>(NUMBER_OF_FIGURES);
		this.permanentFigures = new ArrayList<>(NUMBER_OF_FIGURES);
		this.currentFigure = null;
	}
	
	public void addPlayerFigures(Figure figure) throws IllegalColorException {
		if(figure.getFigureColor().equals(figure.getFigureColor())) {
			this.figures.add(figure);
			this.permanentFigures.add(figure);
		}
		else 
			throw new IllegalColorException(ILLEGAL_COLOR_EXCEPTION_MESS);
	}
	public String getName() {
		return name;
	}
	public ArrayList<Figure> getFigures(){
		return figures;
	}
	public ArrayList<Figure> getPermanentFigures(){
		return permanentFigures;
	}
	public Figure getCurrFigure() {
		if(currentFigure == null) {
			currentFigure = figures.get(0);
			return currentFigure;
		}
		else
			return currentFigure;
	}
	public Color getPlayerColor() {
		return playerColor;
	}
	public void setName(String Name) {
		this.name = name + counter;
	}
	public void setFigures(ArrayList<Figure> fiugres) {
		this.figures.addAll(fiugres);
	}
	public void setCurrentFigure(Figure currentFigure) {
		this.currentFigure = currentFigure;
	}
	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Igrac " + " - " + this.name + "\n");
		for(var el : this.permanentFigures)
			result.append("\t\t" + el + "\n");
		return result.toString();
	}
}
