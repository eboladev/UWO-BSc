import java.io.*;

public class Compress {

	public static void main(String[] args) throws Exception {

		/* Attributes */
		int collisions =0;
		String read = new String();
		BufferedInputStream in;
		BufferedOutputStream out;
		in = new BufferedInputStream(new FileInputStream(args[0]));
		out = new BufferedOutputStream(new FileOutputStream(args[0] + ".zzz"));


		/*
		 * Opening input file and reading all characters in the string storing
		 * them as String variable read
		 */
		if (args.length > 1)
		{
			System.out.println("Too Many Arguments, Reduce Numbers of Arguments to 1");
			return;
		}
		else
		{
			try
			{
				for (int i = 0; i < in.available();)
				{
					read += (char) in.read();
				}
			}
			catch (FileNotFoundException ee)
			{
				System.out.println("File Not Found");
			}
		}

		/* Initialize the Dictionary with all the ASCII codes" */
		Dictionary Dict = new Dictionary(2999);
		// Wanted to choose a number to get collisions below 2000
		//Technically there is no limitation to memory for this project so this number could be much
		//higher which would dramatically decrease the number of collisions
		for (int i = 0; i < 256; i++) {
			String insert = new String();
			insert += (char) i;
			DictEntry ASCII = new DictEntry(insert, i);
			collisions += Dict.insert(ASCII);
			
		}

		/* Algorithm */
  	DictEntry insert_entry = new DictEntry(null, 0);
		String found = new String();
		boolean escape = false;
		int add = 1;

		for (int i = 0; i <= read.length() - 1;) // traverse through input string
		{
			found = new String();
			String first = new String();
			first += read.charAt(i); //Char at current position in the string
			add = 1; //reset add
			escape = false; //reset escape

			while (escape == false) //looping through length of string thats found in dictionary starting at 2 char
			{
				if (i == read.length() - 1) //if its the last letter
				{
					found = first; 
				} 
				else
				{
					first += read.charAt(i + add); //add the second character since we know single characters are in the dictionary following the ASCII insertion
				}
				if (Dict.find(first) != null && read.length() > (i + add + 1)) //if its in the dictionary already and we are not at the end of the file
				{
					add++; 
				} 
				else
					escape = true; //escape while loop
			}
			if (Dict.numElements() < 4096) //restriction on the total number of elements in the dictionary
			{
				insert_entry = new DictEntry(first, Dict.numElements());
				collisions += Dict.insert(insert_entry); //adding the current element to the dictionary				
			}
			for (int z = 0; z < first.length() - 1; z++) // to get the string first minus the last character (with the last character it was the dictionary entry, without it is the largest string to get the code for
			{
				found += first.charAt(z);
			}

			MyOutput output = new MyOutput();
			output.output(Dict.find(found).getCode(), out); //output code
		
			i = i + found.length(); //increase position based on length of the string found

		}
		MyOutput output = new MyOutput();
		output.flush(out); //flush
		in.close(); //close in
		out.close(); //close out
		System.out.println("Total Number of Collisions: " + collisions);
	} // Main end
} // end

