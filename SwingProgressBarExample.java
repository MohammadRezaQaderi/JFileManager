import javax.swing.*;

public class SwingProgressBarExample extends JPanel {

    JProgressBar pbar;

    static final int MY_MINIMUM = 0;

    static final int MY_MAXIMUM = 100;

    public SwingProgressBarExample() {
        // initialize Progress Bar
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        // add to JPanel
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }

    public static void main(String args[]) {
        final SwingProgressBarExample it = new SwingProgressBarExample();
        JFrame frame = new JFrame("Progress Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(it);
        frame.pack();
        frame.setVisible(true);
        // run a loop to demonstrate raising
        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        it.updateBar(percent);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}




//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.event.*;
//import javax.swing.filechooser.FileSystemView;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeCellRenderer;
//import javax.swing.tree.DefaultTreeModel;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class FileManager {
//    private JFrame frame;
//    private JPanel panel;
//    private FileSystemView fileSystemView;
//    private DefaultTreeModel treeModel;
//    private JTree tree;
//    private DefaultMutableTreeNode currentNode;
//    private String Path;
//    private ArrayList<String> paths = new ArrayList<String>();
//    private JPanel Grid;
//    private String nowPath;
//    private Desktop desktop;
//    private JScrollPane scrollPane;
//    private static String startState = System.getProperty("user.dir");
//    private JTextArea textArea;
//    /**
//     * setting Frame
//     */
//    private JTextField OpeningFile;
//    private JTextField SyncState;
//    private JTextField ComputerIP;
//    private JTextField ComputerPort;
//    private JComboBox LookAndFeel;
//    private JCheckBox ShowingFileGrid;
//    private JCheckBox ShowingFileTable;
//    private JComboBox TimeToSync;
//    /**
//     * Copy and Paste
//     */
//    private Path destinationPath;
//    private Path sourcePath;
//    private File[] copyFile;
//    private boolean isCut;
//
//    /**
//     * Searched file
//     */
//    private ArrayList<File> fileSearch = new ArrayList<>();
//
//    private JTable table = new JTable();
//    private JScrollPane taScrollPane;
//
//    /**
//     * Title of the application
//     */
//    public static final String APP_TITLE = "FileManager";
//
//
//    /**
//     * Set JMenu Item
//     */
//    private JPanel paneln;
//
//    /**
//     * Set Up Panel
//     */
//    private JTextField Paths;
//    private JTextField Search;
//
//    /**
//     * Now File Place
//     */
//    private File currentFile;
//    private TableModel TableModel;
//    private File[] selectedFile;
//    private ListSelectionListener listSelectionListener;
//    private Boolean Show = true;
//    private JSplitPane splitPane;
//    private TableModel tableModel = new TableModel();
//
//    public File getCurrentFile() {
//        return currentFile;
//    }
//
//    public void setCurrentFile(File currentFile) {
//        this.currentFile = currentFile;
//    }
//
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
//        if (!Show) {
//            Table();
//        }
//        createTree();
//        if (Show) {
//            paneln = Grid();
//            paneln.addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//                    Paths.setText(currentFile.getPath());
//                    if (SwingUtilities.isRightMouseButton(e))
//                        popupMenu().show(paneln, e.getX(), e.getY());
//                    if (e.getClickCount() == 2) {
//                        if (currentFile.isDirectory())
//                            Table();
//                        else {
//                            try {
//                                desktop.open(currentFile);
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            });
//            scrollPane = new JScrollPane(paneln);
//            scrollPane.setMinimumSize(new Dimension(100, 100));
//            scrollPane.setPreferredSize(new Dimension(100, 100));
//            scrollPane.setSize(new Dimension(100, 100));
//            scrollPane.setMaximumSize(new Dimension(100, 100));
//            scrollPane.createVerticalScrollBar();
//            panel.add(scrollPane, BorderLayout.CENTER);
//        }
//        if (!Show) {
//            setInfoTable();
//        }
//        table.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isRightMouseButton(e))
//                    popupMenu().show(table, e.getX(), e.getY());
//                if (e.getClickCount() == 2) {
//                    Path = currentFile.getPath();
//                    Paths.setText(Path);
//                    if (currentFile.isDirectory())
//                        update();
//                    else {
//                        try {
//                            desktop.open(currentFile);
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//        tree.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 1) {
//                    selectedFile = currentFile.listFiles();
//                    Path = currentFile.getPath();
//                    Paths.setText(Path);
//                    int HowMany = currentFile.listFiles().length;
//                    textArea.setText(Integer.toString(HowMany) + " items" + " ");
//                }
//            }
//        });
//        panel.add(DownPanel(), BorderLayout.SOUTH);
//        frame.setContentPane(panel);
//        frame.setVisible(true);
//    }
//
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
//                    selectedFile[0].delete();
//                    currentFile = file;
//                    Path = file.getPath();
//                    Paths.setText(Path);
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
//                String[] Lookandfeel = {"Default", "Black"};
//                JPanel Pro = new JPanel(new GridLayout(1, 3));
//                LookAndFeel = new JComboBox(Lookandfeel);
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
//    private JPanel DownPanel() {
//        JPanel DownPanel = new JPanel(new BorderLayout());
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        Icon Grid = new ImageIcon("list.png");
//        Icon Table = new ImageIcon("grid.png");
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
//        back.setFont(new Font("Arial", 12, 5));
//        JButton forward = new JButton(forwardIcon);
//        forward.setFont(new Font("Arial", 12, 5));
//        JButton undo = new JButton(undoIcon);
//        undo.setFont(new Font("Arial", 12, 5));
//        undo.setBackground(Color.LIGHT_GRAY);
//
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
//                    Table();
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
//                    return;
//                }
//                File[] files = new File[1];
//                files[0] = currentFile;
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
//                File file = new File(currentFile.getPath());
//                selectedFile[0].renameTo(new File(file.getParent() + newName));
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
//
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
//        update();
//    }
//
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
//    private void createTree() {
//        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
//            public void valueChanged(TreeSelectionEvent tse) {
//                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
//                File file = (File) node.getUserObject();
//                currentFile = file;
//                currentNode = node;
//                System.out.println(currentFile.getName() + "create tree");
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
//                if (!Show) {
//                    Table();
//                } else {
//                    paneln = GridUD();
//                    JScrollPane scrollPane = new JScrollPane(paneln);
//                    scrollPane.setMinimumSize(new Dimension(100, 100));
//                    scrollPane.setPreferredSize(new Dimension(100, 100));
//                    scrollPane.setSize(new Dimension(100, 100));
//                    scrollPane.setMaximumSize(new Dimension(100, 100));
//                    scrollPane.createVerticalScrollBar();
//                    scrollPane.createHorizontalScrollBar();
//                    splitPane.add(scrollPane);
//                    panel.add(splitPane, BorderLayout.CENTER);
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
//        JScrollPane tscrollPane = new JScrollPane(tree);
//        tscrollPane.createVerticalScrollBar();
//        tscrollPane.createHorizontalScrollBar();
//        tscrollPane.setVisible(true);
//        if (!Show) {
//            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tscrollPane, table);
//            splitPane.setVisible(true);
//            panel.add(splitPane, BorderLayout.WEST);
//        } else {
//            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tscrollPane, paneln);
//            splitPane.setVisible(true);
//            panel.add(splitPane, BorderLayout.WEST);
//        }
//    }
//
//    private JPanel GridUD() {
//        paneln.removeAll();
//        paneln = Grid();
//        paneln.revalidate();
//        paneln.repaint();
//        return paneln;
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
//        table.setDragEnabled(true);
//        try {
//            Icon icon = fileSystemView.getSystemIcon(files[0]);
//            table.setRowHeight(icon.getIconHeight() + 7);
//            table.getColumnModel().getColumn(0).setPreferredWidth(35);
//            table.getColumnModel().getColumn(1).setPreferredWidth(400);
//            table.getColumnModel().getColumn(2).setPreferredWidth(200);
//            table.getColumnModel().getColumn(3).setPreferredWidth(200);
//            table.setAutoCreateRowSorter(true);
//            table.setShowHorizontalLines(true);
//            table.setShowVerticalLines(false);
//            table.setDragEnabled(true);
//            frame.setTitle(fileSystemView.getSystemDisplayName(currentFile));
//        } catch (Exception e) {
//            System.out.println(123456);
//        }
//    }
//
//    private void update() {
//        if (fileSystemView.getSystemDisplayName(currentFile).equals("Desktop")) {
//            currentFile = fileSystemView.getRoots()[0];
//        } else if (fileSystemView.getSystemDisplayName(currentFile).equals("This PC"))
//            currentFile = fileSystemView.getRoots()[0].listFiles()[0];
//        if (Path.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
//            Path = "This PC";
//        }
//        if (Path.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
//            Path = "Libraries";
//        } else {
//            Table();
//            int HowMany = currentFile.listFiles().length;
//            textArea.setText(Integer.toString(HowMany) + "items" + "   ");
//        }
//    }
//    private JPanel Grid() {
//        Grid = new JPanel(new FlowLayout(/*(currentFile.listFiles().length/5),5)*/));
//        Grid.setMaximumSize(new Dimension(100, 200));
//        File[] files = currentFile.listFiles();
//        for (File chunck : files) {
//            JPanel panel = new JPanel(new BorderLayout(2, 2));
//            panel.setMaximumSize(new Dimension(100, 100));
//            panel.setMinimumSize(new Dimension(100, 100));
//            panel.setPreferredSize(new Dimension(100, 100));
//            panel.setSize(100, 100);
//            JButton button = new JButton();
//            button.setMaximumSize(new Dimension(100, 100));
//            button.setPreferredSize(new Dimension(100, 100));
//            JLabel textField = new JLabel();
//            textField.setText(chunck.getName());
//            Icon icon = fileSystemView.getSystemIcon(chunck);
//            button.setIcon(icon);
//            panel.add(button, BorderLayout.CENTER);
//            panel.add(textField, BorderLayout.SOUTH);
//            Grid.add(panel);
//        }
//        Grid.revalidate();
//        Grid.repaint();
//        return Grid;
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
//    class FileTreeCellRenderer extends DefaultTreeCellRenderer {
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
//
//            String string;
//            string = Search.getText();
//
//
//            if (selected) {
//                label.setBackground(backgroundSelectionColor);
//                label.setForeground(textSelectionColor);
//            } else {
//                label.setBackground(backgroundNonSelectionColor);
//                label.setForeground(textNonSelectionColor);
//            }
//
//            return label;
//        }
//    }
//}


























































































































































//
//
//
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.event.*;
//import javax.swing.filechooser.FileSystemView;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeCellRenderer;
//import javax.swing.tree.DefaultTreeModel;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Set;
//import java.util.concurrent.Flow;
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
//    private static String startState = "C:\\Users\\mohammad reza";
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
//        File file = new File(Path);
//        currentFile = file;
//        panel = new JPanel(new BorderLayout(5, 5));
//        panel.setBorder(new EmptyBorder(3, 3, 3, 3));
//        panel.add(UpPanel(), BorderLayout.NORTH);
//
//        if (Show > 0) {
////            Table();
////            JScrollPane scrollPane = new JScrollPane(Gird());
////            scrollPane.createVerticalScrollBar();
////            scrollPane.createHorizontalScrollBar();
////            scrollPane.setVisible(true);
//            panel.add(Gird(), BorderLayout.CENTER);
//        }
//        createTree();
//
//        if (Show > 0) {
////            setInfoTable();
//        }
//        table.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isRightMouseButton(e))
//                    popupMenu().show(table, e.getX(), e.getY());
//                if (e.getClickCount() == 2) {
//                    SetPath();
//                    if (currentFile.isDirectory()) {
//                        update();
//                        selectedFile = null;
//                    } else {
//                        try {
//                            desktop.open(currentFile);
//                            currentFile = currentFile.getParentFile();
//                            SetPath();
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
//                    SetPath();
//                    if (currentFile.isDirectory()) {
//                        update();
//                        selectedFile = null;
//                    } else {
//                        try {
//                            desktop.open(currentFile);
//                            currentFile = currentFile.getParentFile();
//                            update();
//                            SetPath();
//                            selectedFile = null;
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//                if (e.getKeyChar() == '\b') {
//                    if (currentFile.getName().equals("Desktop"))
//                        return;
//                    File file = new File(currentFile.getParent());
//                    currentFile = file;
//                    SetPath();
//                    update();
//                }
//                if (e.getKeyChar() == KeyEvent.VK_UP) {
////                     = table.getRowCount() - 1 ;
//                }
//
//            }
//        });
//        panel.add(DownPanel(), BorderLayout.SOUTH);
//        frame.setContentPane(panel);
//        frame.setVisible(true);
//    }
//
//
//    public void SetPath() {
//        Path = currentFile.getPath();
//        paths.add(Path);
//        Paths.setText(Path);
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
//                if (selectedFile == null) {
//                    return;
//                }
//                File file = new File(currentFile.getParent());
//                delete(selectedFile);
//                selectedFile = null;
//                //TODO  fffff
//                currentFile = file;
//                update();
//                SetPath();
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
//                public void actionPerformed(ActionEvent e) {
//                    if (selectedFile == null)
//                        return;
//                    File file = new File(currentFile.getParent());
//                    delete(selectedFile);
//                    selectedFile = null;
//                    currentFile = file;
//                    update();
//                    SetPath();
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
//                    String Type1 = Type(currentFile);
//                    JOptionPane.showMessageDialog(null, "Type :" + Type1 + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//
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
//                    String Type = Type(currentFile);
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
//
//                    JOptionPane.showMessageDialog(null, "Type :" + Type + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + (Long.toString(currentFile.length())) + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
//
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
//                SetPath();
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
//        Paths.setPreferredSize(new Dimension(500, 35));
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
//        Search.setPreferredSize(new Dimension(150, 35));
//        Search.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyChar() == '\n')
//                    if (Search.getText().equals("")) {
//                        update();
//                        SetPath();
//                        return;
//                    }
//                File[] files = new File[1];
//                files[0] = currentFile;
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
//                Show = 0;
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
//                Show = 0;
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
//        for (File file1 : file) {
//            if (file1.isDirectory()) {
//                if (file1.getName().contains(str)) {
//                    fileSearch.add(file1);
//                    searching(file1.listFiles(), str);
//
//                }
//            } else {
//                if (!file1.isDirectory()) {
//                    if (file1.getName().contains(str)) {
//                        fileSearch.add(file1);
//                    }
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
//                if (Show > 0) {
////                    Table();
//                    System.out.println("aas" + Path);
//                    panel.remove(Grid);
//                    Grid.removeAll();
//                    Grid = GridUpdate();
////                    JScrollPane scrollPane = new JScrollPane(Grid);
////                    scrollPane.createVerticalScrollBar();
////                    scrollPane.createHorizontalScrollBar();
////                    scrollPane.setVisible(true);
//                    panel.add(Grid, BorderLayout.CENTER);
//                    panel.revalidate();
//                    panel.repaint();
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
//                    SetPath();
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
////            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tscrollPane, table);
////            splitPane.setVisible(true);
////            panel.add(splitPane, BorderLayout.WEST);
//            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tscrollPane, Grid);
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
//    private JPanel Gird() {
//        int size = currentFile.listFiles().length;
//        Grid = new JPanel(new GridLayout(size/5 ,5));
//        Grid.setMaximumSize(new Dimension(100, 200));
//        File[] files = currentFile.listFiles();
//        System.out.println(currentFile.listFiles().length);
//        for (File file : files) {
//            JPanel panels = new JPanel(new BorderLayout(2, 2));
//            panels.setMaximumSize(new Dimension(100, 100));
//            panels.setMinimumSize(new Dimension(100, 100));
//            panels.setPreferredSize(new Dimension(100, 100));
//            panels.setSize(100, 100);
//            JButton button = new JButton();
//            button.setMaximumSize(new Dimension(100, 100));
//            button.setPreferredSize(new Dimension(100, 100));
//            button.setMinimumSize(new Dimension(100 , 100));
//            button.setSize(new Dimension(100 , 100));
//            JLabel textField = new JLabel();
//            textField.setText(file.getName());
//            Icon icon = fileSystemView.getSystemIcon(file);
//            button.setIcon(icon);
//            button.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    currentFile = file;
//                    if (SwingUtilities.isRightMouseButton(e))
//                        popupMenu().show(button, e.getX(), e.getY());
//                    if (e.getClickCount() == 2) {
//                        SetPath();
//                        if (currentFile.isDirectory()) {
//                            panel.remove(Grid);
//                            Grid.removeAll();
//                            Grid = GridUpdate();
//                            panel.add(Grid, BorderLayout.CENTER);
//                            panel.revalidate();
//                            panel.repaint();
//                            selectedFile = null;
//                        } else {
//                            try {
//                                desktop.open(currentFile);
//                                currentFile = currentFile.getParentFile();
//                                SetPath();
//                                panel.remove(Grid);
//                                Grid.removeAll();
//                                Grid = GridUpdate();
//                                panel.add(Grid, BorderLayout.CENTER);
//                                panel.revalidate();
//                                panel.repaint();
//                                selectedFile = null;
//                            } catch (IOException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            });
//            panels.add(button, BorderLayout.CENTER);
//            panels.add(textField, BorderLayout.SOUTH);
//            Grid.add(panels);
//        }
//        return Grid;
//    }
//
//    public JPanel GridUpdate() {
//        Grid = Gird();
//        Grid.revalidate();
//        Grid.repaint();
//        return Grid;
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
//        System.out.println(currentFile.getPath());
//        panel.remove(Grid);
//        Grid.removeAll();
//        Grid = GridUpdate();
//        panel.add(Grid, BorderLayout.CENTER);
//        panel.revalidate();
//        panel.repaint();
//        selectedFile = null;
////        Table();
//        int HowMany = currentFile.listFiles().length;
//        textArea.setText(Integer.toString(HowMany) + "items" + "   ");
//        /*}*/
//    }
//
//    public static String Type(File file){
//        String path = file.getName();
//        String[] strings = path.split(".");
//        System.out.println(path + "     "+ strings.length );
//        if (strings.length < 2)
//            return "";
//        if (file.isDirectory())
//            return "FileFolder";
//        return strings[strings.length-1];
//    }
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