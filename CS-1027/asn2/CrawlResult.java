/** @author Matthew Stokes */
public class CrawlResult {

	/** Instance Variables */

	/** The Page crawled */
	private Page pageCrawled;
	/** The Page crawled */
	private String error;
	/** A flag indicating if the Page matched the search term */
	private boolean match;
	/** the order in which the page was crawled */
	private int sequenceNumber;

	/**
	 * A constructor that takes a Page, integer sequence number, and a boolean
	 * indicating if the passed Page matched the search term. Initializes all
	 * instance variables
	 * 
	 * @param Page
	 *            pageCrawled page already crawled
	 * @param int sequenceNumber
	 * @param boolean match page has matched keyword
	 */
	public CrawlResult(Page pageCrawled, int sequenceNumber, boolean match) {
		this.pageCrawled = pageCrawled;
		this.sequenceNumber = sequenceNumber;
		this.match = match;
	}

	/**
	 * A constructor that takes a Page, integer sequence number, and a boolean
	 * indicating if the passed Page matched the search term. Initializes all
	 * instance variables
	 * 
	 * @param Page
	 *            pageCrawled page already crawled
	 * @param int sequenceNumber
	 * @param String
	 *            error any associated errors
	 */
	public CrawlResult(Page pageCrawled, int sequenceNumber, String error) {
		this.pageCrawled = pageCrawled;
		this.sequenceNumber = sequenceNumber;
		this.error = error;
	}

	/**
	 * gets page just crawled.
	 * 
	 * @return Page pageCrawled page just crawled
	 */
	public Page getPage() {
		return (pageCrawled);
	}

	/**
	 * gets any associated Error message
	 * 
	 * @return String error error message
	 */
	public String getErrorMessage() {
		return (error);
	}

	/**
	 * get the number sequence for the given element
	 * 
	 * @return sequenceNumber
	 */
	public int getSequence() {
		return (sequenceNumber);
	}

	/**
	 * variable match is the keyword has been found on page
	 * 
	 * @return boolean match matching boolean
	 */
	public boolean isMatch() {
		return (match);
	}

	/**
	 * If crawl was successful
	 * 
	 * @return boolean true if match==true; else false
	 */
	public boolean crawlSuccess() {
		if (match == true) {
			return true;
		}

		else
			return false;
	}
}
