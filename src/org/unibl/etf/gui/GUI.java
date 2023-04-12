package org.unibl.etf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.SystemColor;
import javax.swing.border.LineBorder;

import org.unibl.etf.Player;
import org.unibl.etf.Program;
import org.unibl.etf.Simulation;
import org.unibl.etf.figures.Figure;
import org.unibl.etf.figures.Ghost;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	private static final String CARD_TEXT_FIELD = "Trenutna karta";
	private JPanel mainPanel = new JPanel();;
	public DiamondCircleMap diamondCircleMap;
	private final JPanel header = new JPanel();
	private final JPanel playerPanel = new JPanel();
	private final JPanel figuresPanel = new JPanel();
	private final JPanel cardPanel = new JPanel();
	JPanel descriptionCardPanel = new JPanel();
	JPanel filePanel = new JPanel();
	private JTextField cardTextField = new JTextField();
	
	private JTextField gameDurationTextField = new JTextField();
	private JTextField logoTextField = new JTextField();
	public JLabel gamesCountLabel = new JLabel();
	private final JTextArea figurePathTextArea = new JTextArea();
	private JButton startStopButton = new JButton("Pokreni / Zaustavi");
	private final JButton fileButton = new JButton();
	private JLabel cardLabel = new JLabel();
	
	private static final String CARD1_IMAGE = "card1.png";
	private static final String CARD2_IMAGE = "card2.png";
	private static final String CARD3_IMAGE = "card3.png";
	private static final String CARD4_IMAGE = "card4.png";
	private static final String SPECIALCARD_IMAGE = "specialcard.png";
	
	private ImageIcon card1ImageIcon;
	private ImageIcon card2ImageIcon;
	private ImageIcon card3ImageIcon;
	private ImageIcon card4ImageIcon;
	private ImageIcon specialCardImageIcon;
	private final JLabel cardDescriptionLabel = new JLabel("Opis znacenja karte:");
	private JTextArea cardDescriptionTextArea = new JTextArea();
	JList figuresList = new JList();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		card1ImageIcon = new ImageIcon(CARD1_IMAGE);
		card2ImageIcon = new ImageIcon(CARD2_IMAGE);
		card3ImageIcon = new ImageIcon(CARD3_IMAGE);
		card4ImageIcon = new ImageIcon(CARD4_IMAGE);
		specialCardImageIcon = new ImageIcon(SPECIALCARD_IMAGE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 650);
		mainPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		diamondCircleMap = new DiamondCircleMap();
		diamondCircleMap.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		diamondCircleMap.setBackground(SystemColor.inactiveCaption);
		diamondCircleMap.setBounds(189, 162, 479, 331);
		mainPanel.add(diamondCircleMap);
		header.setBackground(new Color(128, 128, 128));
		header.setBounds(0, 0, 934, 86);
		mainPanel.add(header);
		header.setLayout(null);
		
		logoTextField = new JTextField();
		logoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		logoTextField.setForeground(Color.RED);
		logoTextField.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
		logoTextField.setText("DiamondCircle");
		logoTextField.setBackground(new Color(255, 228, 181));
		logoTextField.setBounds(346, 29, 211, 31);
		header.add(logoTextField);
		logoTextField.setColumns(10);
		
		gamesCountLabel = new JLabel();
		gamesCountLabel.setText("<html><center>"+"Trenutni broj odigranih"+"<br>"+"igara: 0"+"</center></html>");
		gamesCountLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		gamesCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gamesCountLabel.setBounds(35, 29, 145, 31);
		header.add(gamesCountLabel);
		startStopButton.setForeground(Color.BLACK);
		startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Simulation.pause && Simulation.ongoingGame)
					Simulation.pause = true;
				else if(Simulation.pause && Simulation.ongoingGame){
					try {
						synchronized (Simulation.lockForNotifyObject) {
							Simulation.pause = false;
							Simulation.lockForNotifyObject.notifyAll();
						}
					}catch (Exception ex) {
						Program.myLogger.logger.warning(ex.getMessage());
					}
				}
				else if(!Simulation.ongoingGame && !Simulation.pause) {
					//Simulation.guiEdittor.setVisible(false);
					Simulation simulation = new Simulation();
					simulation.start();
				}
			}
		});
		
		startStopButton.setBackground(Color.ORANGE);
		startStopButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		startStopButton.setBounds(701, 29, 145, 31);
		header.add(startStopButton);
		FlowLayout flowLayout = (FlowLayout) playerPanel.getLayout();
		flowLayout.setHgap(25);
		playerPanel.setBackground(new Color(192, 192, 192));
		playerPanel.setBounds(26, 97, 831, 40);
		mainPanel.add(playerPanel);
		figuresPanel.setBackground(new Color(192, 192, 192));
		figuresPanel.setBounds(26, 159, 142, 441);
		mainPanel.add(figuresPanel);
		figuresPanel.setLayout(null);
		figurePathTextArea.setBackground(SystemColor.activeCaptionBorder);
		figurePathTextArea.setWrapStyleWord(true);
		figurePathTextArea.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		figurePathTextArea.setLineWrap(true);
		figurePathTextArea.setText("Klikom na odredjenu figuru prikazuje se novi prozor na kom se iscrtava trenutna predjena putanja.");
		figurePathTextArea.setBounds(21, 273, 111, 100);
		
		figuresPanel.add(figurePathTextArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 24, 89, 227);
		figuresPanel.add(scrollPane);
		figuresList.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		

		scrollPane.setViewportView(figuresList);
		figuresList.setBackground(Color.GRAY);
		cardPanel.setBackground(new Color(192, 192, 192));
		cardPanel.setBounds(686, 165, 171, 270);
		
		mainPanel.add(cardPanel);
		cardPanel.setLayout(null);
		cardLabel.setForeground(new Color(255, 218, 185));
		
		cardLabel.setBackground(new Color(255, 165, 0));
		cardLabel.setBounds(10, 44, 148, 215);
		cardPanel.add(cardLabel);
		cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardTextField.setBounds(10, 11, 154, 22);
		cardPanel.add(cardTextField);
		cardTextField.setHorizontalAlignment(SwingConstants.CENTER);
		cardTextField.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 13));
		cardTextField.setBackground(new Color(255, 228, 181));
		cardTextField.setText(CARD_TEXT_FIELD);
		cardTextField.setBorder(BorderFactory.createLineBorder(Color.RED,1));
		gameDurationTextField.setHorizontalAlignment(SwingConstants.CENTER);
		gameDurationTextField.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
		
		gameDurationTextField.setBackground(new Color(255, 228, 181));
		gameDurationTextField.setBounds(689, 447, 168, 28);
		mainPanel.add(gameDurationTextField);
		gameDurationTextField.setColumns(10);
		filePanel.setBackground(new Color(192, 192, 192));
		
		filePanel.setBounds(697, 504, 162, 96);
		mainPanel.add(filePanel);
		filePanel.setLayout(null);
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilesFrame filesFrame = new FilesFrame();
				filesFrame.setVisible(true);
				filesFrame.addFilesOnList();
			}
		});
		fileButton.setForeground(Color.BLACK);
		fileButton.setText("<html><center>"+"Prikaz liste"+"<br>"+"fajlova sa"+"<br>" + "rezultatima" + "</center></html>");
		fileButton.setVerticalAlignment(SwingConstants.CENTER);
		fileButton.setHorizontalAlignment(SwingConstants.CENTER);
		fileButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		fileButton.setBackground(Color.ORANGE);
		fileButton.setBounds(33, 21, 105, 53);
		
		filePanel.add(fileButton);
		descriptionCardPanel.setBackground(new Color(192, 192, 192));
		
		descriptionCardPanel.setBounds(178, 504, 509, 96);
		mainPanel.add(descriptionCardPanel);
		descriptionCardPanel.setLayout(null);
		cardDescriptionLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		cardDescriptionLabel.setBounds(205, 11, 120, 14);
		
		descriptionCardPanel.add(cardDescriptionLabel);
		cardDescriptionTextArea.setWrapStyleWord(true);
		cardDescriptionTextArea.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 11));
		
		cardDescriptionTextArea.setBackground(Color.LIGHT_GRAY);
		cardDescriptionTextArea.setLineWrap(true);
		cardDescriptionTextArea.setBounds(184, 36, 159, 51);
		descriptionCardPanel.add(cardDescriptionTextArea);
		
		
	    figuresList.setCellRenderer(new DefaultListCellRenderer(){
	    	@Override
	        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean hasFocus){
	            Component component = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
	            Figure figure = (Figure) value;	
	            setText(figure.getName());
	            determineColor(component, figure);
	            setHorizontalAlignment(CENTER);
	            return component;
	        }
	    });
		
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	          JList figureList = (JList) mouseEvent.getSource();
	          if (mouseEvent.getClickCount() == 1) {
	            int index = figureList.locationToIndex(mouseEvent.getPoint());
	            if (index >= 0) {
	              Object o = figureList.getModel().getElementAt(index);
	              if(o instanceof Figure) {
	            	  Figure figure = (Figure) o;
	            	  FigureFrame figureFrame = new FigureFrame();
	            	  figureFrame.setVisible(true);
	            	  figureFrame.setLabeltext(figure);
	            	  figureFrame.colorPath(figure);
	              }
	            }
	          }
	        }
	      };
	      figuresList.addMouseListener(mouseListener);
	}
	public void insertCard(int numebrOnCard) {
		
		if(numebrOnCard == 1)
			cardLabel.setIcon(card1ImageIcon);
		else if(numebrOnCard == 2)
			cardLabel.setIcon(card2ImageIcon);
		else if(numebrOnCard == 3)
			cardLabel.setIcon(card3ImageIcon);
		else if(numebrOnCard == 4)
			cardLabel.setIcon(card4ImageIcon);
		
		
	}
	public void insertSpecialCard() {
		this.cardLabel.setIcon(specialCardImageIcon);
	}
	public DiamondCircleMap getDiamondCircleMap() {
		return diamondCircleMap;
	}
	public void setGamesCountLabel() {
		this.gamesCountLabel.setText("<html><center>"+"Trenutni broj odigranih"+"<br>"+"igara: " + Simulation.numberOfGamesPlayed +"</center></html>");
	}
	public void setCardDescriptionTextAreaOrdinary(Player player, Figure figure, int numberOfFields) {
		this.cardDescriptionTextArea.setText("Na potezu je igrac " + player.getName() + " " + figure.getName() + " prelazi " + numberOfFields + " polja" );
	}
	public void setCardDescriptionTextAreaSpecial(Player player, Figure figure) {
		this.cardDescriptionTextArea.setText("Na potezu je igrac " + player.getName() + ", " + figure.getName() + " je izvukla specijalnu kartu");
	}
	public void addPlayerLabels() {
		for(var player : Simulation.playersInGame) {
			JLabel plr = new JLabel(player.getName());
			if(player.getPlayerColor() == org.unibl.etf.Color.RED)
				plr.setForeground(Color.RED);
			else if(player.getPlayerColor() == org.unibl.etf.Color.GREEN)
				plr.setForeground(Color.GREEN);
			else if(player.getPlayerColor() == org.unibl.etf.Color.BLUE)
				plr.setForeground(Color.BLUE);
			else 
				plr.setForeground(Color.YELLOW);
			this.playerPanel.add(plr);
		}
	}
	public void clearPlayerLabels() {
		this.playerPanel.removeAll();
	}
	public void determineColor(Component component,Figure figure) {
		if(figure.getFigureColor() == org.unibl.etf.Color.RED)
    		component.setForeground(java.awt.Color.RED);
    	else if(figure.getFigureColor() == org.unibl.etf.Color.GREEN)
    		component.setForeground(java.awt.Color.GREEN);
    	else if(figure.getFigureColor() == org.unibl.etf.Color.BLUE)
    		component.setForeground(java.awt.Color.BLUE);
    	else if(figure.getFigureColor() == org.unibl.etf.Color.YELLOW)
    		component.setForeground(java.awt.Color.YELLOW);
	}
	public void addFiguresOnList() {
		//Figure[] figures =(Figure[]) Simulation.figuresArray.toArray();
		figuresList.setListData(Simulation.figuresArray.toArray());
	}
	public void setGameDurationTextField(int seconds) {
		this.gameDurationTextField.setText("Vrijeme trajanja igre: " + seconds + "s");
	}
}
