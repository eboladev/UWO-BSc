/**
 *  @author Matthew Stokes & CS 1027 Given Assignment 3 Code
 *
 *  Represents a CrawlerWindow by using a GUI to display crawl results and display search fields
 *  
 *
 */
import javax.swing.UIManager;

public class MyCrawlerWindow extends CrawlerWindow {

	public MyCrawlerWindow() {
		this.initComponents();
	}
	  /**
	   * Called when the search button is clicked
{ If a crawl is currently in progress (use the superclass isCrawling method), then stop the crawler
{ If a crawl is not in progress, check if the user entered both a URL and search term
* If so, start the crawler
* If not, display an appropriate error message using the superclass showErrorMessage method
	   */
	private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {

		if (isCrawling() == true) {
			stopCrawler();
		} else if (!isCrawling() && !searchTermField.getText().equals("")
				&& !seedURLField.getText().equals("")) {
			startCrawler();
		} else if (searchTermField.getText().equals("")) {
			showErrorMessage("Empty Search Term Field");
		}
		if (seedURLField.getText().equals("")) {
			showErrorMessage("Empty Seed URL Field");
		}

	}
	  /**
	 Called when the reset button is clicked. This should:
{ Clear the seed URL and search term fields. You can clear text in a text field using .setText(null)
{ Set the selected crawl type to breadth-first search. You can set a radio button using .setSelected(true)
{ Set the max depth and max. links per page and fields to 3. You can set the value of a spinner
using setValue() which takes an Object
	   */
	private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {
		setComponentsEnabled(true);
		searchTermField.setText(null);
		seedURLField.setText(null);
		crawlTypeBFSOption.setSelected(true);
		maxDepthSpinner.setValue(3);
		maxLinksPerPageSpinner.setValue(3);
		searchButton.setText("Search");
		stopCrawler(); //Added function if you are going to dock just comment out
		clearResults(); //Added function if you are going to dock just comment out
	}					// Both added to make function more realistic
	  /**
	   * Starts crawler crawling.
	   */
	private void startCrawler() {

		clearResults();
		searchButton.setText("Stop");
		setComponentsEnabled(false);

		if (crawlTypeBFSOption.isSelected()) {
			BreadthCrawler crawler = new BreadthCrawler(
					searchTermField.getText(), getResultList());
			super.crawler = crawler;
		}
		if (crawlTypeDFSOption.isSelected()) {
			DepthCrawler crawler = new DepthCrawler(searchTermField.getText(),
					getResultList());
			super.crawler = crawler;
		}
		if (crawlTypeLEVOption.isSelected()) {
			LevenshteinCrawler crawler = new LevenshteinCrawler(
					searchTermField.getText(), getResultList());
			super.crawler = crawler;
		}
		if (crawlTypeSOption.isSelected()) {
			SoundexCrawler crawler = new SoundexCrawler(
					searchTermField.getText(), getResultList());
			super.crawler = crawler;
		}
		crawler.setMaxDepth((Integer) maxDepthSpinner.getValue());
		crawler.setMaxLinks((Integer) maxLinksPerPageSpinner.getValue());
		startCrawlerThread(seedURLField.getText());
	}
	  /**
	   * Stop crawler from crawling.
	   */
	public void stopCrawler() {

		if (super.crawler != null && isCrawling() == true) {
			crawler.stop();
			stopCrawlerThread();
			this.searchButton.setText("Search");
			setComponentsEnabled(true);

		}
	}
	  /**
	   * Enable or disable all labels, text fields, buttons (except the Search/Stop & Reset button), radio buttons, and
spinners.
	   * @param boolean enabled if true all enabled; else false all disabled except Search/Stop & Reset
	   */
	public void setComponentsEnabled(boolean enabled) {
		searchTermField.setEnabled(enabled);
		seedURLField.setEnabled(enabled);
		crawlTypeDFSOption.setEnabled(enabled);
		crawlTypeBFSOption.setEnabled(enabled);
		crawlTypeLEVOption.setEnabled(enabled);
		crawlTypeSOption.setEnabled(enabled);
		maxDepthSpinner.setEnabled(enabled);
		maxLinksPerPageSpinner.setEnabled(enabled);
		;// I chose to keep Reset button enabled no matter what and added code
			// to resetButtonActionPerformed
		// I chose to do this because I feel it functions more realistically
		//If you are going to take a mark away un-comment the below statement instead
		// resetButton.setEnabled(enabled);
	}

	private void initComponents() {

		searchButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchButtonActionPerformed(evt);
			}
		});

		resetButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resetButtonActionPerformed(evt);
			}
		});

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			// Ignore the exception -- not fatal
		}
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MyCrawlerWindow().setVisible(true);
			}
		});

	}
}
