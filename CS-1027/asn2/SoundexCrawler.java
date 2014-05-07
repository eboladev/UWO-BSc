/**
 *  @author Matthew Stokes
 *
 *  Represents a SoundexCrawler with uses a breadth-first-search approach to crawl
 */

import java.util.Iterator;

public class SoundexCrawler extends Crawler {

	/** INSTANCE VARIABLES **/

	/** storage for the pages that have yet to be visited **/
	private CircularArrayQueue<Page> pageToVisit = new CircularArrayQueue<Page>();

	/**
	 * Constructor that initializes a new BreadthCrawler .
	 * 
	 * @param String
	 *            keyword The keyword for which the crawler will search
	 * @param CrawlResultList
	 *            results
	 */
	public SoundexCrawler(String keyword, CrawlResultList results) {
		super(keyword, results);
	}

	/**
	 * A public method search that takes a String page address, and performs a
	 * breadth-first search, starting at that page. Note that this method
	 * overrides the search method in Crawler.
	 * 
	 * @param String
	 *            pageAddress
	 */
	public void search(String pageAddress)
	/**
	 * Important to note I could not figure out how to properly use the Iterator
	 * slash create myself a new iterator class so Page should be changed to
	 * SoundexPage all below. Exactly the same issue I had with the
	 * LevenshteinCrawler If this is done and the iterator issue is solved the
	 * program will run perfectly as the Iterator is the only issue with the
	 * Bonus coding
	 */
	{

		Page startPage = new Page(pageAddress); // a new Page object,
												// initialized with the starting
												// address
		super.setCrawling(true);
		pageToVisit.enqueue(startPage);
		while (!pageToVisit.isEmpty() && isCrawling == true) {
			Page page = pageToVisit.dequeue();
			printVisiting(page); // print a message before visiting each page
									// indicating that the page is about to be
									// visited.
			crawlingNextPage();
			try {
				WebHelper.downloadPage(page);
				addCrawledPage(page, page.containsText(keyword));
			} catch (Exception e) {
				addFailedPage(page, page.getAddress());
			}
			addVisitedLink(page.getAddress());
			if (page.containsText(keyword)) {
				printMatch(page);// print a message when a match has been found
			}
			if (page.getDepth() < maxDepth) {
				// Get an iterator of the page's links
				Iterator<Page> itorator = page.linkedPageIterator();
				int initialSize = pageToVisit.size();
				while (itorator.hasNext()
						&& ((pageToVisit.size() - initialSize) < maxLinks)) {
					Page linkedPage = itorator.next();
					if (!(hasVisitedLink(linkedPage.getAddress()))
							&& !pageToVisit.contains(linkedPage)) {
						pageToVisit.enqueue(linkedPage);
					}
				}
			}
		}
		super.setCrawling(false);
	}
}
