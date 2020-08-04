/*
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileTransferHandler extends TransferHandler {

    @Override
    protected Transferable createTransferable(JComponent c) {
        List<File> files = new ArrayList<File>();
        // copy your files from the component to a concrete List<File> files ...
        // the following code would be a sample for a JList filled with java.io.File(s) ...
        */
/*JList list = (JList) c;
        for (Object obj: list.getSelectedValues()) {
            files.add((File)obj);
        }*//*

        return new FileTransferable(files);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
 class FileTransferable implements Transferable {

    private List<File> files;

    public FileTransferable(List<File> files) {
        this.files = files;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.javaFileListFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(DataFlavor.javaFileListFlavor);
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return files;
    }
}
*/


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
class FileListTransferHandler extends TransferHandler {
    private JTable list;

    public FileListTransferHandler(JTable list) {
        this.list = list;
    }


    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public boolean canImport(TransferSupport ts) {
        return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    public boolean importData(TransferSupport ts) {
        try {
            @SuppressWarnings("rawtypes")
            List data = (List) ts.getTransferable().getTransferData(
                    DataFlavor.javaFileListFlavor);
            if (data.size() < 1) {
                return false;
            }

            DefaultListModel listModel = new DefaultListModel();
            for (Object item : data) {
                File file = (File) item;
                listModel.addElement(file);
                System.out.println(file.getName() + file.getPath());


            }

            list.setModel((javax.swing.table.TableModel) listModel);
            return true;

        } catch (UnsupportedFlavorException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}





//
//@SuppressWarnings("serial")
//public class FileDragDemo extends JPanel {
//    private JTable list = new JTable();
//
//    public FileDragDemo() {
//        list.setDragEnabled(true);
//        list.setTransferHandler(new FileListTransferHandler(list));
//
//        add(new JScrollPane(list));
//    }
//
//    private static void createAndShowGui() {
//        FileDragDemo mainPanel = new FileDragDemo();
//
//        JFrame frame = new JFrame("FileDragDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(mainPanel);
//        frame.pack();
//        frame.setLocationByPlatform(true);
//        frame.setVisible(true);
//    }
//
////    public static void main(String[] args) {
////        SwingUtilities.invokeLater(new Runnable() {
////            public void run() {
////                createAndShowGui();
////            }
////        });
//    }
////}


//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//
//public class DragButton extends JButton{
//
//    private volatile int draggedAtX, draggedAtY;
//
//    public DragButton(String text){
//        super(text);
//        setDoubleBuffered(false);
//        setMargin(new Insets(0, 0, 0, 0));
//        setSize(25, 25);
//        setPreferredSize(new Dimension(25, 25));
//
//        addMouseListener(new MouseAdapter(){
//            public void mousePressed(MouseEvent e){
//                draggedAtX = e.getX() - getLocation().x;
//                draggedAtY = e.getY() - getLocation().y;
//            }
//        });
//
//        addMouseMotionListener(new MouseMotionAdapter(){
//            public void mouseDragged(MouseEvent e){
//                setLocation(e.getX() - draggedAtX, e.getY() - draggedAtY);
//            }
//        });
//    }

//    public static void main(String[] args){
//        JFrame frame = new JFrame("DragButton");
//        frame.setLayout(null);
//        frame.getContentPane().add(new DragButton("1"));
//        frame.getContentPane().add(new DragButton("2"));
//        frame.getContentPane().add(new DragButton("3"));
//        frame.setSize(300, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//}







///*
// * DragPictureDemo.java is a 1.4 example that
// * requires the following files:
// *     Picture.java
// *     DTPicture.java
// *     PictureTransferHandler.java
// *     images/Maya.jpg
// *     images/Anya.jpg
// *     images/Laine.jpg
// *     images/Cosmo.jpg
// *     images/Adele.jpg
// *     images/Alexi.jpg
// */
//import java.io.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.datatransfer.*;
//import java.awt.dnd.*;
//import javax.swing.*;
//
//public class DragPictureDemo extends JPanel {
//
//    DTPicture pic1, pic2, pic3, pic4,  pic5,  pic6,
//            pic7, pic8, pic9, pic10, pic11, pic12;
//    static String mayaString = "Maya";
//    static String anyaString = "Anya";
//    static String laineString = "Laine";
//    static String cosmoString = "Cosmo";
//    static String adeleString = "Adele";
//    static String alexiString = "Alexi";
//    PictureTransferHandler picHandler;
//
//    public DragPictureDemo() {
//        super(new BorderLayout());
//        picHandler = new PictureTransferHandler();
//
//        JPanel mugshots = new JPanel(new GridLayout(4, 3));
//        pic1 = new DTPicture(createImageIcon("images/" +
//                mayaString + ".jpg", mayaString).getImage());
//        pic1.setTransferHandler(picHandler);
//        mugshots.add(pic1);
//        pic2 = new DTPicture(createImageIcon("images/" +
//                anyaString + ".jpg", anyaString).getImage());
//        pic2.setTransferHandler(picHandler);
//        mugshots.add(pic2);
//        pic3 = new DTPicture(createImageIcon("images/" +
//                laineString + ".jpg", laineString).getImage());
//        pic3.setTransferHandler(picHandler);
//        mugshots.add(pic3);
//        pic4 = new DTPicture(createImageIcon("images/" +
//                cosmoString + ".jpg", cosmoString).getImage());
//        pic4.setTransferHandler(picHandler);
//        mugshots.add(pic4);
//        pic5 = new DTPicture(createImageIcon("images/" +
//                adeleString + ".jpg", adeleString).getImage());
//        pic5.setTransferHandler(picHandler);
//        mugshots.add(pic5);
//        pic6 = new DTPicture(createImageIcon("images/" +
//                alexiString + ".jpg", alexiString).getImage());
//        pic6.setTransferHandler(picHandler);
//        mugshots.add(pic6);
//
//        //These six components with no pictures provide handy
//        //drop targets.
//        pic7 = new DTPicture(null);
//        pic7.setTransferHandler(picHandler);
//        mugshots.add(pic7);
//        pic8 = new DTPicture(null);
//        pic8.setTransferHandler(picHandler);
//        mugshots.add(pic8);
//        pic9 = new DTPicture(null);
//        pic9.setTransferHandler(picHandler);
//        mugshots.add(pic9);
//        pic10 = new DTPicture(null);
//        pic10.setTransferHandler(picHandler);
//        mugshots.add(pic10);
//        pic11 = new DTPicture(null);
//        pic11.setTransferHandler(picHandler);
//        mugshots.add(pic11);
//        pic12 = new DTPicture(null);
//        pic12.setTransferHandler(picHandler);
//        mugshots.add(pic12);
//
//        setPreferredSize(new Dimension(450, 630));
//        add(mugshots, BorderLayout.CENTER);
//        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//    }
//
//    /** Returns an ImageIcon, or null if the path was invalid. */
//    protected static ImageIcon createImageIcon(String path,
//                                               String description) {
//        java.net.URL imageURL = DragPictureDemo.class.getResource(path);
//
//        if (imageURL == null) {
//            System.err.println("Resource not found: "
//                    + path);
//            return null;
//        } else {
//            return new ImageIcon(imageURL, description);
//        }
//    }
//
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI() {
//        //Make sure we have nice window decorations.
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        //Create and set up the window.
//        JFrame frame = new JFrame("DragPictureDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        //Create and set up the menu bar and content pane.
//        DragPictureDemo demo = new DragPictureDemo();
//        demo.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(demo);
//
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
//}
























//
//
//
//
//import javax.swing.*;
//        import javax.swing.border.EmptyBorder;
//        import javax.swing.event.*;
//        import javax.swing.filechooser.FileSystemView;
//        import javax.swing.tree.DefaultMutableTreeNode;
//        import javax.swing.tree.DefaultTreeCellRenderer;
//        import javax.swing.tree.DefaultTreeModel;
//        import java.awt.*;
//        import java.awt.event.*;
//        import java.io.*;
//        import java.nio.file.Path;
//        import java.util.ArrayList;
//        import java.util.Date;
//
//public class FileManager {
//    //the main frame of the file manger
//    private JFrame frame;
//    // the main panel
//    private JPanel panel;
//    private FileSystemView fileSystemView;
//    private Desktop desktop;
//    //default Model of tree
//    private DefaultTreeModel treeModel;
//    //the tree that show directory like tree
//    private JTree tree;
//    private DefaultMutableTreeNode currentNode;
//    //the now path of file
//    private String Path;
//    //the Array list of the path that u where in it
//    private ArrayList<String> paths = new ArrayList<String>();
//    //the showing file in big format
//    private JPanel Grid;
//    //the starting place of file manger
//    private static String startState = System.getProperty("user.dir");
//    private JTextArea textArea;
//    /**
//     * setting Frame
//     */
//    //the state place to start
//    private JTextField OpeningFile;
//    //the directory that file was be upload in
//    private JTextField SyncState;
//    //the server computer IP
//    private JTextField ComputerIP;
//    //the server computer Port
//    private JTextField ComputerPort;
//    // the look and file of Swing
//    private JComboBox LookAndFeel;
//    //showing file in big Icon
//    private JCheckBox ShowingFileGrid;
//    //showing file in rows
//    private JCheckBox ShowingFileTable;
//    //the time that set to update the file
//    private JComboBox TimeToSync;
//    //now look and feel
//    private static String Lookandfeel;
//    /**
//     * Copy and Paste
//     */
//    //the file that want to be copy
//    private File[] copyFile;
//    //file is cut or not
//    private boolean isCut;
//
//    /**
//     * Searched file
//     */
//    private JTable table = new JTable();
//    private JScrollPane taScrollPane;
//
//    /**
//     * Title of the application
//     */
//    //the title of pro
//    public static final String APP_TITLE = "FileManager";
//
//
//    /**
//     * Set Up Panel
//     */
//    // the Addres bar of pro
//    private JTextField Paths;
//    // the search bar
//    private JTextField Search;
//    private ArrayList<File> fileSearch = new ArrayList<>();
//    /**
//     * Now File Place
//     */
//    //the now file that we in it
//    private File currentFile;
//    //the number of file that selected
//    private File[] selectedFile;
//    //use to show which kind of file
//    private static int Show = 1;
//    private JSplitPane splitPane;
//    private TableModel tableModel = new TableModel();
//
//    public FileManager() {
//        frame = new JFrame(APP_TITLE);
//        frame.setBounds(500, 500, 880, 700);
//        frame.setLocationRelativeTo(null);
//        fileSystemView = FileSystemView.getFileSystemView();
//        desktop = Desktop.getDesktop();
//        splitPane = new JSplitPane(SwingConstants.VERTICAL);
//        frame.setJMenuBar(setMenuBar());
//        Path = startState;
//        panel = new JPanel(new BorderLayout(5, 5));
//        panel.setBorder(new EmptyBorder(3, 3, 3, 3));
//        panel.add(UpPanel(), BorderLayout.NORTH);
//
//        if (Show > 0) {
//            Table();
//        }
//        createTree();
//
//        if (Show > 0) {
//            setInfoTable();
//        }
//        table.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isRightMouseButton(e))
//                    popupMenu().show(table, e.getX(), e.getY());
//                if (e.getClickCount() == 1) {
//                    selectedFile = currentFile.listFiles();
//                    update();
//                }if (e.getClickCount() == 2) {
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    System.out.println(currentFile.getName() + "     ");
//                    if (currentFile.isDirectory()) {
//                        System.out.println(currentFile.getName() + "     ");
//                        update();
//                        selectedFile = null;
//                    }else {
//                        try {
//                            desktop.open(currentFile);
//                            currentFile = currentFile.getParentFile();
//                            Path = currentFile.getPath();
//                            paths.add(Path);
//                            Paths.setText(Path);
//                            update();
//                            selectedFile = null;
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//        table.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyChar() == '\n') {
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    selectedFile = currentFile.listFiles();
//                    if (currentFile.isDirectory()) {
//                        update();
//                        selectedFile = null;
//                    }
//                    else {
//                        try {
//                            desktop.open(currentFile);
//                            currentFile = currentFile.getParentFile();
//                            Path = currentFile.getPath();
//                            paths.add(Path);
//                            Paths.setText(Path);
//                            update();
//                            selectedFile = null;
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//                if (e.getKeyChar() == '\b') {
//                    if (currentFile.getName().equals("Desktop"))
//                        return;
//                    if (currentFile.getName().equals("C:"))
//                        return;
//                    currentFile = currentFile.getParentFile();
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    update();
//                    selectedFile = null;
//                }
//
//            }
//        });
//        tree.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//
//                if (e.getClickCount() == 1) {
//                    int HowMany = currentFile.listFiles().length;
//                    textArea.setText(Integer.toString(HowMany) + " items" + " ");
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    update();
//                    selectedFile = null;
//                }//Todo tree update
//                if (e.getClickCount() == 2) {
//                    int HowMany = currentFile.listFiles().length;
//                    textArea.setText(Integer.toString(HowMany) + " items" + " ");
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    update();
//                    selectedFile = null;
//                }
//            }
//        });
//        panel.add(DownPanel(), BorderLayout.SOUTH);
//        frame.setContentPane(panel);
//        frame.setVisible(true);
//    }
//
//    /**
//     * Set the Menu Bar of File Manger
//     *
//     * @return The JMenuBar
//     */
//    private JMenuBar setMenuBar() {
//        JMenuBar jMenuBar = new JMenuBar();
//        JMenu files = new JMenu("File");
//        files.setMnemonic('F');
//        files.setToolTipText("Do some Thing for file");
//        JMenu edit = new JMenu("Edit");
//        edit.setMnemonic('E');
//        edit.setToolTipText("Edit the file");
//        JMenu help = new JMenu("Help");
//        help.setMnemonic('H');
//        help.setToolTipText("Help U To Use This Pro");
//        JMenuItem newFile = new JMenuItem("NewFile");
//        newFile.setMnemonic('i');
//        newFile.setToolTipText("Create New File In This Phat");
//        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
//        newFile.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CreateNewFile();
//            }
//        });
//        JMenuItem newFolder = new JMenuItem("NewFolder");
//        newFolder.setMnemonic('o');
//        newFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
//        newFolder.setToolTipText("Create New Folder In This Phat");
//        newFolder.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CreateNewFolder();
//            }
//        });
//        JMenuItem deleteSelected = new JMenuItem("Delete Selected");
//        deleteSelected.setMnemonic('D');
//        deleteSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
//        deleteSelected.setToolTipText("Delete Selected File(s) OR Folder(s)");
//        deleteSelected.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (selectedFile == null)
//                    return;
//                File file = new File(currentFile.getParent());
//                delete(selectedFile);
//                selectedFile = null;
//                //TODO  fffff
//                currentFile = file;
//                update();
//            }
//        });
//        JMenuItem syncFiles = new JMenuItem("Sync File");
//        syncFiles.setMnemonic('S');
//        syncFiles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
//        syncFiles.setToolTipText("Sync the File OR Folder");
//        JMenuItem rename = new JMenuItem("Rename");
//        rename.setMnemonic(KeyEvent.VK_F2);
//        rename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
//        rename.setToolTipText("Rename The File(s) OR Folder(s)");
//        rename.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                File file = new File(currentFile.getParent());
//                RenameFile();
//                currentFile = file;
//                Path = file.getPath();
//                Paths.setText(Path);
//                update();
//            }
//        });
//        JMenuItem cut = new JMenuItem("Cut");
//        cut.setMnemonic('t');
//        cut.setToolTipText("Cut the Selected File OR Folder ");
//        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
//        cut.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CopyORCut();
//            }
//        });
//        JMenuItem copy = new JMenuItem("Copy");
//        copy.setMnemonic('o');
//        copy.setToolTipText("Copy The Selected File OR Folder ");
//        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
//        copy.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CopyORCut();
//            }
//        });
//        JMenuItem paste = new JMenuItem("Paste");
//        paste.setMnemonic('P');
//        paste.setToolTipText("Paste The Copied File OR Folder ");
//        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
//        paste.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (copyFile == null)
//                    return;
//                CopyFile(copyFile, currentFile);
//                if (isCut) {
//                    for (int i = 0; i < copyFile.length; i++) {
//                        copyFile[i].delete();
//                    }
//                }
//                copyFile = null;
//                isCut = false;
//
//            }
//        });
//        JMenuItem sync = new JMenuItem("Sync");
//        sync.setMnemonic('S');
//        sync.setToolTipText("Sync Selected File OR Folder ");
//        sync.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
//        JMenuItem aboutMe = new JMenuItem("About Me");
//        aboutMe.setMnemonic('A');
//        aboutMe.setToolTipText("About The Person That Create This Pro");
//        aboutMe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
//        aboutMe.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new JFrame("About_Me");
//                frame.setBounds(500, 500, 500, 500);
//                frame.setLocationRelativeTo(null);
//                frame.setBackground(Color.MAGENTA);
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                JTextArea textArea = new JTextArea();
//                textArea.setText("Hello It`s Me the Person how create this program" + "\n" +
//                        "من محمد رضا قادری هستم" + "\n" +
//                        "Email : Mrq112775@Gmail.com" + "\n" +
//                        "خدایی راه بیاید با من");
//                Font f = new Font("Serif", Font.ITALIC, 15);
//                textArea.setFont(f);
//                textArea.setBackground(Color.PINK);
//                frame.add(textArea);
//                frame.setVisible(true);
//            }
//        });
//        help.add(aboutMe);
//        JMenuItem helpItem = new JMenuItem("Help_Item");
//        helpItem.setMnemonic('I');
//        helpItem.setToolTipText("Talk About This Pro And What is Do");
//        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
//        helpItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new JFrame("Help_Item");
//                frame.setBounds(500, 500, 700, 200);
//                frame.setLocationRelativeTo(null);
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                JTextArea textArea = new JTextArea();
//                textArea.setText("This Program is about the File Manager" + "\n With This Pro U can Edit , Copy , Paste and Some More Option Like Windows File Manager");
//                Font f = new Font("Serif", Font.BOLD, 15);
//                textArea.setFont(f);
//                textArea.setBackground(Color.PINK);
//                frame.add(textArea);
//                frame.setVisible(true);
//            }
//        });
//        JMenuItem setting = new JMenuItem("Setting");
//        setting.setMnemonic('S');
//        setting.setToolTipText("Set the Pro How Do ");
//        setting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
//        setting.addActionListener(new ActionListener() {
//            @Override
//            //TODO 111
//            public void actionPerformed(ActionEvent e) {
//                JFrame frame = new JFrame("Setting");
//                frame.setBounds(500, 500, 700, 500);
//                frame.setLocationRelativeTo(null);
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                JPanel panel = new JPanel(new GridLayout(8, 1));
//                JPanel openfile = new JPanel(new GridLayout(1, 2));
//                OpeningFile = new JTextField(startState);
//                Font f = new Font("Serif", Font.PLAIN, 15);
//                OpeningFile.setEditable(true);
//                OpeningFile.setFont(f);
//                JTextField jTextField = new JTextField("Opening File Path");
//                jTextField.setEditable(false);
//                jTextField.setPreferredSize(new Dimension(100, 20));
//                openfile.add(jTextField);
//                openfile.add(OpeningFile);
//                panel.add(openfile);
//                JPanel syncstate = new JPanel(new GridLayout(1, 2));
//                SyncState = new JTextField();
//                JTextField jTextField1 = new JTextField("Sync Save State");
//                jTextField1.setEditable(false);
//                jTextField1.setPreferredSize(new Dimension(100, 20));
//                SyncState.setEditable(true);
//                syncstate.add(jTextField1);
//                syncstate.add(SyncState);
//                panel.add(syncstate);
//                JPanel compIP = new JPanel(new GridLayout(1, 2));
//                JTextField jTextField2 = new JTextField("The DComputer IP ");
//                jTextField2.setEditable(false);
//                jTextField2.setPreferredSize(new Dimension(100, 20));
//                ComputerIP = new JTextField();
//                ComputerIP.setEditable(true);
//                compIP.add(jTextField2);
//                compIP.add(ComputerIP);
//                panel.add(compIP);
//                JPanel compPort = new JPanel(new GridLayout(1, 2));
//                JTextField jTextField3 = new JTextField("The DComputer Port ");
//                jTextField3.setEditable(false);
//                jTextField3.setPreferredSize(new Dimension(100, 20));
//                ComputerPort = new JTextField();
//                ComputerPort.setEditable(true);
//                compPort.add(jTextField3);
//                compPort.add(ComputerPort);
//                String[] Lookandfeel1 = {"Windows", "Nimbus", "Motif"};
//                JPanel Pro = new JPanel(new GridLayout(1, 3));
//                LookAndFeel = new JComboBox(Lookandfeel1);
//                Pro.add(LookAndFeel);
//                JPanel panel1 = new JPanel(new GridLayout(1, 2));
//                ShowingFileGrid = new JCheckBox("Grid Showing");
//                ShowingFileTable = new JCheckBox("Table Showing");
//                panel1.add(ShowingFileGrid);
//                panel1.add(ShowingFileTable);
//                Pro.add(panel1);
//                String[] Time = {"every min ", "5 min", "10 min ", " 30 min ", "1 hour"};
//                TimeToSync = new JComboBox(Time);
//                Pro.add(TimeToSync);
//                panel.add(Pro);
//                if (startState.equals("") || !(new File(startState)).isDirectory())
//                    startState = System.getProperty("user.dir");
//                LookAndFeel.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (LookAndFeel.getSelectedIndex() == 0)
//                            Lookandfeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//                        if (LookAndFeel.getSelectedIndex() == 1)
//                            Lookandfeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
//                        if (LookAndFeel.getSelectedIndex() == 2)
//                            Lookandfeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//                        Data(Lookandfeel + "/" + startState + "/" + Show);
//                    }
//                });
//                OpeningFile.addKeyListener(new KeyAdapter() {
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//                        if (e.getKeyChar() == '\n')
//                            startState = OpeningFile.getText();
//                        Data(Lookandfeel + "/" + startState + "/" + Show);
//                    }
//                });
//                ShowingFileTable.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (ShowingFileTable.isSelected())
//                            Show = 1;
//                        else
//                            Show = 0;
//                        Data(Lookandfeel + "/" + startState + "/" + Show);
//                    }
//                });
//                ShowingFileGrid.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (ShowingFileGrid.isSelected())
//                            Show = 0;
//                        else
//                            Show = 1;
//                        Data(Lookandfeel + "/" + startState + "/" + Show);
//                    }
//                });
//                frame.add(panel);
//                frame.setVisible(true);
//            }
//        });
//        files.add(newFile);
//        files.add(newFolder);
//        files.add(deleteSelected);
//        files.add(syncFiles);
//        edit.add(rename);
//        edit.add(cut);
//        edit.add(copy);
//        edit.add(paste);
//        edit.add(sync);
//        help.add(setting);
//        help.add(helpItem);
//        jMenuBar.add(files);
//        jMenuBar.add(edit);
//        jMenuBar.add(help);
//
//        return jMenuBar;
//    }
//
//    /**
//     * Set the Mouse menu handler
//     *
//     * @return the PopUpMenu
//     */
//    private JPopupMenu popupMenu() {
//        JPopupMenu PopUpMenu = new JPopupMenu("RightClick");
//        if (selectedFile != null) {
//            JMenuItem open = new JMenuItem("Open");
//            open.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    update();
//                }
//            });
//            JMenuItem cut = new JMenuItem("Cut");
//            cut.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    CopyORCut();
//                }
//            });
//            JMenuItem copy = new JMenuItem("Copy");
//            copy.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    CopyORCut();
//                }
//            });
//            JMenuItem delete = new JMenuItem("Delete");
//            delete.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {/*
//                delete(selectedFile);
//                //TODO
//                update();*/
//                    File file = new File(currentFile.getParent());
//                    System.out.println(currentFile.getName());
//                    delete(selectedFile);/*
//                    Path = file.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);*/
//                    update();
//                }
//            });
//            JMenuItem paste = new JMenuItem("Paste");
//            paste.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (copyFile == null)
//                        return;
//                    CopyFile(copyFile, currentFile);
//                    if (isCut) {
//                        for (int i = 0; i < copyFile.length; i++) {
//                            copyFile[i].delete();
//                        }
//                    }
//                    copyFile = null;
//                    isCut = false;
//                }
//
//            });
//            JMenuItem properties = new JMenuItem("Properties");
//            properties.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    long lastModified = currentFile.lastModified();
//                    int directory = 0;
//                    int file = 0;
//                    //Todo type
//                    String Type;
//                    if (currentFile.isDirectory()) {
//                        for (File i : currentFile.listFiles()) {
//                            if (i.isDirectory())
//                                directory++;
//                            else
//                                file++;
//                        }
//                    }
//                    long s = currentFile.length();
//                    float d = (float) (s / 100000);
//                    if (currentFile.isDirectory()) {
//                        JOptionPane.showMessageDialog(null, "Type : FileFolder " + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Type : File " + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//
//                    }
//                }
//            });
//            PopUpMenu.add(open);
//            PopUpMenu.add(cut);
//            PopUpMenu.add(copy);
//            PopUpMenu.add(paste);
//            PopUpMenu.add(delete);
//            PopUpMenu.add(properties);
//        } else {
//            JMenuItem newFolder = new JMenuItem("NewFolder");
//            newFolder.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    CreateNewFolder();
//                }
//            });
//            JMenuItem newFile = new JMenuItem("NewFile");
//            newFile.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    CreateNewFile();
//                }
//            });
//            JMenuItem properties = new JMenuItem("Properties");
//            properties.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    long lastModified = currentFile.lastModified();
//                    int directory = 0;
//                    int file = 0;
//                    //Todo type
//                    String Type;
//                    if (currentFile.isDirectory()) {
//                        for (File i : currentFile.listFiles()) {
//                            if (i.isDirectory())
//                                directory++;
//                            else
//                                file++;
//                        }
//                    }
//                    long s = currentFile.length();
//                    float d = (float) (s / 100000);
//                    if (currentFile.isDirectory()) {
//                        JOptionPane.showMessageDialog(null, "Type : FileFolder " + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Type : File " + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//
//                    }
//                }
//            });
//
//            PopUpMenu.add(newFile);
//            PopUpMenu.add(newFolder);
//            PopUpMenu.add(properties);
//        }
//        return PopUpMenu;
//    }
//
//    /**
//     * The Upest Panel that have address and search bar and back and forward and undo of file manger
//     * this panel set in North of main panel
//     *
//     * @return the panel
//     */
//    private JPanel UpPanel() {
//        JPanel UpPanel = new JPanel();
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.BOTH;
//        c.weighty = 1;
//        c.weightx = 1;
//        Icon backIcon = new ImageIcon("back.png");
//        Icon forwardIcon = new ImageIcon("Forward.png");
//        Icon undoIcon = new ImageIcon("undo.png");
//        JButton back = new JButton(backIcon);
//        back.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 1) {
//                    int size = paths.size() - 1;
//                    Paths.setText(paths.get(size));
//                    currentFile = new File(paths.get(size));
//                    update();
//                }
//            }
//        });
//        JButton forward = new JButton(forwardIcon);
//        JButton undo = new JButton(undoIcon);
//        undo.setBackground(Color.LIGHT_GRAY);
//        undo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (currentFile.getName().equals("Desktop"))
//                    return;
//                File file = new File(currentFile.getParent());
//                currentFile = file;
//                Path = currentFile.getPath();
//                Paths.setText(Path);
//                update();
//            }
//        });
//        back.setMaximumSize(new Dimension(10, 10));
//        back.setMinimumSize(new Dimension(5, 5));
//        forward.setMaximumSize(new Dimension(10, 10));
//        forward.setMinimumSize(new Dimension(5, 5));
//        undo.setMaximumSize(new Dimension(20, 20));
//        undo.setMinimumSize(new Dimension(5, 5));
//        c.weightx = 1;
//        c.weighty = 1;
//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.ipadx = 0;
//        c.ipady = 0;
//        c.insets = new Insets(0, 0, 0, 0);
//        UpPanel.add(back, c);
//        c.gridx = 1;
//        c.gridy = 0;
//        c.ipadx = 50;
//        UpPanel.add(forward, c);
//        c.insets = new Insets(0, 2, 0, 2);
//        c.gridx = 2;
//        c.gridy = 0;
//        c.ipadx = 50;
//        UpPanel.add(undo, c);
//        GridBagConstraints c1 = new GridBagConstraints();
//        Paths = new JTextField(System.getProperty(Path));
//        Paths.setPreferredSize(new Dimension(500, 20));
//        Paths.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    File file = new File(e.getActionCommand());
//                    currentFile = file;
//                    update();
//
//                } catch (Exception e1) {
//                    System.out.println("There is no This Path");
//                }
//            }
//        });
//
//        c1.gridx = 3;
//        c1.ipadx = 3000;
//        UpPanel.add(Paths, c1);
//        c1.gridx = 4;
//        c1.ipadx = 300;
//        Search = new JTextField("Search");
//        Search.setPreferredSize(new Dimension(150, 20));
//        Search.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals("")) {
//                    update();
//                    return;
//                }
//                File[] files = new File[1];
//                files[0] = currentFile;
//                System.out.println(files[0]);
//                searching(files, Search.getText());
//                files = new File[fileSearch.size()];
//                for (int i = 0; i < fileSearch.size(); i++) {
//                    files[i] = fileSearch.get(i);
//                }
//                tableModel.setFiles(files);
//                fileSearch = new ArrayList<>();
//            }
//        });
//
//        UpPanel.add(Search, c1);
//
//        return UpPanel;
//    }
//
//    /**
//     * The Down Panel that have tow icon to set the show formation
//     *
//     * @return the panel
//     */
//    private JPanel DownPanel() {
//        JPanel DownPanel = new JPanel(new BorderLayout());
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        Icon Grid = new ImageIcon("list (2).png");
//        Icon Table = new ImageIcon("grid (2).png");
//        JButton Grids = new JButton(Grid);
//        Grids.setMaximumSize(new Dimension(40, 40));
//        Grids.setPreferredSize(new Dimension(40, 40));
//        Grids.setMinimumSize(new Dimension(40, 40));
//        Grids.setSize(new Dimension(40, 40));
//        Grids.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                /*Show = true;*/
//                update();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//            }
//        });
//        JButton Tables = new JButton(Table);
//        Tables.setMaximumSize(new Dimension(40, 40));
//        Tables.setPreferredSize(new Dimension(40, 40));
//        Tables.setMinimumSize(new Dimension(40, 40));
//        Tables.setSize(new Dimension(40, 40));
//        Tables.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                /*Show = false;*/
//                update();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//            }
//        });
//        c.ipadx = 0;
//        c.ipady = 0;
//        c.gridx = 0;
//        c.gridy = 0;
//        panel.add(Tables, c);
//        GridBagConstraints c1 = new GridBagConstraints();
//        c1.ipadx = 1;
//        c1.ipady = 0;
//        c1.gridx = 15;
//        c1.gridy = 0;
//        panel.add(Grids, c1);
//        JPanel panel1 = new JPanel();
//        int HowMany = currentFile.listFiles().length;
//        textArea = new JTextArea();
//        textArea.setSize(new Dimension(300, 300));
//        textArea.setText(Integer.toString(HowMany) + "items" + "   ");
//        panel1.add(textArea);
//        DownPanel.add(panel, BorderLayout.EAST);
//        DownPanel.add(panel1, BorderLayout.WEST);
//        return DownPanel;
//    }
//
//    /**
//     * set the file for cut or copy
//     */
//    private void CopyORCut() {
//        copyFile = null;
//        File[] files = new File[selectedFile.length];
//        int counter = 0;
//        for (File file : selectedFile) {
//            files[counter] = file;
//            counter++;
//        }
//        copyFile = files;
//        isCut = true;
//    }
//
//    /**
//     * Rename the file that u select that if some thing get wrong told to u
//     */
//    public void RenameFile() {
//        JFrame frame = new JFrame("RenameFile");
//        String newName = JOptionPane.showInputDialog(frame, "New Name");
//        File[] files = currentFile.listFiles();
//        int count = 0;
//        if (selectedFile.length == 1) {
//            for (File file : files) {
//                if (fileSystemView.getSystemDisplayName(file).equals(newName))
//                    count++;
//                break;
//            }
//
//            if (count > 0) {
//                JOptionPane.showMessageDialog(frame, "The Process not done !! Please Try Again", "Error Massage", JOptionPane.ERROR_MESSAGE);
//            }
//            if (count == 0) {
//                selectedFile[0].renameTo(new File(newName));
//            }
//        } else {
//            JOptionPane.showMessageDialog(frame, "Please Select one Item", "Error Massage", JOptionPane.ERROR_MESSAGE);
//        }
//        if (selectedFile == null) {
//            return;
//        }
//        update();
//    }
//
//    /**
//     * Create new Directory in current path
//     */
//    public void CreateNewFolder() {
//        JFrame frame = new JFrame("CreateNewFolder");
//        String newName = JOptionPane.showInputDialog(frame, "New Folder Name");
//        File[] files = currentFile.listFiles();
//        int count = 0;
//        for (File file : files) {
//            if (fileSystemView.getSystemDisplayName(file).equals(newName))
//                if (file.isDirectory())
//                    count++;
//            break;
//        }
//        if (count > 0)
//            newName += "Copy";
//        File file = new File(Path + "\\" + newName);
//        if (!file.mkdir()) {
//            JOptionPane.showMessageDialog(frame, "the procces not done !! please try again", "Errored Massage", JOptionPane.ERROR_MESSAGE);
//        }
//        /*treeModel.insertNodeInto(new DefaultMutableTreeNode(file), currentNode, currentNode.getChildCount());*/
//        update();
//    }
//
//    /**
//     * Create new file in current path with the type of file
//     */
//    private void CreateNewFile() {
//        JFrame frame = new JFrame("CreateNewFile");
//        String newName = JOptionPane.showInputDialog(frame, "New File Name With Type :");
//        //see have file like that
//        File[] files = currentFile.listFiles();
//        int count = 0;
//        for (File file : files) {
//            if (fileSystemView.getSystemDisplayName(file).equals(newName))
//                count++;
//            break;
//        }
//        if (count != 0) {
//            //TODO bad form
//            newName += "-Copies";
//        }
//
//        File file = new File(Path + "\\" + newName);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(frame, "the procces not done !! please try again", "Errored Massage", JOptionPane.ERROR_MESSAGE);
//        }
//        update();
//    }
//
//    /**
//     * Copy the file (or array of the files) int current path
//     *
//     * @param copyFile    the file(s) want to copy
//     * @param currentFile the file that want to be copied
//     */
//    private void CopyFile(File[] copyFile, File currentFile) {
//        File file1;
//        File file2;
//        FileInputStream inputStream;
//        FileOutputStream outputStream;
//        int lenght;
//        byte[] buffer = new byte[2048];
//        for (File i : copyFile) {
//            try {
//                if (i.isDirectory()) {
//                    file2 = new File(currentFile + "\\" + i.getName());
//                    file2.mkdir();
//                    CopyFile(i.listFiles(), file2);
//
//                } else {
//                    file1 = new File(i.getAbsolutePath());
//                    file2 = new File(currentFile + "\\" + i.getName());
//                    outputStream = new FileOutputStream(file2);
//                    inputStream = new FileInputStream(file1);
//                    while ((lenght = inputStream.read(buffer)) > 0) {
//                        outputStream.write(buffer, 0, lenght);
//                    }
//                    inputStream.close();
//                    outputStream.close();
//                }
//            } catch (IOException in) {
//                System.out.println("There Is Something Wrong");
//            }
//        }
//        update();
//    }
//
//    private void delete(File[] file) {
//        File file1 = new File(currentFile.getParent());
//        for (File m : file) {
//            if (m == null)
//                return;
//            if (m.isDirectory()) {
//                delete(m.listFiles());
//            }
//            file1 = new File(m.getParent());
//            m.delete();
//        }
//        currentFile = file1;
//        update();
//    }
//
//    private void searching(File[] file, String str) {
//
//        for (File file1 : file) {
//            if (!file1.isDirectory()) {
//                if (file1.getName().contains(str)) {
//                    fileSearch.add(file1);
//                }
//            } else {
//                if (file1.getName().contains(str)) {
//                    fileSearch.add(file1);
//                    searching(file1.listFiles(), str);
//                }
//            }
//        }
//    }
//
//    private void Data(String string) {
//        try {
//            PrintStream printStream = new PrintStream(new FileOutputStream("File.txt"));
//            printStream.print(string);
//        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "some Thing go Wrong");
//        }
//    }
//
//    public static void ReadData() {
//        String Data = "";
//        try {
//            FileReader fileReader = new FileReader("File.txt");
//            int Count;
//            while ((Count = fileReader.read()) != -1) {
//                Data += (char) Count;
//            }
//        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "Some Thing Go Wrong");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!Data.equals("")) {
//            String[] strings = Data.split("/", 3);
//            System.out.println(strings[0] + "  " + strings[1] + "  " + strings.length);
//            startState = strings[0];
//            Lookandfeel = strings[1];
//            Show = Integer.parseInt(strings[2]);
//        }
//    }
//
//    private void createTree() {
//        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
//            public void valueChanged(TreeSelectionEvent tse) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
//                File file = (File) node.getUserObject();
//                currentFile = file;
//                currentNode = node;
//                Path = file.getPath();
//                if (Path.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
//                    Path = "This PC";
//                }
//                if (Path.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
//                    Path = "Libraries";
//                }
//                getChildren(node, file);
//                paths.add(Path);
//                Paths.setText(Path);
//                System.out.println("333");
//                if (Show > 0) {
//                    Table();
//                }
//                selectedFile = null;
//            }
//        };
//        File fileRoot = fileSystemView.getRoots()[0];
//        DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
//        getChildren(root, fileRoot);
//        treeModel = new DefaultTreeModel(root);
//        tree = new JTree(treeModel);
//        tree.addTreeSelectionListener(treeSelectionListener);
//        tree.expandRow(0);
//        tree.setVisibleRowCount(10);
//        tree.setCellRenderer(new FileTreeCellRenderer());
//        tree.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyChar() == '\b') {
//                    if (currentFile.getName().equals("Desktop"))
//                        return;
//                    File file = new File(currentFile.getParent());
//                    currentFile = file;
//                    Path = currentFile.getPath();
//                    paths.add(Path);
//                    Paths.setText(Path);
//                    update();
//                }
//
//            }
//        });
//        JScrollPane tscrollPane = new JScrollPane(tree);
//        tscrollPane.createVerticalScrollBar();
//        tscrollPane.createHorizontalScrollBar();
//        tscrollPane.setVisible(true);
//        if (Show > 0) {
//            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tscrollPane, table);
//            splitPane.setVisible(true);
//            panel.add(splitPane, BorderLayout.WEST);
//        }
//    }
//
//    private void getChildren(DefaultMutableTreeNode node, File file) {
//        //in current file we search for baby@@
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//            if (!file1.isDirectory())
//                continue;
//            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(file1);
//            node.add(node1);
//        }
//    }
//
//    private void Table() {
//        // Create a Model For Table
//        if (currentFile == null)
//            currentFile = new File(Path);
//        File[] files = currentFile.listFiles();
//        tableModel.setFiles(files);
//        table.setModel(tableModel);
//        try {
//            Icon icon = fileSystemView.getSystemIcon(files[0]);
//            table.setRowHeight(icon.getIconHeight() + 7);
//            table.getColumnModel().getColumn(0).setPreferredWidth(35);
//            table.getColumnModel().getColumn(1).setPreferredWidth(400);
//            table.getColumnModel().getColumn(2).setPreferredWidth(200);
//            table.getColumnModel().getColumn(3).setPreferredWidth(200);
//            table.setAutoCreateRowSorter(true);
//            table.setShowHorizontalLines(true);
//            table.setShowVerticalLines(true);
//            table.setDragEnabled(true);
//            frame.setTitle(fileSystemView.getSystemDisplayName(currentFile));
//        } catch (Exception e) {
//            System.out.println(123456);
//        }
//    }
//
//    private void setInfoTable() {
//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                int[] ints = table.getSelectionModel().getSelectedIndices();
//                File[] files = currentFile.listFiles();
//                File[] files1 = new File[ints.length];
//                int counter = 0;
//                for (Integer i : ints) {
//                    try {
//                        currentFile = files[i];
//                        files1[counter] = files[i];
//                        counter++;
//                    } catch (Exception e1) {
//                    }
//                }
//                selectedFile = files1;
//            }
//        });
//        taScrollPane = new JScrollPane(table);
//        splitPane.add(taScrollPane);
//        panel.add(splitPane, BorderLayout.CENTER);
//    }
//
//    public static String getLookandfeel() {
//        return Lookandfeel;
//    }
//
//    public static void setLookandfeel(String lookandfeel) {
//        Lookandfeel = lookandfeel;
//    }
//
//    private void update() {
//        /*if (fileSystemView.getSystemDisplayName(currentFile).equals("Desktop")) {
//            currentFile = fileSystemView.getRoots()[0];
//        } else if (fileSystemView.getSystemDisplayName(currentFile).equals("This PC"))
//            currentFile = fileSystemView.getRoots()[0].listFiles()[0];
//        if (Path.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
//            Path = "This PC";
//        }
//        if (Path.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
//            Path = "Libraries";
//        } else {*/
//        Table();
//        int HowMany = currentFile.listFiles().length;
//        textArea.setText(Integer.toString(HowMany) + "items" + "   ");
//        /*}*/
//    }
//
//    class FileTreeCellRenderer extends DefaultTreeCellRenderer {
//
//
//        private FileSystemView fileSystemView;
//
//        private JLabel label;
//
//        FileTreeCellRenderer() {
//            label = new JLabel();
//            label.setOpaque(true);
//            fileSystemView = FileSystemView.getFileSystemView();
//        }
//
//        @Override
//        public Component getTreeCellRendererComponent(
//                JTree tree,
//                Object value,
//                boolean selected,
//                boolean expanded,
//                boolean leaf,
//                int row,
//                boolean hasFocus) {
//
//            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
//            File file = (File) node.getUserObject();
//            currentFile = file;
//            label.setIcon(fileSystemView.getSystemIcon(file));
//            label.setText(fileSystemView.getSystemDisplayName(file));
//            label.setToolTipText(file.getPath());
//            if (selected) {
//                label.setBackground(backgroundSelectionColor);
//                label.setForeground(textSelectionColor);
//            } else {
//                label.setBackground(backgroundNonSelectionColor);
//                label.setForeground(textNonSelectionColor);
//            }
//            return label;
//        }
//    }
//}