/**  
 * @author Matthew Stokes
 */
/*************************************************************************
 * Compilation: javac Soundex.java Execution: java surname1 surname2
 * 
 * 
 * % java Soundex Wohrzhick Warzick W622: Wohrzhick W622: Warzick
 * 
 * % java Soundex Smith Smyth S530: Smith S530: Smyth
 * 
 * % java Soundex Washington Lee W252: Washington L000: Lee
 * 
 * % java Soundex Pfister Jackson P236: Pfister J250: Jackson
 * 
 * % java Soundex Scott Numbers S300: Scott N516: Numbers
 * 
 * 
 * Note: we ignore the "Names with Prefix" and "Constant Separator" rules from
 * http://www.archives.gov/research_room/genealogy/census/soundex.html
 * 
 *************************************************************************/
public class SoundexPage extends Page {

	public String text = "";

	public SoundexPage(String address) {
		super(address);
	}

	public static String getSoundex(String s) {
		char[] x = s.toUpperCase().toCharArray();
		char firstLetter = x[0];

		// convert letters to numeric code
		for (int i = 0; i < x.length; i++) {
			switch (x[i]) {
			case 'B':
			case 'F':
			case 'P':
			case 'V': {
				x[i] = '1';
				break;
			}

			case 'C':
			case 'G':
			case 'J':
			case 'K':
			case 'Q':
			case 'S':
			case 'X':
			case 'Z': {
				x[i] = '2';
				break;
			}

			case 'D':
			case 'T': {
				x[i] = '3';
				break;
			}

			case 'L': {
				x[i] = '4';
				break;
			}

			case 'M':
			case 'N': {
				x[i] = '5';
				break;
			}

			case 'R': {
				x[i] = '6';
				break;
			}

			default: {
				x[i] = '0';
				break;
			}
			}
		}

		// remove duplicates
		String output = "" + firstLetter;
		for (int i = 1; i < x.length; i++)
			if (x[i] != x[i - 1] && x[i] != '0')
				output += x[i];

		// pad with 0's or truncate
		output = output + "0000";
		return output.substring(0, 4);
	}

	public boolean containsText(String text) {

		if (this.text.contains(text.toLowerCase())
				|| (getSoundex(text).equals(getSoundex(this.text)))) {
			return true;
		}
		return false;
	}
}
