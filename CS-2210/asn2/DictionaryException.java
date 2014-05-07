
@SuppressWarnings("serial")
public class DictionaryException extends Exception {

	/* Attributes */
	private String message;
	
	/** 
	 * Constructor method 
	 * @param message
	 */
	public DictionaryException(String message) 
	{
		super(message);
		this.message=message;
	}
	/** 
	 * @return Returns the string message which we gave to the constructor
	 */
	public String getMessage()
	{
		return message;
	}
}
