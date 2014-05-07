import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class CrawlerWindow extends JFrame {

    /**************************************************************************
     * INSTANCE VARIABLES
     *************************************************************************/
    protected Crawler crawler;       // Crawler used to search the web graph
    private CrawlResultList results; // Stores a list of crawl results
    private Thread crawlerThread;    // Runs the crawler on a separate thread
    /**************************************************************************
     * INSTANCE VARIABLES - GUI COMPONENTS
     *************************************************************************/
    protected JRadioButton crawlTypeBFSOption;
    protected JRadioButton crawlTypeDFSOption;
    protected JRadioButton crawlTypeLEVOption; //LEV
    protected JRadioButton crawlTypeSOption; //Soundex
    protected JSpinner maxLinksPerPageSpinner;
    protected JSpinner maxDepthSpinner;
    protected JButton resetButton;
    protected JButton searchButton;
    protected JTextField searchTermField;
    protected JTextField seedURLField;
    protected JLabel searchTermLabel;
    protected JLabel maxDepthLabel;
    protected JLabel maxLinksPerPageLabel;
    protected JLabel seedURLLabel;
    protected ButtonGroup searchTypeButtonGroup;
    protected JScrollPane statusScrollPane;
    protected JTable statusTable;
    protected JTabbedPane tabPanel;
    protected JMenuBar menubar;
    protected JMenu fileMenu;    
    protected JMenuItem exitMenuItem;
    protected JPanel newCrawlPanel;
    protected JPanel crawlLimitsPanel;
    protected JPanel crawlStatusPanel;
    protected JPanel crawlTypePanel;
    protected JPanel crawlerTab;

    /**
     * Creates a new CrawlerWindow
     */
    public CrawlerWindow() {

        this.crawlerThread = null;
        this.crawler = null;
        this.results = new CrawlResultList();

        initComponents();
    }

    /**
     * Stops the crawl process
     */
    public abstract void stopCrawler();

    /**
     * Returns a Boolean value indicating whether or not a crawl is
     * currently in progress
     * @return True, if a crawl is in progress; false, otherwise
     */
    public boolean isCrawling() {
        return ((this.crawler != null) && (this.crawler.isCrawling()));
    }

    /**
     * Clears the crawl results table
     */
    public void clearResults() {
        this.results.clear();
    }

    /**
     * Returns the CrawlResultList that is bound to the crawl results table
     * @return The CrawlResultList that is bound to the crawl results table
     */
    public CrawlResultList getResultList() {
        return this.results;
    }

    /**
     * Starts the crawler thread
     * @param url The URL at which to begin crawling
     */
    protected void startCrawlerThread(String url) {

        this.crawlerThread = new Thread(new CrawlRunner(this, crawler, url));
        this.crawlerThread.start();
    }

    /**
     * Stops the crawler thread
     */
    protected void stopCrawlerThread() {

        if ((this.crawlerThread == null) || (!this.crawlerThread.isAlive())) {
            return;
        }

        try {
            this.crawlerThread.join();
        } catch (Exception ex) {
            showErrorMessage("Error stopping crawler thread: " + ex.getMessage());
        }

    }

    /**
     * Displays the specified error message in a popup dialog
     * @param message The error message to display
     */
    protected void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**************************************************************************
     * BEGINNING OF NASTY GUI CODE!
     *************************************************************************/

    private void initializeStatusTable() {
        for (int i = 0; i < this.statusTable.getColumnCount(); i++) {
            this.statusTable.getColumn(statusTable.getColumnName(i)).setCellRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    renderer.setForeground(Color.BLACK);
                    CrawlResult result = ((CrawlResultList) table.getModel()).get(row);

                    if (result.crawlSuccess()) {

                        if (result.isMatch()) {
                            renderer.setBackground(Color.GREEN);
                        } else {
                            renderer.setBackground(Color.WHITE);
                        }
                    } else {
                        renderer.setBackground(Color.RED);
                    }

                    return renderer;
                }
            });
        }

        this.statusTable.getColumn(statusTable.getColumnName(0)).setMinWidth(75);
        this.statusTable.getColumn(statusTable.getColumnName(0)).setMaxWidth(75);
        this.statusTable.getColumn(statusTable.getColumnName(2)).setMinWidth(100);
        this.statusTable.getColumn(statusTable.getColumnName(2)).setMaxWidth(100);

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        searchTypeButtonGroup = new ButtonGroup();
        tabPanel = new JTabbedPane();
        crawlerTab = new JPanel();
        newCrawlPanel = new JPanel();
        seedURLLabel = new JLabel();
        searchTermLabel = new JLabel();
        searchTermField = new JTextField();
        seedURLField = new JTextField();
        searchButton = new JButton();
        resetButton = new JButton();
        crawlStatusPanel = new JPanel();
        statusScrollPane = new JScrollPane();
        statusTable = new JTable();
        crawlTypePanel = new JPanel();
        crawlTypeDFSOption = new JRadioButton();
        crawlTypeBFSOption = new JRadioButton();
        crawlTypeLEVOption = new JRadioButton(); //LEV
        crawlTypeSOption = new JRadioButton(); //Soundex
        crawlLimitsPanel = new JPanel();
        maxLinksPerPageLabel = new JLabel();
        maxDepthLabel = new JLabel();
        maxLinksPerPageSpinner = new JSpinner();
        maxDepthSpinner = new JSpinner();
        menubar = new JMenuBar();
        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();        

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        newCrawlPanel.setBorder(BorderFactory.createTitledBorder("New Crawl"));

        seedURLLabel.setDisplayedMnemonic('U');
        seedURLLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        seedURLLabel.setLabelFor(seedURLField);
        seedURLLabel.setText("Seed URL");

        searchTermLabel.setDisplayedMnemonic('T');
        searchTermLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        searchTermLabel.setLabelFor(searchTermField);
        searchTermLabel.setText("Search Term");

        searchButton.setMnemonic('S');
        searchButton.setText("Search");

        resetButton.setMnemonic('R');
        resetButton.setText("Reset");

        GroupLayout newCrawlPanelLayout = new GroupLayout(newCrawlPanel);
        newCrawlPanel.setLayout(newCrawlPanelLayout);
        newCrawlPanelLayout.setHorizontalGroup(
                newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(newCrawlPanelLayout.createSequentialGroup().addContainerGap().addGroup(newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(searchTermLabel).addComponent(seedURLLabel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(newCrawlPanelLayout.createSequentialGroup().addComponent(searchTermField, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(resetButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)).addComponent(seedURLField, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)).addContainerGap()));
        newCrawlPanelLayout.setVerticalGroup(
                newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(newCrawlPanelLayout.createSequentialGroup().addGroup(newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(seedURLLabel).addComponent(seedURLField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(newCrawlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(searchTermLabel).addComponent(searchTermField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(resetButton).addComponent(searchButton)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        newCrawlPanelLayout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[]{searchTermField, seedURLField});

        crawlStatusPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder("Crawl Status")));

        statusTable.setModel(results);
        statusTable.getTableHeader().setReorderingAllowed(false);
        statusScrollPane.setViewportView(statusTable);

        GroupLayout crawlStatusPanelLayout = new GroupLayout(crawlStatusPanel);
        crawlStatusPanel.setLayout(crawlStatusPanelLayout);
        crawlStatusPanelLayout.setHorizontalGroup(
                crawlStatusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlStatusPanelLayout.createSequentialGroup().addComponent(statusScrollPane, GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE).addContainerGap()));
        crawlStatusPanelLayout.setVerticalGroup(
                crawlStatusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlStatusPanelLayout.createSequentialGroup().addComponent(statusScrollPane, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE).addContainerGap()));

        crawlTypePanel.setBorder(BorderFactory.createTitledBorder("Crawl Type"));

        searchTypeButtonGroup.add(crawlTypeDFSOption);
        crawlTypeDFSOption.setMnemonic('D');
        crawlTypeDFSOption.setSelected(true);
        crawlTypeDFSOption.setText("Depth-First Search (DFS)");

        searchTypeButtonGroup.add(crawlTypeBFSOption);
        crawlTypeBFSOption.setMnemonic('B');
        crawlTypeBFSOption.setText("Breadth-First Search (BFS)");
        
        searchTypeButtonGroup.add(crawlTypeLEVOption);
        crawlTypeLEVOption.setMnemonic('X');
        crawlTypeLEVOption.setText("Levenshtein Search (LEV)");

        searchTypeButtonGroup.add(crawlTypeSOption);
        crawlTypeSOption.setMnemonic('Y');
        crawlTypeSOption.setText("Soundex Search (S)");
        
        GroupLayout crawlTypePanelLayout = new GroupLayout(crawlTypePanel);
        crawlTypePanel.setLayout(crawlTypePanelLayout);
        crawlTypePanelLayout.setHorizontalGroup(
                crawlTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlTypePanelLayout.createSequentialGroup().addContainerGap().addGroup(crawlTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(crawlTypeBFSOption).addComponent(crawlTypeDFSOption).addComponent(crawlTypeLEVOption).addComponent(crawlTypeSOption)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        crawlTypePanelLayout.setVerticalGroup(
                crawlTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlTypePanelLayout.createSequentialGroup().addComponent(crawlTypeDFSOption).addGap(1, 1, 1).addComponent(crawlTypeBFSOption).addGap(1, 1, 1).addComponent(crawlTypeLEVOption).addGap(1, 1, 1).addComponent(crawlTypeSOption).addContainerGap(29, Short.MAX_VALUE)));

        crawlLimitsPanel.setBorder(BorderFactory.createTitledBorder("Crawler Limits"));

        maxLinksPerPageLabel.setDisplayedMnemonic('L');
        maxLinksPerPageLabel.setLabelFor(maxLinksPerPageSpinner);
        maxLinksPerPageLabel.setText("Max. Links/Page");
        maxLinksPerPageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        maxDepthLabel.setDisplayedMnemonic('M');
        maxDepthLabel.setLabelFor(maxDepthSpinner);
        maxDepthLabel.setText("Max. Depth");
        maxDepthLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        maxLinksPerPageSpinner.setModel(new SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(1), null, Integer.valueOf(1)));

        maxDepthSpinner.setModel(new SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(1), null, Integer.valueOf(1)));

        GroupLayout crawlLimitsPanelLayout = new GroupLayout(crawlLimitsPanel);
        crawlLimitsPanel.setLayout(crawlLimitsPanelLayout);
        crawlLimitsPanelLayout.setHorizontalGroup(
                crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlLimitsPanelLayout.createSequentialGroup().addContainerGap().addGroup(crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(maxLinksPerPageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(maxDepthLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(maxDepthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(maxLinksPerPageSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        crawlLimitsPanelLayout.setVerticalGroup(
                crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlLimitsPanelLayout.createSequentialGroup().addGroup(crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(maxDepthLabel).addComponent(maxDepthSpinner, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(crawlLimitsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(maxLinksPerPageLabel).addComponent(maxLinksPerPageSpinner, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)).addContainerGap(24, Short.MAX_VALUE)));

        GroupLayout crawlerTabLayout = new GroupLayout(crawlerTab);
        crawlerTab.setLayout(crawlerTabLayout);
        crawlerTabLayout.setHorizontalGroup(
                crawlerTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlerTabLayout.createSequentialGroup().addContainerGap().addGroup(crawlerTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(crawlStatusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(crawlerTabLayout.createSequentialGroup().addComponent(newCrawlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(crawlTypePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(crawlLimitsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addContainerGap()));
        crawlerTabLayout.setVerticalGroup(
                crawlerTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(crawlerTabLayout.createSequentialGroup().addContainerGap().addGroup(crawlerTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(crawlLimitsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGroup(crawlerTabLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(crawlTypePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(newCrawlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(crawlStatusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        tabPanel.addTab("Crawler", crawlerTab);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }

        });
        
        fileMenu.add(exitMenuItem);

        menubar.add(fileMenu);      
        setJMenuBar(menubar);

        initializeStatusTable();

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(tabPanel).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE).addContainerGap()));

        tabPanel.getAccessibleContext().setAccessibleName("Crawler");

        this.setTitle("CS 1027b - Assignment 3");
        pack();
    }
}
