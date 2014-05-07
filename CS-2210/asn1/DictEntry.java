public class DictEntry {

	/* Attributes */

	private String key;
	private int code;

	/**
	 * A constructor which returns a new DictEntry with the specified key and
	 * code
	 * 
	 * @param key
	 * @param code
	 */
	public DictEntry(String key, int code) {
		this.key = key;
		this.code = code;
	}

	/**
	 * A method to get the key for a specific dictionary entry
	 * 
	 * @return Returns key1 the key in the DictEntry
	 */
	public String getKey() {

		return key;
	}

	/**
	 * A method to get the code for a specific dictionary entry
	 * 
	 * @return Returns code1 the key in the DictEntry
	 */
	public int getCode() {
		return code;
	}

} // end
