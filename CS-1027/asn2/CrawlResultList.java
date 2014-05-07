import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class CrawlResultList extends AbstractTableModel {
    
    private final static int SEQUENCE_COLUMN = 0;
    private final static int URL_COLUMN = 1;
    private final static int MATCH_COLUMN = 2;
    private final String[] COLUMN_NAMES = new String[] { "Seq. Num", "Page URL", "Term Found?" };

    private ArrayList<CrawlResult> results;

    public CrawlResultList() {
        this.results = new ArrayList<CrawlResult>();
    }

    public CrawlResult get(int index) {
        return this.results.get(index);
    }
    
    public int getRowCount() {

        return this.results.size();
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        
        switch (columnIndex) {
            case SEQUENCE_COLUMN:
                return Integer.class;
            case URL_COLUMN:
                return String.class;
            case MATCH_COLUMN:
                return Boolean.class;
            default:
                return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        if ((rowIndex < 0) || (rowIndex >= this.results.size()))
            return null;

        CrawlResult result = this.results.get(rowIndex);

        switch (columnIndex) {
            case SEQUENCE_COLUMN:
                return result.getSequence();
            case URL_COLUMN:

                if (result.crawlSuccess())
                    return result.getPage().getAddress();
                else
                    return result.getPage().getAddress() + " - " + result.getErrorMessage();
                
            case MATCH_COLUMN:
                return result.isMatch();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    public boolean add(CrawlResult result) {
        this.results.add(result);
        this.fireTableRowsInserted(this.results.size() - 1, this.results.size() - 1);
        return true;
    }

    public void clear() {
        this.results.clear();
        this.fireTableDataChanged();
    }
}
