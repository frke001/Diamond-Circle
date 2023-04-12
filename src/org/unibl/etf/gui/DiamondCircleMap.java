package org.unibl.etf.gui;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.unibl.etf.Color;
import org.unibl.etf.Simulation;
import org.unibl.etf.figures.Figure;
import org.unibl.etf.figures.FlyingFigure;
import org.unibl.etf.figures.OrdinaryFigure;
import org.unibl.etf.figures.SuperFastFigure;

public class DiamondCircleMap extends JPanel{
	
	private ArrayList<JLabel> labels = new ArrayList<>();
	
	private static final String DIAMOND_IMAGE_PATH = "diamondIcon.png";
	private static final String SUPERFAST_IMAGE_PATH = "fast.png";
	private static final String ORDINARY_IMAGE_PATH = "ordinary.png";
	private static final String FLYING_IMAGE_PATH = "flying.png";
	
	private ImageIcon diamondImageIcon;
	private ImageIcon superFastFigureImageIcon;
	private ImageIcon ordinaryFigureImageIcon;
	private ImageIcon flyingFigureImageIcon;

    public DiamondCircleMap(){
    	
    	this.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED,2));
    	this.setLayout(new GridLayout(Simulation.mapDimension,Simulation.mapDimension,2,2));
    	for(int i = 0; i < Simulation.mapDimension; i++)
    		for(int j = 0; j < Simulation.mapDimension; j++) {
    			int numberOfField = i * Simulation.mapDimension + j + 1;
    			JLabel oneLabel = new JLabel(String.valueOf(numberOfField), SwingConstants.CENTER);
    			oneLabel.setHorizontalTextPosition(JLabel.CENTER);
    			oneLabel.setVerticalTextPosition(JLabel.CENTER);
    			Border border = BorderFactory.createLineBorder(java.awt.Color.RED,1);
    			oneLabel.setBorder(border);
    			oneLabel.setOpaque(true);
    			if(!Simulation.path.contains(numberOfField)) {
    				oneLabel.setBackground(java.awt.Color.ORANGE);
    			}
    			else {
    				oneLabel.setBackground(java.awt.Color.LIGHT_GRAY);
    			}
    			this.add(oneLabel);
    			labels.add(oneLabel);	
    		}
    	
    	diamondImageIcon = new ImageIcon(DIAMOND_IMAGE_PATH);
    	superFastFigureImageIcon = new ImageIcon(SUPERFAST_IMAGE_PATH);
    	ordinaryFigureImageIcon = new ImageIcon(ORDINARY_IMAGE_PATH);
    	flyingFigureImageIcon = new ImageIcon(FLYING_IMAGE_PATH);
    }
    
    public void determineColor(Component component,Figure figure) {
    	if(figure.getFigureColor() == Color.RED)
    		component.setBackground(java.awt.Color.RED);
    	else if(figure.getFigureColor() == Color.GREEN)
    		component.setBackground(java.awt.Color.GREEN);
    	else if(figure.getFigureColor() == Color.BLUE)
    		component.setBackground(java.awt.Color.BLUE);
    	else if(figure.getFigureColor() == Color.YELLOW)
    		component.setBackground(java.awt.Color.YELLOW);
    }
    public void figurePlacement(Figure figure) {
    	int position = figure.getCurrentPossition();
    	int value = Simulation.path.get(position);
    	JLabel currentLabel = labels.get(value - 1);
        determineColor(currentLabel, figure);
    	if(figure instanceof SuperFastFigure)
    		currentLabel.setIcon(superFastFigureImageIcon);
    	else if(figure instanceof FlyingFigure)
    		currentLabel.setIcon(flyingFigureImageIcon);
    	else if(figure instanceof OrdinaryFigure)
    		currentLabel.setIcon(ordinaryFigureImageIcon);
    }
    
    public void unplacement(int value) {
    	JLabel currentLabel = labels.get(value - 1);
    	currentLabel.setBackground(java.awt.Color.LIGHT_GRAY);
    	currentLabel.setIcon(null);
    }
    public void holePlacement(int value) {
    	JLabel currentLabel = labels.get(value - 1);
    	currentLabel.setBackground(java.awt.Color.BLACK);
    	currentLabel.setIcon(null);
    }
    public void diamondPlacement(int value) {
    	JLabel currentLabel = labels.get(value - 1);
    	currentLabel.setBackground(java.awt.Color.PINK);
    	currentLabel.setIcon(diamondImageIcon);
    }
    public ArrayList<JLabel> getLabels(){
    	return labels;
    }
}