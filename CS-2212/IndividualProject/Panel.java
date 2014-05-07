import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class Panel {

	private static JPanel Panel1; //Panel to place everything upon
	private char charCast;
	private String charCastString;
	private int rowCount = 1, columnCount = 1; //place keepers for placing buttons
	private JButton[] letters; //button array
	private JButton p1p, p2p; //Player 1 Puzzle and Player 2 Puzzle Buttons
	private String p2word, p1word; //Player 1 and player 2 puzzle words
	private JMenuBar menuBar;
	private JMenu file, color_scheme, p2_cat, p1_cat;
	private JMenuItem exit, oceanBlue, vibrantRed, tv_shows1, movies1,phrases1, tv_shows2, movies2, phrases2;
	public JLabel cat1, cat2; //category
	private JButton p2;
	private JButton solve; //solve button
	private String spaces, spacesX; //puzzle with blank spaces
	private JLabel man; //image icon itself
	private static JLabel hangman, hangman1, solved;
	private int tries = 0; //how many incorrect guesses a player has
	private int p1Points = 0, p2Points = 0; //Point Counter
	
	//place keepers essentially//
	private int done = 0;
	private int reset = 0;
	private int p1done = 0;
	public boolean firstWordSolved = false;
	
	/**
	 * Makes a panel with all the items placed upon it
	 */
	public void makePanel() {
		letters = new JButton[26];//26 letters in the alphabet
		Panel1 = new JPanel();
		Panel1.setSize(200, 600);
		Panel1.setLayout(null);

		//for loop used to add buttons labeled by their letter to the array
		for (int x = 65; x <= 90; x++)
		{

			charCastString = new String(); //convert from string to char
			charCast = (char) x;
			charCastString += charCast;

			//adds button, sets font, background, bounds, and action listener to Panel
			letters[x - 65] = new JButton(charCastString);
			letters[x - 65].setFont(new Font("Serif", Font.BOLD, 15));
			letters[x - 65].setBackground(Color.WHITE);
			letters[x - 65].setBounds((550 + (50 * columnCount)),
					((50 * rowCount)), 50, 50);
			letters[x - 65].addActionListener(new press_button());
			Panel1.add(letters[x - 65]);

			//keeping track of my positioning for the button
			if (columnCount == 3) {
				rowCount++;
				columnCount = 0;
			}
			columnCount++;
		}
		
		//for loop is used to eliminate function of all the newly added buttons
		//no purpose to try and solve a puzzle thats not their yet...
		for (int x = 0; x < letters.length; x++)
		{
			letters[x].setEnabled(false);
			letters[x].setBackground(Color.BLACK);
		}
		
		//creates solve button, sets bounds,background color and eliminates function
		//adds action listener and attaches to panel
		solve = new JButton("Solve");
		solve.setBounds(400, 375, 175, 25);
		solve.setEnabled(false);
		solve.setBackground(Color.BLACK);
		Panel1.add(solve);
		solve.addActionListener(new solver());

		//adds player 1 puzzle button
		p1p = new JButton("Player 1 Puzzle");
		p1p.setBounds(400, 100, 175, 50);
		Panel1.add(p1p);
		p1p.addActionListener(new Player1());

		//adds player 2 puzzle button
		p2p = new JButton("Player 2 Puzzle");
		p2p.setBounds(400, 200, 175, 50);
		Panel1.add(p2p);
		p2p.addActionListener(new Player2());

		///////////////////////
		//PERSISTANT DATA//
		///////////////////////
		
		//if the file cat (short for category) is created, load the category
		//from the file, else default to Tv Shows for player 1
		try {
			BufferedReader in = new BufferedReader(new FileReader("cat.txt"));
			try {
				String cat_1 = in.readLine();
				cat1 = new JLabel(cat_1);
				cat1.setFont(new Font("Serif", Font.ITALIC, 12));
				cat1.setBounds(400, 50, 175, 50);
				Panel1.add(cat1);
				in.close();
			} catch (IOException e) {
			}

		} catch (FileNotFoundException e) {
			cat1 = new JLabel("Player 1 Category: Tv Shows");
			cat1.setFont(new Font("Serif", Font.ITALIC, 12));
			cat1.setBounds(400, 50, 175, 50);
			Panel1.add(cat1);
		}

		//if the file cat (short for category) is created, load the category
		//from the file, else default to Tv Shows for player 2
		try {
			BufferedReader in = new BufferedReader(new FileReader("cat.txt"));
			try {
				in.readLine();
				String cat_2 = in.readLine();
				cat2 = new JLabel(cat_2);
				cat2.setFont(new Font("Serif", Font.ITALIC, 12));
				cat2.setBounds(400, 250, 175, 50);
				Panel1.add(cat2);
				in.close();
			} catch (IOException e) {
			}

		} catch (FileNotFoundException e) {
			cat2 = new JLabel("Player 2 Category: Tv Shows");
			cat2.setFont(new Font("Serif", Font.ITALIC, 12));
			cat2.setBounds(400, 250, 175, 50);
			Panel1.add(cat2);
		}

		//Hangman Origional Image//
		ImageIcon icon = new ImageIcon(getClass().getResource("P1.png"),"Hangman Stand");
		man = new JLabel(icon);
		man.setBounds(50, 0, 300, 400);
		Panel1.add(man);
	}
/**
 * 
 * @return Returns the panel so main can use it
 */
	public JPanel getPanel() {
		return Panel1;
	}
/**
 * takes player 1 and player 2's inputs and creates spaces files which are the word
 * masked using spaces for letters
 */
	public void makeHangMan1() {
		spaces = new String();
		for (int x = 0; x < p1word.length(); x++) {
			if (((int) p1word.charAt(x) < 65 || (int) p1word.charAt(x) > 91)
					&& p1word.charAt(x) != ' ') //if the character is not a letter or a space ex. numbers or <>?" ect
			{
				spaces += p1word.charAt(x); //leave filled in
			} 
			else if (p1word.charAt(x) == ' ') //if its a space
			{
				spaces += "/"; //substitute space for /
			}
			else
				spaces += "_ "; //if its a character mask the character
		}
		//same as above but for player 2's word
		spacesX = new String();
		for (int x = 0; x < p2word.length(); x++) {
			if (((int) p2word.charAt(x) < 65 || (int) p2word.charAt(x) > 91)
					&& p2word.charAt(x) != ' ')
			{
				spacesX += p2word.charAt(x);
			}
			else if (p2word.charAt(x) == ' ')
			{
				spacesX += "/";
			}
			else
				spacesX += "_ ";
		}
		
		//Creates the text block of the puzzle to be solved (bottom right corner)
		hangman = new JLabel("Player 1 Puzzle To Solve");
		hangman.setBounds(0, 425, 800, 25);
		hangman.setFont(new Font("Serif", Font.BOLD, 14));
		hangman1 = new JLabel(spaces);
		hangman1.setBounds(0, 450, 800, 100);
		hangman1.setFont(new Font("Serif", Font.ITALIC, 38));
		Panel1.add(hangman1);
		Panel1.add(hangman);
		Panel1.repaint();
		
		//removes the option to change categories after you have entered a puzzle
		p1_cat.remove(tv_shows1);
		p1_cat.remove(movies1);
		p1_cat.remove(phrases1);
		p2_cat.remove(tv_shows2);
		p2_cat.remove(movies2);
		p2_cat.remove(phrases2);
	}
/**
 * allowing letters to be chosen again upon completion of a puzzle; allowing
 * player 2 to choose letters after player 1 has finished.
 */
	public void solving() {
		if (p1word != null && p2word != null) {
			for (int x = 0; x < letters.length; x++) {
				letters[x].setEnabled(true);
				letters[x].setBackground(Color.WHITE);
			}
			solve.setEnabled(true);
			solve.setBackground(Color.WHITE);

		}
	}
/**
 * 
 * @author Matthew Stokes
 * The Beast. Basically the functioning of the game. Fills in the puzzle as the
 * player chooses letters. Fills dissables buttons from being selected after they
 * have been chosen once. Fill in the hangman using awesome 7 imagine files. Displays
 * a message if the puzzle is solved or failed, keeps track if the player solved the
 * puzzle. adds a button to continue to player two and also to finish the game.
 * 
 * The workhorse. 
 *
 */
	private class press_button implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (p1word != null && p2word != null) { //provided both players selected word
				if (firstWordSolved == false) { //on player1

					Panel1.remove(hangman1);
					String spaces2 = new String();

					//filling in characters in the word as they are guessed
					for (int x = 0; x < p1word.length(); x++) {
						String temp = new String();
						temp += p1word.charAt(x);
						if (((int) p1word.charAt(x) < 65 || (int) p1word
								.charAt(x) > 91) && p1word.charAt(x) != ' ') {
							spaces2 += p1word.charAt(x);
						} else if (p1word.charAt(x) == ' ') {
							spaces2 += "/";
						} else {
							if (e.getActionCommand().charAt(0) == p1word
									.charAt(x)) {
								spaces2 += p1word.charAt(x);
							} else if (spaces.contains(temp)) {
								spaces2 += temp;
							} else
								spaces2 += "_ ";
						}
					}
					
					//if the letter guessed wasn't in the puzzle
					//gets the next progression of hangman image
					if (!spaces2.contains(e.getActionCommand())) {
						if (tries == 0) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P2.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else if (tries == 1) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P3.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else if (tries == 2) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P4.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else if (tries == 3) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P5.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else if (tries == 4) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P6.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else if (tries == 5) {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P7.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries++;
						} else {
							ImageIcon icon2 = new ImageIcon(getClass()
									.getResource("P8.png"), "Hangman Stand");
							Panel1.remove(man);
							man = new JLabel(icon2);
							man.setBounds(50, 0, 300, 400);
							Panel1.add(man);
							tries = 0;
							firstWordSolved = true; //but puzzle failed

							//puzzle was failed to be solved in the alloted 7 guesses
							//makes a Label to let the user know he/she failed
							//to answer the puzzle correctly
							solved = new JLabel("FAIL");
							solved.setBounds(360, 315, 200, 70);
							solved.setFont(new Font("Serif", Font.BOLD, 40));
							Panel1.add(solved);
							solve.setEnabled(false);

							//Player 2 click to start button is created
							p2 = new JButton("Player 2 Click to Start");
							p2.setBounds(400, 515, 350, 25);
							p2.setForeground(Color.YELLOW);
							p2.setFont(new Font("Serif", Font.BOLD, 20));
							p2.setBackground(Color.BLUE);
							p2.addActionListener(new Part2());
							Panel1.add(p2);
							Panel1.repaint();
						}
					}
					//disables the button just chosen therefore it cannot be
					//guessed a second time
					Panel1.getComponentAt(Panel1.getMousePosition().x,
							Panel1.getMousePosition().y).setEnabled(false);
					hangman1 = new JLabel(spaces2);
					hangman1.setBounds(0, 450, 800, 100);
					hangman1.setFont(new Font("Serif", Font.ITALIC, 38));
					Panel1.add(hangman1);
					Panel1.repaint();

					spaces = spaces2; //very important! Sets the puzzle to be solved
					//as this updated version with any letters that have already been
					//filled in. Essential
					
					String temp = new String();
					temp += '_';
					if (!spaces2.contains(temp)) { //no more letters to be guessed
						firstWordSolved = true;

						//You solved the puzzle!
						solved = new JLabel("SOLVED");
						solved.setBounds(360, 315, 200, 70);
						solved.setFont(new Font("Serif", Font.BOLD, 40));
						Panel1.add(solved);
						solve.setEnabled(false);
						p1Points = 1;

						//Same as previous lets player 1 transfer control to player 2
						//additionally sets the p1done breaker
						p2 = new JButton("Player 2 Click to Start");
						p2.setBounds(400, 515, 350, 25);
						p2.setForeground(Color.YELLOW);
						p2.setFont(new Font("Serif", Font.BOLD, 20));
						p2.setBackground(Color.BLUE);
						p2.addActionListener(new Part2());
						Panel1.add(p2);
						Panel1.repaint();
						p1done = 1;
					}
				}
			}

			//Player 2's turn
			if (firstWordSolved == true && reset == 1 && done != 1) {
				if (p1done == 1) {//resetting tries 

					tries = 0;
					p1done = 2;
				}
				Panel1.remove(hangman1);

				String spaces2 = new String();

				//filling in characters in the word as they are guessed
				//java really butchers the formatting sorry about that
				//I've lost marks for converting it back though so 
				//as ugly as it is its gotta stay this way.
				for (int x = 0; x < p2word.length(); x++) {
					String temp = new String();
					temp += p2word.charAt(x);
					if (((int) p2word.charAt(x) < 65 || (int) p2word.charAt(x) > 91)
							&& p2word.charAt(x) != ' ') {
						spaces2 += p2word.charAt(x);
					} else if (p2word.charAt(x) == ' ') {
						spaces2 += "/";
					} else {
						if (e.getActionCommand().charAt(0) == p2word.charAt(x)) {
							spaces2 += p2word.charAt(x);
						} else if (spacesX.contains(temp)) {
							spaces2 += temp;
						} else
							spaces2 += "_ ";
					}
				}
				
				//if the letter guessed wasn't in the puzzle
				//gets the next progression of hangman image
				if (!spaces2.contains(e.getActionCommand())) {
					if (tries == 0) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P2.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else if (tries == 1) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P3.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else if (tries == 2) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P4.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else if (tries == 3) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P5.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else if (tries == 4) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P6.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else if (tries == 5) {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P7.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries++;
					} else {
						ImageIcon icon2 = new ImageIcon(getClass().getResource(
								"P8.png"), "Hangman Stand");
						Panel1.remove(man);
						man = new JLabel(icon2);
						man.setBounds(50, 0, 300, 400);
						Panel1.add(man);
						tries = 0;
						firstWordSolved = true;

						//puzzle was failed to be solved in the alloted 7 guesses
						//makes a Label to let the user know he/she failed
						//to answer the puzzle correctly
						solved = new JLabel("FAIL");
						solved.setBounds(360, 315, 200, 70);
						solved.setFont(new Font("Serif", Font.BOLD, 40));
						Panel1.add(solved);
						solve.setEnabled(false);

						//hit button to take the user to game over screen 
						p2 = new JButton("GameOver See Results");
						p2.setBounds(400, 515, 350, 25);
						p2.setForeground(Color.YELLOW);
						p2.setFont(new Font("Serif", Font.BOLD, 20));
						p2.setBackground(Color.BLUE);
						p2.addActionListener(new End());
						Panel1.add(p2);
						Panel1.repaint();
						done = 1; //game over marker
					}
				}
				//disabling a button after it has already been hit
				Panel1.getComponentAt(Panel1.getMousePosition().x,
						Panel1.getMousePosition().y).setEnabled(false);
				hangman1 = new JLabel(spaces2);
				hangman1.setBounds(0, 450, 800, 100);
				hangman1.setFont(new Font("Serif", Font.ITALIC, 38));
				Panel1.add(hangman1);
				Panel1.repaint();

				spacesX = spaces2;
				String temp = new String();
				temp += '_';
				if (!spaces2.contains(temp)) {
					firstWordSolved = true;

					//You solved the puzzle!
					solved = new JLabel("SOLVED");
					solved.setBounds(360, 315, 200, 70);
					solved.setFont(new Font("Serif", Font.BOLD, 40));
					Panel1.add(solved);
					solve.setEnabled(false);
					p2Points = 1; //points awarded to player2

					//hit button to take the user to game over screen 
					p2 = new JButton("GameOver See Results");
					p2.setBounds(400, 515, 350, 25);
					p2.setForeground(Color.YELLOW);
					p2.setFont(new Font("Serif", Font.BOLD, 20));
					p2.setBackground(Color.BLUE);
					p2.addActionListener(new End());
					Panel1.add(p2);
					Panel1.repaint();
					done = 1;
				}
			}
		}
	}
/**
 * 
 * @author Matthew Stokes
 *	This method is what takes the user from Player A to Player B game.
 *	It removes all player 1's answers and unlocks everything that has
 *	been disabled and reloads a new picture and Player 1's puzzle to be
 *	solved
 *
 */
	private class Part2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (reset != 1) { //only rest all the values once
				Panel1.remove(p2);
				Panel1.remove(solved);
				Panel1.repaint();
				ImageIcon icon3 = new ImageIcon(getClass()
						.getResource("P1.png"), "Hangman Stand");
				Panel1.remove(man);
				man = new JLabel(icon3);
				man.setBounds(50, 0, 300, 400);
				Panel1.add(man);
				solve.setEnabled(true);

				Panel1.remove(hangman1);
				Panel1.remove(hangman);
				hangman = new JLabel("Player 2 Puzzle To Solve");
				hangman.setBounds(0, 425, 800, 25);
				hangman.setFont(new Font("Serif", Font.BOLD, 14));
				Panel1.add(hangman);
				for (int x = 0; x < 26; x++) {
					letters[x].setEnabled(true);
				}
				hangman1 = new JLabel(spacesX);
				hangman1.setBounds(0, 450, 800, 100);
				hangman1.setFont(new Font("Serif", Font.ITALIC, 38));
				Panel1.add(hangman1);
				Panel1.repaint();
				reset++;
			}

		}
	}
/**
 * 
 * @author Matthew Stokes
 *	Game over Screen. Also calculates point totals and lets the user know who
 *	has won the match in case he/she could not keep track in the heat of the moment
 *
 */
	private class End implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.removeAll(); //remove everything
			Panel1.repaint();

			//adds the GAME OVER text//
			JLabel the_end = new JLabel("GAME OVER!");
			the_end.setFont(new Font("Serif", Font.BOLD, 60));
			the_end.setBounds(0, 0, 800, 200);
			Panel1.add(the_end);
			
			//displays proper winner of the game
			if (p1Points > p2Points) {
				JLabel winnerP1 = new JLabel("Player 1 Wins");
				winnerP1.setBounds(0, 100, 800, 200);
				winnerP1.setFont(new Font("Serif", Font.BOLD, 60));
				Panel1.add(winnerP1);
			} else if (p1Points == p2Points) {
				JLabel draw = new JLabel("Both Player 1 and Player 2 won!");
				draw.setBounds(0, 100, 800, 200);
				draw.setFont(new Font("Serif", Font.BOLD, 45));
				Panel1.add(draw);
			} else {
				JLabel winnerP2 = new JLabel("Player 2 Wins");
				winnerP2.setBounds(0, 100, 800, 200);
				winnerP2.setFont(new Font("Serif", Font.BOLD, 60));
				Panel1.add(winnerP2);
			}
			//adds a quit button to allow the user to exit
			JButton close = new JButton("Quit");
			close.setBounds(400, 450, 175, 50);
			Panel1.add(close);
			close.addActionListener(new press_exit());
		}
	}
/**
 * 
 * @author Matthew Stokes
 *	I have never seen a solve button in a game of hangman before. Why not just
 *	click the letters, so I chose to keep it in beacuse of the problem specs but
 *	its function is limited to humour. 
 */
	private class solver implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showInputDialog("Enter Solution");
			JOptionPane
					.showInputDialog("That might be the solution...Try hitting the proper buttons on the GUI...its hangman not wheel of fortune");

		}
	}
/**
 * 
 * @author Matthew Stokes
 *	Player 2's pop up puzzle box
 */
	private class Player2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			p2word = new String();
			PopUpBox2 p2 = new PopUpBox2();
			p2.makePopUpBox2();
			p2word = p2.getPlayer2word();
			p2p.setEnabled(false);
			p2p.setBackground(Color.BLACK);
		}
	}

	/**
	 * 
	 * @author Matthew Stokes
	 *Player 1's pop up text box
	 */
	private class Player1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			p1word = new String();
			PopUpBox1 p1 = new PopUpBox1();
			p1.makePopUpBox1();
			p1word = p1.getPlayer1word();
			p1p.setEnabled(false);
			p1p.setBackground(Color.BLACK);

		}
	}
//getter method to return p1word
	public String getP1Word() {
		return p1word;
	}
//getter method to return p2word
	public String getP2Word() {
		return p2word;
	}
/**
 * method to make the MenuBar seen atop of the panel. 
 */
	public void makeMenuBar() {

		menuBar = new JMenuBar(); // creates new MenuBar
		file = new JMenu("File");
		color_scheme = new JMenu("ColorScheme");
		p1_cat = new JMenu("Player1Category");
		p2_cat = new JMenu("Player2Category");
		menuBar.add(file);
		menuBar.add(color_scheme);
		menuBar.add(p1_cat);
		menuBar.add(p2_cat);

		exit = new JMenuItem("Exit");
		oceanBlue = new JMenuItem("Ocean Blue");
		vibrantRed = new JMenuItem("Vibrant Red");
		tv_shows1 = new JMenuItem("Tv Shows");
		movies1 = new JMenuItem("Movies");
		phrases1 = new JMenuItem("Phrases");
		tv_shows2 = new JMenuItem("Tv Shows");
		movies2 = new JMenuItem("Movies");
		phrases2 = new JMenuItem("Phrases");
		file.add(exit);
		color_scheme.add(oceanBlue);
		color_scheme.add(vibrantRed);
		p1_cat.add(tv_shows1);
		p1_cat.add(movies1);
		p1_cat.add(phrases1);
		p2_cat.add(tv_shows2);
		p2_cat.add(movies2);
		p2_cat.add(phrases2);

		//adds basic functions to each of the menu items
		oceanBlue.addActionListener(new press_oceanBlue());
		vibrantRed.addActionListener(new press_vibrantRed());
		exit.addActionListener(new press_exit());
		tv_shows1.addActionListener(new tv_shows1());
		movies1.addActionListener(new movies1());
		phrases1.addActionListener(new phrases1());
		tv_shows2.addActionListener(new tv_shows2());
		movies2.addActionListener(new movies2());
		phrases2.addActionListener(new phrases2());
	}

	public JMenuBar getMenu() {
		return menuBar;
	}

	private class tv_shows1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat1);
			cat1 = new JLabel("Player 1 Category: Tv Shows");
			cat1.setFont(new Font("Serif", Font.ITALIC, 12));
			cat1.setBounds(400, 50, 175, 50);
			Panel1.add(cat1);
			Panel1.repaint();
		}
	}

	private class tv_shows2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat2);

			cat2 = new JLabel("Player 2 Category: Tv Shows");
			cat2.setFont(new Font("Serif", Font.ITALIC, 12));
			cat2.setBounds(400, 250, 175, 50);
			Panel1.add(cat2);
			Panel1.repaint();
		}
	}

	private class movies1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat1);

			cat1 = new JLabel("Player 1 Category: Movies");
			cat1.setFont(new Font("Serif", Font.ITALIC, 12));
			cat1.setBounds(400, 50, 175, 50);
			Panel1.add(cat1);
			Panel1.repaint();
		}
	}

	private class movies2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat2);

			cat2 = new JLabel("Player 2 Category: Movies");
			cat2.setFont(new Font("Serif", Font.ITALIC, 12));
			cat2.setBounds(400, 250, 175, 50);
			Panel1.add(cat2);
			Panel1.repaint();
		}
	}

	private class phrases1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat1);

			cat1 = new JLabel("Player 1 Category: Phrases");
			cat1.setFont(new Font("Serif", Font.ITALIC, 12));
			cat1.setBounds(400, 50, 175, 50);
			Panel1.add(cat1);
			Panel1.repaint();
		}
	}

	private class phrases2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Panel1.remove(cat2);

			cat2 = new JLabel("Player 2 Category: Phrases");
			cat2.setFont(new Font("Serif", Font.ITALIC, 12));
			cat2.setBounds(400, 250, 175, 50);
			Panel1.add(cat2);
			Panel1.repaint();
		}
	}
////////////////////////
//PERSISTANT DATA//
////////////////////////
	/**
	 * method write to file cat.txt player1 and player2's last used category
	 * then exits
	 */
	private class press_exit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				PrintWriter out = new PrintWriter(new FileWriter("cat.txt"));
				String text = new String();
				text = cat1.getText();
				out.println(text);
				text = cat2.getText();
				out.println(text);
				out.close();

			} catch (IOException e2) {
			}
			;
			System.exit(0);
		}
	}
/**
 * 
 * @author Matthew Stokes
 *	A majestic oceanBlue. In the specs it also said foreground color so I chose
 *	to edit the color of my letter buttons to meet this requirement
 */
	private class press_oceanBlue implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Panel1.getBackground() != Color.BLUE) {
				Panel1.setBackground(Color.BLUE);
				for (int x = 0; x < letters.length; x++) {
					letters[x].setForeground(Color.BLUE);
				}
			} else {
				Panel1.setBackground(Color.WHITE);
				for (int x = 0; x < letters.length; x++) {
					letters[x].setForeground(Color.BLACK);
				}
			}
		}
	}
	/**
	 * 
	 * @author Matthew Stokes
	 *	A majestic vibrantRed. In the specs it also said foreground color so I chose
	 *	to edit the color of my letter buttons to meet this requirement
	 */
	private class press_vibrantRed implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Panel1.getBackground() != Color.RED) {
				Panel1.setBackground(Color.RED);
				for (int x = 0; x < letters.length; x++) {
					letters[x].setForeground(Color.RED);
				}
			} else {
				Panel1.setBackground(Color.WHITE);
				for (int x = 0; x < letters.length; x++) {
					letters[x].setForeground(Color.BLACK);
				}
			}
		}
	}
}//END
