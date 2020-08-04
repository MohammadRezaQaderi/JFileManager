import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.Date;

public class TableModel extends AbstractTableModel {
    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    public Object getValueAt(int row, int column) {
        try {
            File file = files[row];
            switch (column) {
                case 0:
                    return fileSystemView.getSystemIcon(file);
                case 1:
                    return fileSystemView.getSystemDisplayName(file);
                case 2:
                    return file.lastModified();
                case 3:
                    return FileManager.Type(file);
                case 4:
                    return file.length();
                default:
                    System.err.println("Logic Error");
            }
        } catch (Exception e) {
        }

        return "";
    }

    private String[] columns = {"Icon", "File", "Last Modified", "Type", "Size",};

    TableModel() {
        this(new File[0]);
    }

    TableModel(File[] files) {
        this.files = files;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 2:
                return Date.class;
            case 3:
                return File.class;
            case 4:
                return Long.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        this.files = files;
        fireTableDataChanged();
    }
}
