import java.util.LinkedList;

public class Dictionary implements DictionaryADT {

	/* Attributes */
	
	private int size;
	private LinkedList<DictEntry>[] dictionary_table;
	private DictEntry element;
	private int count;
	private boolean success = false;
	private int hashCode;

	/**
	 * Constructor method which initializes an empty dictionary with the
	 * specified size
	 * 
	 * @param size
	 */
	@SuppressWarnings("unchecked")	//no try and catch around cast just a technicality issue when creating array of lists
	public Dictionary(int size) {
		this.size = size;
		dictionary_table = (LinkedList<DictEntry>[]) new LinkedList[size];
		for (int i = 0; i <= size - 1; i++) {
			dictionary_table[i] = new LinkedList<DictEntry>(); // initializes linked lists for every position in the array
		}
	}

	/**
	 * Method inserts the given pair(string,code) in the dictionary. Throws a
	 * DictionaryException if the key associated with pair,pair.getKey(), is
	 * already in the dictionary. Implemented using a hash table with separate
	 * chaining. Method will return 1 if the insertion of object pair produces a
	 * collision, and return value 0 otherwise
	 * 
	 * @param pair
	 * @return Return 1 if collision, else 0
	 */
	public int insert(DictEntry pair) throws DictionaryException {
		if (dictionary_table[hashCode(pair.getKey())].size() != 0) // if item present in linked list
		{
			for (int i = 0; i < dictionary_table[hashCode(pair.getKey())].size(); i++) // traversing linked list
			{
				element = dictionary_table[hashCode(pair.getKey())].get(i); // get element at spot in linked list
				if (element.getKey() == pair.getKey()) // if elements key = pair's key
					throw new DictionaryException("Already in Dicitonary");
			}
			dictionary_table[hashCode(pair.getKey())].add(pair);
			count++;
			return 1;
		} else
		{
			dictionary_table[hashCode(pair.getKey())].add(pair);
			count++;
		return 0;
		}
	}

	/**
	 * Removes the entry with the given key from the dictionary. Must throw a
	 * DictionaryException if the key is not in the dictionary
	 * 
	 * @param key
	 */
	public void remove(String key) throws DictionaryException {

		for (int i = 0; i <= dictionary_table[hashCode(key)].size() - 1; i++) // traversing linked list associated with hashCode(key) location in dictionary_table
		{
			if (dictionary_table[hashCode(key)].get(i).getKey().compareTo(key) == 0) // if key = key
			{
				dictionary_table[hashCode(key)].remove(i);
				success = true;
				count--;
			}
		}
		if (success == false)
			throw new DictionaryException(
					"Cannot Remove Element That is Not Present");
	}

	/**
	 * A method which returns the DictEntry objected stored in the dictionary
	 * with the given key, or null entry if no entry in the dictionary has the
	 * given key
	 * 
	 * @param key
	 * @return Returns DictEntry element if found. Else returns null
	 */
	public DictEntry find(String key) {
		for (int i = 0; i <= dictionary_table[hashCode(key)].size() - 1; i++) // traversing linked list associated with hashCode(key) location in dictionary_table
		{
			if (dictionary_table[hashCode(key)].get(i).getKey().compareTo(key) == 0) // if key = key
			{
				return dictionary_table[hashCode(key)].get(i); // return element associated with key
			}
		}
		return null;
	}

	/**
	 * A method that returns the number of DictEntry objects stored in the
	 * dictionary
	 * 
	 * @return Returns number of DictEntry objects stored in the dictionary
	 */
	public int numElements() {
		return count;
	}

	/**
	 * 
	 * @param hash_string
	 * @return Returns hashCode which is derived from the (int) for the
	 *         hash_string. The hashCode has also been compression mapped
	 */
	private int hashCode(String hash_string) {
		hashCode = hash_string.charAt(0);

		for (int i = 1; i <= hash_string.length() - 1; i++) // Polynomial Accumulation
		{
			hashCode += (hash_string.charAt(i) * (37 ^ i));
		}

		hashCode = hashCode % size; // Compression Function

		return hashCode;
	}

}// end
