public class DictEntry {

	/* Attributes */

	private String word;
	private String definition;
	private int type;

	/**
	 * A construction which returns a new DictEntry with the specified word,
	 * definition, and type. If the type is 1, then the text defining the word
	 * is stored in the definition String. If the type is 2 or 3, then
	 * definition stores the name of the corresponding multimedia file
	 * 
	 * @param word
	 * @param definition
	 * @param type
	 */
	public DictEntry(String word, String definition, int type) {
		this.word = word;
		this.definition = definition;
		this.type = type;
	}

	/**
	 * @return Returns the word in the DictEntry
	 */
	public String word() {
		return word;
	}

	/**
	 * @return Returns the definition in the DictEntry
	 */
	public String definition() {
		return definition;
	}

	/**
	 * if the type is 1, then return string. If the type is 2 or 3
	 * 
	 * @return Returns the type in the DictEntry
	 */
	public int type() {
		return type;
	}

}
