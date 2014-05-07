import java.util.ArrayList;

public abstract class Crawler {

	/** INSTANCE VARIABLES **/

	/** A String representing the keyword for which the crawler will search */
	protected String keyword = "";
	/** An integer representing the max. depth to which the crawler will search */
	protected int maxDepth;
	/**
	 * An integer representing the max. number of links to be crawled on each
	 * page
	 */
	protected int maxLinks;
	// List of links the crawler has already visited
	private ArrayList<String> linksVisited;

	/** INSTANCE VARIABLES FOR PART 2 **/

	/** CrawlResultList to store the results of our crawl */
	private CrawlResultList results;
	/** Integer indicating the sequence number of the last page crawled */
	private int sequenceNumber;
	/** boolean indicating whether or not we should stop crawling */
	protected boolean stop;
	/** boolean indicating if we are currently crawling. */
	protected boolean isCrawling;

	/**
	 * Constructor that initializes a new crawler.
	 * 
	 * @param String
	 *            keyword The keyword for which the crawler will search
	 * @param CrawlResultList
	 *            results
	 */
	public Crawler(String keyword, CrawlResultList results) {
		this.linksVisited = new ArrayList<String>();

		this.keyword = keyword;
		this.results = results;
		sequenceNumber = 0;
		isCrawling = false;
		stop = false;
	}

	/**
	 * Returns a Boolean value indicating if the crawler has visited the
	 * specified web site.
	 * 
	 * @param address
	 *            Address of the web site to test
	 * @return True, if the crawler has visited the site; false, otherwise
	 */
	public boolean hasVisitedLink(String address) {
		return this.linksVisited.contains(address);
	}

	/**
	 * Adds the specified address to the list of pages the crawler has already
	 * visited
	 * 
	 * @param address
	 *            Address of the page the crawler has visited
	 */
	public void addVisitedLink(String address) {
		this.linksVisited.add(address);
	}

	/**
	 * getter method used to get keyword
	 * 
	 * @return String keyword
	 */
	public String getKeyword() {
		return (keyword);
	}

	/**
	 * setter method used to set keyword
	 * 
	 * @param String
	 *            keyword
	 */
	public void setKeyword(String newKeyword) {
		keyword = newKeyword;
	}

	/**
	 * getter method used to get maximum depth of the crawler
	 * 
	 * @return int newMaxDepth maximum depth that the crawler can crawl
	 */
	public int getMaxDepth() {
		return (maxDepth);
	}

	/**
	 * setter method used to set maximum depth of the crawler
	 * 
	 * @param int newMaxDepth maximum depth that the crawler can crawl
	 */
	public void setMaxDepth(int newMaxDepth) {
		maxDepth = newMaxDepth;
	}

	/**
	 * getter method used to get maximum links the crawler can crawl to on each
	 * page
	 * 
	 * @return int newMaxLinks maximum links to be crawled on each page
	 */
	public int getMaxLinks() {
		return (maxLinks);
	}

	/**
	 * setter method used to set maximum links the crawler can crawl to on each
	 * page
	 * 
	 * @param int newMaxLinks maximum links to be crawled on each page
	 */
	public void setMaxLinks(int newMaxLinks) {
		maxLinks = newMaxLinks;
	}

	/**
	 * Abstract method we will implement this method in the BreadthCrawler and
	 * DepthCrawler classes
	 * 
	 * @param String
	 *            startAddress Takes a String representing the address at which
	 *            to start searching
	 */
	abstract void search(String startAddress);

	/**
	 * The method prints a message indicating that the search keyword has been
	 * found on the page, and prints its address
	 * 
	 * @param Page
	 *            pageFound Is the page the keyword was found on
	 */
	protected void printMatch(Page pageFound) {
		System.out.println("Keyword found on: " + pageFound.getAddress());
	}

	/**
	 * The method prints a message indicating indicating that the crawler is
	 * about to visit the page, and prints its address
	 * 
	 * @param Page
	 *            pageVisiting Is the page the crawler is about to visit
	 */
	protected void printVisiting(Page pageVisiting) {
		System.out.println("About to visit: " + pageVisiting.getAddress());
	}

	// Part 2
	/**
	 * Takes a Page and a boolean indicating if the page matched the search
	 * term. Creates a new CrawlResult object with parameters Page and the
	 * boolean from above, as well as an integer sequence number of order, and
	 * adds it to the CrawlResultList object you stored as an instance variable
	 * 
	 * @param Page
	 *            page current page
	 * @param boolean matching if page is matching the search term
	 */
	protected void addCrawledPage(Page page, boolean matching) {
		CrawlResult CrawlResult1 = new CrawlResult(page, sequenceNumber,
				matching);
		results.add(CrawlResult1);
	}

	/**
	 * Takes a Page and a boolean indicating if the page matched the search
	 * term. Creates a new CrawlResult object with parameters Page and the
	 * boolean from above, as well as an integer sequence number of order, and
	 * adds it to the CrawlResultList object you stored as an instance variable
	 * 
	 * @param Page
	 *            page current page
	 * @param String
	 *            failed if page has failed/error
	 */
	protected void addFailedPage(Page page, String failed) {
		CrawlResult CrawlResult2 = new CrawlResult(page, sequenceNumber, failed);
		results.add(CrawlResult2);
	}

	/**
	 * Accessor indicating if the crawler is currently crawling.
	 * 
	 * @return boolean isCrawling true if crawling; else false
	 */
	public boolean isCrawling() {
		return (isCrawling);
	}

	/**
	 * Mutator that sets whether or not the crawler is currently crawling.
	 * 
	 * @param boolean settingCrawling true sets isCrawling true; false sets
	 *        isCrawling false
	 */
	protected void setCrawling(boolean settingCrawling) {
		isCrawling = settingCrawling;
	}

	/**
	 * Sets the instance variable that indicates whether or not we should stop
	 * crawling.
	 */
	public void stop() {
		stop = true;
		isCrawling = false;
	}

	/**
	 * Increments the last page sequence number.
	 */
	protected void crawlingNextPage() {
		sequenceNumber++;
	}

}
