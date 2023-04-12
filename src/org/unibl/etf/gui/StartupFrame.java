package org.unibl.etf.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.Program;
import org.unibl.etf.Simulation;
import org.unibl.etf.exceptions.ConfigFileException;

import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class StartupFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtDiamondCircleEntry;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupFrame frame = new StartupFrame();
					frame.setVisible(true);
				} catch (Exception ex) {
					Program.myLogger.logger.warning(ex.getMessage());
				}
			}
		});
	}

	public StartupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 375);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtDiamondCircleEntry = new JTextField();
		txtDiamondCircleEntry.setForeground(new Color(255, 0, 0));
		txtDiamondCircleEntry.setHorizontalAlignment(SwingConstants.CENTER);
		txtDiamondCircleEntry.setText("DIAMOND CIRCLE ENTRY");
		txtDiamondCircleEntry.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		txtDiamondCircleEntry.setBackground(new Color(255, 255, 0));
		txtDiamondCircleEntry.setBounds(119, 29, 192, 43);
		contentPane.add(txtDiamondCircleEntry);
		txtDiamondCircleEntry.setColumns(10);
		
		JLabel dimesionLabel = new JLabel("Izbor dimenzije:");
		dimesionLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		dimesionLabel.setBackground(new Color(255, 255, 0));
		dimesionLabel.setForeground(new Color(0, 0, 0));
		dimesionLabel.setBounds(54, 121, 111, 14);
		contentPane.add(dimesionLabel);
		
		JLabel playerLabel = new JLabel("Izbor broja igraca:");
		playerLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		playerLabel.setBounds(54, 199, 111, 14);
		contentPane.add(playerLabel);
		
		JComboBox mapDimensionComboBox = new JComboBox();
		mapDimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"7", "8", "9", "10"}));
		mapDimensionComboBox.setBackground(new Color(255, 255, 0));
		mapDimensionComboBox.setBounds(195, 118, 82, 22);
		contentPane.add(mapDimensionComboBox);
		
		JComboBox numberOfPlayersComboBox = new JComboBox();
		numberOfPlayersComboBox.setBackground(new Color(255, 255, 0));
		numberOfPlayersComboBox.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		numberOfPlayersComboBox.setBounds(195, 196, 82, 22);
		contentPane.add(numberOfPlayersComboBox);
		
		
		JButton startButton = new JButton("START");
		startButton.setForeground(new Color(255, 0, 0));
		startButton.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 13));
		startButton.setBackground(new Color(255, 255, 0));
		startButton.setBounds(151, 265, 122, 32);
		contentPane.add(startButton);
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dimension = (String) mapDimensionComboBox.getSelectedItem();
				String number = (String) numberOfPlayersComboBox.getSelectedItem();
				try {
					int mapDimension = Integer.parseInt(dimension);
					int numberOfPlayers = Integer.parseInt(number);
					Simulation.startupSettings(mapDimension, numberOfPlayers);
				}catch (IndexOutOfBoundsException ex) {
					Program.myLogger.logger.warning(ex.getMessage());
					System.exit(-1);
				}
				catch (ConfigFileException ex) {
					Program.myLogger.logger.warning(ex.getMessage());
					System.exit(-1);
				}
				catch (IOException ex) {
					Program.myLogger.logger.warning(ex.getMessage());
					System.exit(-1);
				}
				Simulation simulation = new Simulation();
				setVisible(false);
				simulation.start();
			}
		});
	}
}
