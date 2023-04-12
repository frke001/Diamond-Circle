package org.unibl.etf;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JEditorPane;

import org.unibl.etf.cards.*;
import org.unibl.etf.config.ConfigReader;
import org.unibl.etf.config.PostGameResults;
import org.unibl.etf.exceptions.ConfigFileException;
import org.unibl.etf.exceptions.IllegalColorException;
import org.unibl.etf.figures.*;
import org.unibl.etf.gui.GUI;
import org.unibl.etf.timer.GameDurationTimer;

public class Simulation extends Thread {
	public static Object[][] diamondCircleMap;
	public static int mapDimension;
	public static int numberOfPlayers;
	public static int numberOfGamesPlayed = 0;
	public static int numberOfHoles;
	public static boolean ongoingGame;
	public static ArrayList<Player> playersInGame = new ArrayList<>();
	public static Queue<Card> cards = new LinkedList<>();
	public static ArrayList<Figure> figuresArray = new ArrayList<>();
	public static HashMap<Figure, Player> allFigures = new HashMap<>();
	public static ArrayList<Integer> path = new ArrayList<>();
	public static GUI guiEdittor;
	public static boolean pause = false;
	public static Object lockForNotifyObject = new Object();
	public static GameDurationTimer gameDurationTimer;


	public Simulation() {
		Figure.counter = 0;
		Simulation.addFiguresToAllPlayers();
		Simulation.formDiamondCircleMap();
		Simulation.guiEdittor.clearPlayerLabels();
		Simulation.guiEdittor.addPlayerLabels();
		Simulation.guiEdittor.addFiguresOnList();
		Collections.shuffle(playersInGame);
		Collections.shuffle((LinkedList) cards);

	}

	public static void startupSettings(int mapDimensions, int numberOfPlayers) throws ConfigFileException, IOException {
		Simulation.mapDimension = mapDimensions;
		Simulation.numberOfPlayers = numberOfPlayers;
		ConfigReader configReader = new ConfigReader(mapDimensions);
		configReader.readConfig();
		Simulation.numberOfHoles = configReader.numberOfHoles;
		Simulation.path = configReader.path;
		Simulation.playerInitialization();
		Simulation.formCardDeck();
		guiEdittor = new GUI();
		guiEdittor.setVisible(true);
	}

	@Override
	public void run() {
		Simulation.ongoingGame = true;
		gameDurationTimer = new GameDurationTimer();
		gameDurationTimer.start();
		Ghost ghost = new Ghost();
		ghost.start();
		while (!Simulation.isEnded()) {
			for (var player : Simulation.playersInGame) {
				if (player.getFigures().size() != 0) {
					Figure currFigure = player.getCurrFigure();
					int numberOfFields = 0;
					if (currFigure.getCurrentPossition() == 0) {
						currFigure.setBeginRunning(System.currentTimeMillis());
						System.out.println(currFigure.getName() + " se nalazi na poziciji "
								+ Simulation.path.get(currFigure.getCurrentPossition()));
						if (Simulation.getMapFromPossition(
								Simulation.path.get(currFigure.getCurrentPossition())) instanceof Diamond) {

							numberOfFields++;
							Simulation.setMapOnPossition(Simulation.path.get(currFigure.getCurrentPossition()), null);
						}
						Simulation.setMapOnPossition(Simulation.path.get(currFigure.getCurrentPossition()), currFigure);
						guiEdittor.diamondCircleMap.figurePlacement(currFigure);
					}
					Card currentCard = Simulation.cards.poll();
					Simulation.cards.offer(currentCard); // izvucemo kartu sa vrha i vratimo na dno spila

					if (currentCard instanceof OrdinaryCard) {
						numberOfFields += currentCard.getNumberOfFileds();
						System.out.println("Izvucena karta " + numberOfFields);

						Simulation.guiEdittor.insertCard(numberOfFields);
						Simulation.guiEdittor.setCardDescriptionTextAreaOrdinary(player, currFigure, numberOfFields);

						moveFigure(player, numberOfFields);
					} else if (currentCard instanceof SpecialCard) {
						System.out.println("Izvucena specijalna karta");

						Simulation.guiEdittor.insertSpecialCard();
						Simulation.guiEdittor.setCardDescriptionTextAreaSpecial(player, currFigure);

						specialCardFunction();
					}
				}
			}
		}
		Simulation.numberOfGamesPlayed++;
		System.out.println("Igra je zavrsila!");
		Simulation.ongoingGame = false;
		Simulation.guiEdittor.setGamesCountLabel();
		PostGameResults.writeResults();

	}

	private void specialCardFunction() {
		Random random = new Random();
		int currentNumberOfHoles;
		do {
			currentNumberOfHoles = random.nextInt(Simulation.numberOfHoles);
		} while (currentNumberOfHoles == 0);
		ArrayList<Integer> positions = new ArrayList<>();
		do {
			int pos = random.nextInt(Simulation.path.size() - 1);
			if (!positions.contains(pos) && pos != 0) {
				positions.add(pos);
			}
		} while (positions.size() != currentNumberOfHoles);
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Program.myLogger.logger.warning(ex.getMessage());
		}
		synchronized (Simulation.diamondCircleMap) {
			for (var position : positions) {
				int value = Simulation.path.get(position);
				// crtanje rupe
				Simulation.guiEdittor.diamondCircleMap.holePlacement(value);
				Object object = Simulation.getMapFromPossition(value);
				if (!(object instanceof FlyingFigure) && !(object instanceof Diamond) && object != null) {
					Figure figure = (Figure) object;
					Player player = Simulation.allFigures.get(figure);
					System.out.println(figure.getName() + " na poziciji " + value + " je propala u rupu");
					Simulation.setMapOnPossition(value, null);
					figure.setEndRunning(System.currentTimeMillis());
					figure.setTimeSpentPlaying();
					Simulation.removeFigure(player, figure);
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				Program.myLogger.logger.warning(ex.getMessage());
			}
			for (var pos : positions) {
				int value = Simulation.path.get(pos);
				Object object = Simulation.getMapFromPossition(value);
				if (object instanceof FlyingFigure) {
					FlyingFigure flyingFigure = (FlyingFigure) object;
					Simulation.guiEdittor.diamondCircleMap.figurePlacement(flyingFigure);
				} else if (object instanceof Diamond) {
					Simulation.guiEdittor.diamondCircleMap.diamondPlacement(value);
				} else {
					Simulation.guiEdittor.diamondCircleMap.unplacement(value);
				}
			}
		}

	}

	private void moveFigure(Player player, int numberOfFields) {

		Figure currFigure = player.getCurrFigure();
		if (currFigure instanceof SuperFastFigure)
			numberOfFields = numberOfFields * 2;

		for (int i = 1; i <= numberOfFields; i++) {
			if (Simulation.pause) {
				synchronized (Simulation.lockForNotifyObject) {
					try {
						lockForNotifyObject.wait();
					} catch (InterruptedException ex) {
						Program.myLogger.logger.warning(ex.getMessage());
					}

				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Program.myLogger.logger.warning(ex.getMessage());
			}
			synchronized (Simulation.diamondCircleMap) {
				int position = currFigure.getCurrentPossition();
				Simulation.setMapOnPossition(Simulation.path.get(position), null);
				Simulation.guiEdittor.diamondCircleMap.unplacement(Simulation.path.get(position));

				position++;
				while (position < Simulation.path.size() - 1
						&& Simulation.getMapFromPossition(Simulation.path.get(position)) instanceof Figure) {
					currFigure.setCurrentPossition(position);
					position++;
//					numberOfFields--;
					i++;
					if (i != numberOfFields) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							Program.myLogger.logger.warning(ex.getMessage());
						}
					}
				}
				if (position == Simulation.path.size() - 1) {
					currFigure.setCurrentPossition(position);
					Simulation.guiEdittor.diamondCircleMap.figurePlacement(currFigure);
					System.out.println(currFigure.getName() + " je zavrsila igru!");
					try {
						Thread.sleep(500);
					} catch (InterruptedException ex) {
						Program.myLogger.logger.warning(ex.getMessage());
					}
					currFigure.setFinished(true);
					currFigure.setEndRunning(System.currentTimeMillis());
					currFigure.setTimeSpentPlaying();
					Simulation.removeFigure(player, currFigure);
					Simulation.guiEdittor.diamondCircleMap.unplacement(Simulation.path.get(position));
					return;
				}
				if (Simulation.getMapFromPossition(Simulation.path.get(position)) instanceof Diamond) {
					numberOfFields++;
					Simulation.setMapOnPossition(Simulation.path.get(position), null); // sklonimo dijamant
					System.out.println(currFigure.getName() + " je pokupila dijamant!");
				}
				currFigure.setCurrentPossition(position);
				System.out.println(currFigure.getName() + " se nalazi na poziciji " + Simulation.path.get(position));
				Simulation.setMapOnPossition(Simulation.path.get(position), currFigure);
				Simulation.guiEdittor.diamondCircleMap.figurePlacement(currFigure);
			}
		}
	}

	private static void removeFigure(Player player, Figure figure) {
		player.getFigures().remove(figure);
		if (player.getFigures().size() != 0)
			player.setCurrentFigure(player.getFigures().get(0));
		else
			System.out.println(player.getName() + " je zavrsio igru!");
	}

	// if all the players have lost their figures -> end of the game
	private static boolean isEnded() {
		for (var el : Simulation.playersInGame)
			if (el.getFigures().size() != 0)
				return false;
		return true;
	}

	// forming an array of colors
	private static ArrayList<Color> formColorArray() {
		ArrayList<Color> colors = new ArrayList<>();
		for (var el : Color.values()) {
			colors.add(el);
		}
		return colors;
	}

	// assigning colors to players
	private static void playerInitialization() {
		ArrayList<Color> colors = Simulation.formColorArray();
		Collections.shuffle(colors);
		int possition = 0;
		for (int i = 0; i < Simulation.numberOfPlayers; i++) {
			Color tempColor = colors.get(possition++);
			Simulation.playersInGame.add(new Player(tempColor));
		}
	}

	// forming a deck of cards 40 + 12 specialized
	private static void formCardDeck() {
//		int numberOfFields = 1;
//		for (int i = 1; i <= 40; i++) {
//			if (i % 10 == 0)
//				numberOfFields++;
//			Simulation.cards.add(new OrdinaryCard(numberOfFields));
//		}
		for (int i = 0; i < 10; i++) {
			Simulation.cards.add(new OrdinaryCard(1));
		}
		for (int i = 0; i < 10; i++) {
			Simulation.cards.add(new OrdinaryCard(2));
		}
		for (int i = 0; i < 10; i++) {
			Simulation.cards.add(new OrdinaryCard(3));
		}
		for (int i = 0; i < 10; i++) {
			Simulation.cards.add(new OrdinaryCard(4));
		}
		for (int i = 0; i < 12; i++) {
			Simulation.cards.add(new SpecialCard());
		}
	}

	// we generate four figures for each player according to his color
	private static void addFiguresToAllPlayers() {
		Random random = new Random();
		Simulation.allFigures.clear();
		Simulation.figuresArray.clear();
		for (var onePlayer : Simulation.playersInGame) {
			onePlayer.getFigures().clear();
			onePlayer.setCurrentFigure(null);
			onePlayer.getPermanentFigures().clear();
			for (int j = 0; j < Player.NUMBER_OF_FIGURES; j++) {
				int num = random.nextInt(3);
				Figure figure = null;
				if (num == 0)
					figure = new OrdinaryFigure(onePlayer.getPlayerColor());
				else if (num == 1)
					figure = new FlyingFigure(onePlayer.getPlayerColor());
				else
					figure = new SuperFastFigure(onePlayer.getPlayerColor());
				Simulation.allFigures.put(figure, onePlayer);
				Simulation.figuresArray.add(figure);
				try {
					onePlayer.addPlayerFigures(figure);
				} catch (IllegalColorException ex) {
					Program.myLogger.logger.warning(ex.getMessage());
				}
			}
		}
	}

	private static void formDiamondCircleMap() {
		Simulation.diamondCircleMap = new Object[Simulation.mapDimension][Simulation.mapDimension];
	}

	public static Object getMapFromPossition(int value) {
		int i = 0, j = 0;
		Object object = null;
		try {
			if (value % mapDimension == 0) {
				i = value / mapDimension - 1;
				j = mapDimension - 1;
			} else {
				i = value / Simulation.mapDimension;
				j = value % Simulation.mapDimension - 1;
			}
			object = Simulation.diamondCircleMap[i][j];
		} catch (IndexOutOfBoundsException ex) {
			Program.myLogger.logger.warning(ex.getMessage());
		}
		return object;
	}

	public static void setMapOnPossition(int value, Object obj) {
		try {
			int i, j;
			if (value % mapDimension == 0) {
				i = value / mapDimension - 1;
				j = mapDimension - 1;
			} else {
				i = value / Simulation.mapDimension;
				j = value % Simulation.mapDimension - 1;
			}
			Simulation.diamondCircleMap[i][j] = obj;
		} catch (IndexOutOfBoundsException ex) {
			Program.myLogger.logger.warning(ex.getMessage());
		}
	}
//	public static void main(String args[]) {
//		Simulation.startupSettings(7, 2);
//		Simulation simulation = new Simulation();
//		simulation.start();
//	}

}
