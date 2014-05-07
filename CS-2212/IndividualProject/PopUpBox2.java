import javax.swing.JOptionPane;

public class PopUpBox2 {

	private int outputInt;
	private char outputChar;
	private String outputString;
	private String input;

	/**
	 * the Pop up box that comes up when Player 2 Puzzle is hit. Additionally
	 * the String is converted into all capital letters
	 */
	public void makePopUpBox2() {
		input = new String();
		outputString = new String();

		while (true) {
			input = JOptionPane.showInputDialog("Player 2 Enter a Puzzle");
			if (input.length() > 100) { //following specs of no greater then 100 characters
				System.out
						.println("Too Long of a Puzzle, try something shorter then 100 characters");
				System.exit(0);
			}
			if (input.length() != 0)
				break;
		}

		for (int x = 0; x < input.length(); x++) {//converting to upper case
			if ((int) input.charAt(x) >= 97 && (int) input.charAt(x) <= 122) {
				outputInt = (int) input.charAt(x) - 32;
				outputChar = (char) outputInt;
				outputString += outputChar;
			} else {
				outputString += input.charAt(x);
			}
		}
	}

	public String getPlayer2word() {
		return outputString;
	}
}