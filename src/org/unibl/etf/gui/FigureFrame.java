package org.unibl.etf.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.figures.Figure;

import java.awt.SystemColor;
import java.util.spi.AbstractResourceBundleProvider;

import javax.swing.JLabel;
import java.awt.Font;

public class FigureFrame extends JFrame {

	private JPanel contentPane;
    private DiamondCircleMap diamondCircleMap;
    private JLabel headerLabel = new JLabel();
	/**
	 * Create the frame.
	 */
	public FigureFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 359);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		diamondCircleMap = new DiamondCircleMap();
		diamondCircleMap.setBounds(45, 63, 398, 234);
		contentPane.add(diamondCircleMap);
		
		headerLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 11));
		headerLabel.setBackground(SystemColor.activeCaptionBorder);
		headerLabel.setBounds(144, 27, 197, 25);
		contentPane.add(headerLabel);
	}
	
	public void setLabeltext(Figure figure) {
		this.headerLabel.setText("Ime figure: " + figure.getName() + ", Boja: " + figure.getFigureColor());
	}
	public void colorPath(Figure figure) {
		for(var pathElement : figure.getTraveledPath()) {
			JLabel currentLabel = diamondCircleMap.getLabels().get(pathElement - 1);
			diamondCircleMap.determineColor(currentLabel, figure);
		}
	}
}
