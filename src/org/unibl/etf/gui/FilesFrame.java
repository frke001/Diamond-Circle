package org.unibl.etf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.management.ValueExp;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.Program;
import org.unibl.etf.Simulation;
import org.unibl.etf.config.PostGameResults;
import org.unibl.etf.figures.Figure;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class FilesFrame extends JFrame {

	private JPanel contentPane;
	private JList fileList = new JList();
	private JScrollPane scrollPane;
	/**
	 * Create the frame.
	 */
	public FilesFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 478, 429);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 71, 352, 248);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(fileList);
		
		fileList.setCellRenderer(new DefaultListCellRenderer(){
	    	@Override
	        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean hasFocus){
	            Component component = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
	            File file = (File) value;	
	            setText(file.getName());
	            setForeground(Color.GRAY);
	            setHorizontalAlignment(CENTER);
	            return component;
	        }
	    });
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	          JList filesList = (JList) mouseEvent.getSource();
	          int index = filesList.locationToIndex(mouseEvent.getPoint());
	          if (index >= 0) {
	            	Object o = filesList.getModel().getElementAt(index);
		            File file = (File) o;
		            ProcessBuilder proces = new ProcessBuilder();
		            proces.command("notepad.exe",file.getAbsolutePath());
		            try {
		                proces.start(); 
		            }catch (Exception ex) {
		            	Program.myLogger.logger.warning(ex.getMessage());
		            }

	          }
	        }
	      };
	      fileList.addMouseListener(mouseListener);
	}
	
	public void addFilesOnList() {
		//Figure[] figures =(Figure[]) Simulation.figuresArray.toArray();
		fileList.setListData(PostGameResults.getAllFiles());
	}

}
