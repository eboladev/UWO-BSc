public class CrawlRunner implements Runnable {

    private CrawlerWindow window;
    private Crawler crawler;
    private String startURL;

    public CrawlRunner(CrawlerWindow window, Crawler crawler, String startURL) {
        this.window = window;
        this.crawler = crawler;
        this.startURL = startURL;
    }

    public void run() {

			crawler.search(this.startURL);

		
        window.stopCrawler();
    }


}
