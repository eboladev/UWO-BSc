import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Query {

	public static void main(String[] args) {
		OrderedDictionary dictionary;
		String word1 = null, word2 = null;
		int count;

		/* Set up OrderedDictionary */
		dictionary = new OrderedDictionary();
		try {
			BufferedReader in = new BufferedReader(new FileReader(args[0]));
			String word = in.readLine().toLowerCase(); // converts to lower case
			String definition;
			while (word != null) // while still words to add to dictionary
			{

				definition = in.readLine();
				if (definition != null) {
					definition = definition.toLowerCase(); // converts to lower
					// case
				}
				if (definition.endsWith(".wav") || definition.endsWith(".mid")) {
					dictionary.insert(word, definition, 2);
				} else if (definition.endsWith(".jpg")
						|| definition.endsWith(".gif")) {
					dictionary.insert(word, definition, 3);
				} else {
					dictionary.insert(word, definition, 1);
				}
				word = in.readLine();
				if (word != null) {
					word = word.toLowerCase(); // converts to lower case
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot open file");
		} catch (DictionaryException e) {
			System.out.println("Word Already In Dictionary");
		}
		
    
    while (1 > 0) // infinite loop
		{
			StringReader keyboard = new StringReader();
			String line = keyboard.read("Enter Next Command: ");
			StringTokenizer command = new StringTokenizer(line);
			count = command.countTokens();

			if (count < 1) // if no commands
			{
				System.out.println("Too Few Arguments");
			} else if (count == 1) // if 1 command
			{
				word1 = command.nextToken();
				if (word1.compareTo("end") == 0) // check if 1 command is end
				{
					break;
				} else
					System.out.println("Too Few Arguments");
			} else if (count > 2) {
				System.out.println("Too Many Arguments");
			} else // exactly 2 commands
			{
				word1 = command.nextToken();
				word2 = command.nextToken();

				/* DEFINE */
				if (word1.compareTo("define") == 0) {
					if (dictionary.findType(word2) == 2) // if sound file
					{
						try {
							SoundPlayer y = new SoundPlayer();
							String definition = dictionary.findWord(word2);
							y.play(definition);
						} catch (Exception e) {
							System.out.println("Cannot play sound");
						}
					} else if (dictionary.findType(word2) == 3) // if picture
					// file
					{
						try {
							PictureViewer x = new PictureViewer();
							String definition = dictionary.findWord(word2);
							x.show(definition);
						} catch (Exception e) {
							System.out.println("Cannot open picture");
						}
					} else if (dictionary.findType(word2) == 1) // if regular
						// definition
						System.out.println(dictionary.findWord(word2));
					else {
						System.out.println("Word not in dictionary");
					}
				}

				/* DELETE */
				else if (word1.compareTo("delete") == 0) {
					try {
						dictionary.remove(word2);
					} catch (DictionaryException e) {
						System.out.println(word2 + " is not the in dictionary");
					}
				}
				/* LIST */
				else if (word1.compareTo("list") == 0) {
					try {
						dictionary.insert(word2, "Place Holder", 1); // set
						// placeholder
						String listing = word2;
						while (dictionary.successor(listing).startsWith(word2)) {
							listing = dictionary.successor(listing);
							System.out.print(listing + "  ");
						}
						dictionary.remove(word2); // removing placeholder
						System.out.println();
					} catch (DictionaryException e) // if word already in
													// dictionary
													// ex: coin coined
					{
						String listing = word2;
						System.out.print(listing);
						while (dictionary.successor(listing).startsWith(word2)) {
							listing = dictionary.successor(listing);
							System.out.print(listing + "  ");
						}
						System.out.println();
					}

				}

				/* NEXT */
				else if (word1.compareTo("next") == 0) {
					 if(dictionary.findWord(word2) == "") // word not in dictionary
						{
							System.out.println(word2 + " is not in the dictionary");
						}
					 else if (dictionary.successor(word2) != "") // if returns word
					{
						System.out.println(dictionary.successor(word2));
					} 

					else if (dictionary.successor(word2) == "")
					// if doesn't return word
					{
						System.out.println(word2
								+ " is the last word in the dictionary");
					}
				}

				/* PREVIOUS */
				else if (word1.compareTo("previous") == 0) {
					 if(dictionary.findWord(word2) == "") // word not in dictionary
						{
							System.out.println(word2 + " is not in the dictionary");
						}
					 else if (dictionary.predecessor(word2) != "")
					// finds and returns word
					{
						System.out.println(dictionary.predecessor(word2));
					} else if (dictionary.predecessor(word2) == "")
						// if doesn't return word
					{
						System.out.println(word2
								+ " is the first word in the dictionary");
					}
				} else {
					System.out.println("Invalid First Argument");
				}
			}

		} // end while
	} // end main
}// end document
