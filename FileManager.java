import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class FileManager {
    //the main frame of the file manger
    private JFrame frame;
    // the main panel
    private JPanel panel;
    private FileSystemView fileSystemView;
    private Desktop desktop;
    //default Model of tree
    private DefaultTreeModel treeModel;
    //the tree that show directory like tree
    private JTree tree;
    private DefaultMutableTreeNode currentNode;
    //the now path of file
    private String Path;
    //the Array list of the path that u where in it
    private ArrayList<String> paths = new ArrayList<String>();
    //the Array list of the path of u where back from there
    private ArrayList<String> getPaths = new ArrayList<String>();
    //the showing file in big format
    private JPanel Grid;
    //the starting place of file manger
    private static String startState = System.getProperty("user.dir");
    //the text area of phat
    private JTextArea textArea;
    //the tree scroll pane
    private JScrollPane TScrollPane;
    //
    /**
     * setting Frame
     */
    //the state place to start
    private JTextField OpeningFile;
    //the directory that file was be upload in
    private JTextField SyncState;
    //the server computer IP
    private JTextField ComputerIP;
    //the server computer Port
    private JTextField ComputerPort;
    // the look and file of Swing
    private JComboBox LookAndFeel;
    //showing file in big Icon
    private JCheckBox ShowingFileGrid;
    //showing file in rows
    private JCheckBox ShowingFileTable;
    //the time that set to update the file
    private JComboBox TimeToSync;
    private static String timeToSync;
    private static String syncFile ;
    //now look and feel
    private static String Lookandfeel;
    //use to show which kind of file
    private static int Show = 1;
    //The Computer Port
    private static String computerPort;
    //The Computer Program IP
    private static String computerIP;
    /**
     * Copy and Paste
     */
    //the file that want to be copy
    private File[] copyFile;
    //file is cut or not
    private boolean isCut;

    /**
     * Searched file
     */
    private JTable table = new JTable();
    private JScrollPane taScrollPane;

    /**
     * Title of the application
     */
    //the title of pro
    public static final String APP_TITLE = "FileManager";

    /**
     * progress bar
     */
    private JProgressBar progressBar;

    /**
     * Set Up Panel
     */
    // the Addres bar of pro
    private static JTextField Paths = new JTextField();
    // the search bar
    private JTextField Search;
    private ArrayList<File> fileSearch = new ArrayList<>();
    private ArrayList<String> absPaths = new ArrayList<>();
    /**
     * Now File Place
     */
    //the now file that we in it
    private static File currentFile;
    //the number of file that selected
    private static File[] selectedFile;
    public ArrayList<File> addFile = new ArrayList<>();
    private JSplitPane splitPane;
    private TableModel tableModel = new TableModel();
    private Socket socket;
    private String savePath = "C:\\Users\\mohammad reza\\Desktop\\Text";
    private void sync() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                if (dataInputStream.readUTF().equals("Get information")) {
                    addFile = new ArrayList<>();
                    //TODO file Set
                    childOfSync(new File(savePath));
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    dataOutputStream.writeInt(addFile.size());
                    dataOutputStream.flush();
                    for (int i = 0; i < addFile.size(); i++) {
                        dataOutputStream.writeUTF(JustPath(addFile.get(i)));
                        dataOutputStream.flush();
                    }
                    for (int i = 0; i < addFile.size(); i++) {
                        Thread thread = new Thread(addFile.get(i) , "127.0.0.1" ,8888);
                        thread.run();
                    }
                }
                return null;
            }
        };
        SwingWorker<Void , Void > client = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                java.lang.Thread.sleep(10000);
                Socket socket = new Socket("127.0.0.1" , 8888);
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.writeUTF("Start");
                dataOutputStream.flush();
                addFile = new ArrayList<>();
                //TODO file Set
                childOfSync(new File(savePath));
                dataOutputStream.writeInt(addFile.size());
                dataOutputStream.flush();
                for (int i = 0; i < addFile.size(); i++) {
                    dataOutputStream.writeUTF(JustPath(addFile.get(i)));
                    dataOutputStream.flush();
                }
                socket.close();
                return null;
            }
        };
        swingWorker.execute();
        client.execute();
    }
    private String JustPath(File file){
        String s = "";
        for (int i = savePath.length(); i <file.getAbsolutePath().length() ; i++) {
            s += file.getAbsolutePath().charAt(i);
        }
        return s;
    }
    private void childOfSync(File file){
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                addFile.add(file1);
                childOfSync(file1);
            } else {
                addFile.add(file1);
            }
        }
    }
    private void Recursive(File file) {
        if (file.isDirectory()) {
            System.out.println(file.getName());
            File[] files = file.listFiles();
            ThreadForFolder thread = new ThreadForFolder(file , syncFile , Integer.parseInt(computerPort));
            thread.run();
            for (File f : files ) {
                System.out.println(f.getName());
                Recursive(f);
            }
        } else {
            Thread thread = new Thread(file , syncFile , Integer.parseInt(computerPort));
            System.out.println(file.getName());
            thread.run();
        }
    }

    /**
     * Set the file manger Frame
     */
    public FileManager()  {
        try {
            socket = new Socket("127.0.0.1" ,8888);
            DataOutputStream dataOutputStream =new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("Client");
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Warning in Socket");

        }
        sync();
        ReadData();
        frame = new JFrame(APP_TITLE);
        frame.setBounds(500, 500, 880, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        fileSystemView = FileSystemView.getFileSystemView();
        desktop = Desktop.getDesktop();
        splitPane = new JSplitPane(SwingConstants.VERTICAL);
        frame.setJMenuBar(setMenuBar());
        Path = startState;
        File file = new File(startState);
        currentFile = file;
        panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(3, 3, 3, 3));
        panel.add(UpPanel(), BorderLayout.NORTH);

        if (Show == 1) {
            Table();
        }

        if (Show == 0) {
            //TODO scrol
            Grid = Gird();
            Grid = GridUpdate();
//            JScrollPane scrollPane = new JScrollPane(Grid , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            panel.add(Grid, BorderLayout.CENTER);
        }
        createTree();
        if (Show == 1) {
            setInfoTable();
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu().show(table, e.getX(), e.getY());
                        selectedFile = null;
                    }

                    if (e.getClickCount() == 2) {
                        SetPath();
                        if (currentFile.isDirectory()) {
                            update();
                            selectedFile = null;
                        } else {
                            try {
                                desktop.open(currentFile);
                                currentFile = currentFile.getParentFile();
                                SetPath();
                                update();
                                selectedFile = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        SetPath();
                        if (currentFile.isDirectory()) {
                            System.out.println(selectedFile[0].getName() + "saSAASASASAZZ");
                            update();
                            selectedFile = null;
                        } else {
                            try {
                                desktop.open(currentFile);
                                currentFile = currentFile.getParentFile();
                                update();
                                SetPath();
                                selectedFile = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    if (e.getKeyChar() == '\b') {
                        if (currentFile.getName().equals("Desktop"))
                            return;
                        File file = new File(currentFile.getParent());
                        currentFile = file;
                        SetPath();
                        update();
                    }
                    if (e.getKeyChar() == KeyEvent.VK_UP) {

                    }

                }
            });
        }
        panel.add(DownPanel(), BorderLayout.SOUTH);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    /**
     * Set the path
     */
    public void SetPath() {
        Path = currentFile.getPath();
        if (!currentFile.isDirectory())
            paths.add(Path);
        Paths.setText(Path);
    }

    /**
     * Set the Menu Bar of File Manger
     *
     * @return The JMenuBar
     */
    private JMenuBar setMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu files = new JMenu("File");
        files.setMnemonic('F');
        files.setToolTipText("Do some Thing for file");
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E');
        edit.setToolTipText("Edit the file");
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        help.setToolTipText("Help U To Use This Pro");
        JMenuItem newFile = new JMenuItem("NewFile");
        newFile.setMnemonic('i');
        newFile.setToolTipText("Create New File In This Phat");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateNewFile();
                update();
            }
        });
        JMenuItem newFolder = new JMenuItem("NewFolder");
        newFolder.setMnemonic('o');
        newFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        newFolder.setToolTipText("Create New Folder In This Phat");
        newFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateNewFolder();
                update();
            }
        });
        JMenuItem deleteSelected = new JMenuItem("Delete Selected");
        deleteSelected.setMnemonic('D');
        deleteSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        deleteSelected.setToolTipText("Delete Selected File(s) OR Folder(s)");
        deleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    return;
                }
                if (currentFile.isDirectory())
                    delete(selectedFile);
                else {
                    File file = currentFile.getParentFile();
                    currentFile.delete();
                    currentFile = file;
                }
                selectedFile = null;
                update();
                SetPath();
            }
        });
        JMenuItem syncFiles = new JMenuItem("Sync File");
        syncFiles.setMnemonic('S');
        syncFiles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        syncFiles.setToolTipText("Sync the File OR Folder");
        syncFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new Socket("127.0.0.1", 8888);
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    dataOutputStream.writeUTF("Client");
                    System.out.println("Client Request Send");
                    dataOutputStream.flush();
                } catch (IOException e1) {
                    System.out.println("Warning in Socket");
                }
                sync();
            }
        });
        JMenuItem rename = new JMenuItem("Rename");
        rename.setMnemonic(KeyEvent.VK_F2);
        rename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rename.setToolTipText("Rename The File(s) OR Folder(s)");
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RenameFile();
                System.out.println(currentFile.getPath());
                currentFile = currentFile.getParentFile();
                update();
                SetPath();
            }
        });
        JMenuItem cut = new JMenuItem("Cut");
        cut.setMnemonic('t');
        cut.setToolTipText("Cut the Selected File OR Folder ");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CopyORCut();
                isCut = true;
                currentFile = currentFile.getParentFile();
                update();
            }
        });
        JMenuItem copy = new JMenuItem("Copy");
        copy.setMnemonic('o');
        copy.setToolTipText("Copy The Selected File OR Folder ");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentFile.getName() + " copy");
                CopyORCut();
                currentFile = currentFile.getParentFile();
                update();
            }
        });
        JMenuItem paste = new JMenuItem("Paste");
        paste.setMnemonic('P');
        paste.setToolTipText("Paste The Copied File OR Folder ");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (copyFile == null)
                    return;
                System.out.println(currentFile.getName() + " paste");
                CopyFile(copyFile, currentFile);
                if (isCut) {
                    for (int i = 0; i < copyFile.length; i++) {
                        copyFile[i].delete();
                    }
                }
                copyFile = null;
                isCut = false;

                update();
            }
        });
        JMenuItem sync = new JMenuItem("Sync");
        sync.setMnemonic('S');
        sync.setToolTipText("Sync Selected File OR Folder ");
        sync.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        JMenuItem aboutMe = new JMenuItem("About Me");
        aboutMe.setMnemonic('A');
        aboutMe.setToolTipText("About The Person That Create This Pro");
        aboutMe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        aboutMe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("About_Me");
                frame.setBounds(500, 500, 500, 500);
                frame.setLocationRelativeTo(null);
                frame.setBackground(Color.MAGENTA);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTextArea textArea = new JTextArea();
                textArea.setText("Hello It`s Me the Person how create this program" + "\n" +
                        "من محمد رضا قادری هستم" + "\n" +
                        "Email : Mrq112775@Gmail.com" + "\n" +
                        "خدایی راه بیاید با من");
                Font f = new Font("Serif", Font.ITALIC, 15);
                textArea.setFont(f);
                textArea.setBackground(Color.PINK);
                frame.add(textArea);
                frame.setVisible(true);
            }
        });
        help.add(aboutMe);
        JMenuItem helpItem = new JMenuItem("Help_Item");
        helpItem.setMnemonic('I');
        helpItem.setToolTipText("Talk About This Pro And What is Do");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Help_Item");
                frame.setBounds(500, 500, 700, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTextArea textArea = new JTextArea();
                textArea.setText("This Program is about the File Manager" + "\n With This Pro U can Edit , Copy , Paste and Some More Option Like Windows File Manager");
                Font f = new Font("Serif", Font.BOLD, 15);
                textArea.setFont(f);
                textArea.setBackground(Color.PINK);
                frame.add(textArea);
                frame.setVisible(true);
            }
        });
        JMenuItem setting = new JMenuItem("Setting");
        setting.setMnemonic('S');
        setting.setToolTipText("Set the Pro How Do ");
        setting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        setting.addActionListener(new ActionListener() {
            @Override
            //TODO 111
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Setting");
                frame.setBounds(500, 500, 700, 500);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel(new GridLayout(8, 1));
                JPanel openfile = new JPanel(new GridLayout(1, 2));
                OpeningFile = new JTextField(startState);
                Font f = new Font("Serif", Font.PLAIN, 15);
                OpeningFile.setEditable(true);
                OpeningFile.setFont(f);
                OpeningFile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startState = OpeningFile.getText();
                    }
                });
                JTextField jTextField = new JTextField("Opening File Path");
                jTextField.setEditable(false);
                jTextField.setPreferredSize(new Dimension(100, 20));
                openfile.add(jTextField);
                openfile.add(OpeningFile);
                panel.add(openfile);
                JPanel syncstate = new JPanel(new GridLayout(1, 2));
                SyncState = new JTextField(syncFile);
                JTextField jTextField1 = new JTextField("Sync Save State");
                jTextField1.setEditable(false);
                jTextField1.setPreferredSize(new Dimension(100, 20));
                SyncState.setEditable(true);
                SyncState.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        syncFile = SyncState.getText();
                    }
                });
                syncstate.add(jTextField1);
                syncstate.add(SyncState);
                panel.add(syncstate);
                JPanel compIP = new JPanel(new GridLayout(1, 2));
                JTextField jTextField2 = new JTextField("The Computer IP");
                jTextField2.setEditable(false);
                jTextField2.setPreferredSize(new Dimension(100, 20));
                ComputerIP = new JTextField(computerIP);
                ComputerIP.setEditable(true);
                ComputerIP.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        computerIP = ComputerIP.getText();
                    }
                });
                compIP.add(jTextField2);
                compIP.add(ComputerIP);
                panel.add(compIP);
                JPanel compPort = new JPanel(new GridLayout(1, 2));
                JTextField jTextField3 = new JTextField("The DComputer Port ");
                jTextField3.setEditable(false);
                jTextField3.setPreferredSize(new Dimension(100, 20));
                ComputerPort = new JTextField(computerPort);
                ComputerPort.setEditable(true);
                ComputerPort.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        computerPort = ComputerPort.getText();
                    }
                });
                compPort.add(jTextField3);
                compPort.add(ComputerPort);
                panel.add(compPort);
                String[] Lookandfeel1 = {"Windows", "Nimbus", "Motif"};
                JPanel Pro = new JPanel(new GridLayout(1, 3));
                LookAndFeel = new JComboBox(Lookandfeel1);
                Pro.add(LookAndFeel);
                JPanel panel1 = new JPanel(new GridLayout(1, 2));
                ShowingFileGrid = new JCheckBox("Grid Showing");
                ShowingFileTable = new JCheckBox("Table Showing");
                if (Show == 1)
                    ShowingFileTable.setSelected(true);
                else
                    ShowingFileGrid.setSelected(true);
                panel1.add(ShowingFileGrid);
                panel1.add(ShowingFileTable);
                Pro.add(panel1);
                String[] Time = {"every min ", "5 min", "10 min ", " 30 min ", "1 hour"};
                TimeToSync = new JComboBox(Time);
                Pro.add(TimeToSync);
                panel.add(Pro);
                if (startState.equals("") || !(new File(startState)).isDirectory())
                    startState = System.getProperty("user.dir");
                TimeToSync.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (TimeToSync.getSelectedIndex() == 0)
                            timeToSync = "60";
                        if (TimeToSync.getSelectedIndex() == 1)
                            timeToSync = "300";
                        if (TimeToSync.getSelectedIndex() == 2)
                            timeToSync = "600";
                        if (TimeToSync.getSelectedIndex() == 3)
                            timeToSync = "1800";
                        if (TimeToSync.getSelectedIndex() == 4)
                            timeToSync = "6000";
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });

                LookAndFeel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (LookAndFeel.getSelectedIndex() == 0)
                            Lookandfeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                        if (LookAndFeel.getSelectedIndex() == 1)
                            Lookandfeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                        if (LookAndFeel.getSelectedIndex() == 2)
                            Lookandfeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                OpeningFile.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyChar() == '\n')
                            startState = OpeningFile.getText();
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                ShowingFileTable.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (ShowingFileTable.isSelected())
                            Show = 1;
                        else
                            Show = 0;
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                ShowingFileGrid.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (ShowingFileGrid.isSelected())
                            Show = 0;
                        else
                            Show = 1;
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                ComputerPort.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        computerPort = ComputerPort.getText();
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                ComputerIP.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        computerIP = ComputerIP.getText();
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                SyncState.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        syncFile = SyncState.getText();
                        Data(Lookandfeel + "/" + startState + "/" + Show + "/"+syncFile +"/"+ timeToSync +"/"+ computerPort + "/" + computerIP);
                    }
                });
                frame.add(panel);
                frame.setVisible(true);
            }
        });
        files.add(newFile);
        files.add(newFolder);
        files.add(deleteSelected);
        files.add(syncFiles);
        edit.add(rename);
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(sync);
        help.add(setting);
        help.add(helpItem);
        jMenuBar.add(files);
        jMenuBar.add(edit);
        jMenuBar.add(help);

        return jMenuBar;
    }

    /**
     * Set the Mouse menu handler
     *
     * @return the PopUpMenu
     */
    private JPopupMenu popupMenu() {
        JPopupMenu PopUpMenu = new JPopupMenu("RightClick");
        if (selectedFile != null) {
            JMenuItem open = new JMenuItem("Open");
            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetPath();
                    if (currentFile.isDirectory()) {
                        update();
                        selectedFile = null;
                    } else {
                        try {
                            desktop.open(currentFile);
                            currentFile = currentFile.getParentFile();
                            SetPath();
                            update();
                            selectedFile = null;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            JMenuItem cut = new JMenuItem("Cut");
            cut.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CopyORCut();
                    currentFile = currentFile.getParentFile();
                    update();
                    isCut = true;
                }
            });
            JMenuItem copy = new JMenuItem("Copy");
            copy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(currentFile.getName() + " copy");
                    CopyORCut();
                    currentFile = currentFile.getParentFile();
                    update();
                }
            });
            JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedFile == null) {
                        return;
                    }
                    if (currentFile.isDirectory())
                        delete(selectedFile);
                    else {
                        File file = currentFile.getParentFile();
                        currentFile.delete();
                        currentFile = file;
                    }
                    selectedFile = null;
                    update();
                    SetPath();
                }
            });
            JMenuItem paste = new JMenuItem("Paste");
            paste.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(currentFile.getName() + " paste");
                    CopyFile(copyFile, currentFile);
                    if (isCut) {
                        for (int i = 0; i < copyFile.length; i++) {
                            copyFile[i].delete();
                        }
                    }
                    copyFile = null;
                    isCut = false;
                    update();
                }
            });
            JMenuItem properties = new JMenuItem("Properties");
            properties.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long lastModified = currentFile.lastModified();
                    int directory = 0;
                    int file = 0;
                    if (currentFile.isDirectory()) {
                        for (File i : currentFile.listFiles()) {
                            if (i.isDirectory())
                                directory++;
                            else
                                file++;
                        }
                    }
                    long s = currentFile.length();
                    float d = (float) (s / 1000000);
                    String Type1 = Type(currentFile);
                    JOptionPane.showMessageDialog(null, "Type :" + Type1 + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + d + "MB (" + (Long.toString(currentFile.length())) + " byte) " + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
                    selectedFile = null;
                }
            });
            PopUpMenu.add(open);
            PopUpMenu.add(cut);
            PopUpMenu.add(copy);
            PopUpMenu.add(paste);
            PopUpMenu.add(delete);
            PopUpMenu.add(properties);
        } else {
            System.out.println(currentFile.getName());
            JMenuItem newFolder = new JMenuItem("NewFolder");
            newFolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateNewFolder();
                    selectedFile = null;
                    update();
                }
            });
            JMenuItem newFile = new JMenuItem("NewFile");
            newFile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateNewFile();
                    selectedFile = null;
                    update();
                }
            });
            JMenuItem properties = new JMenuItem("Properties");
            properties.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long lastModified = currentFile.lastModified();
                    int directory = 0;
                    int file = 0;
                    //Todo type
                    String Type = Type(currentFile);
                    if (currentFile.isDirectory()) {
                        for (File i : currentFile.listFiles()) {
                            if (i.isDirectory())
                                directory++;
                            else
                                file++;
                        }
                    }
                    long s = currentFile.length();
                    float d = (float) (s / 1000000);
                    JOptionPane.showMessageDialog(null, "Type :" + Type + "\n" + "Location : " + currentFile.getAbsolutePath() + "\n" + "Size : " + d + "MB (" + (Long.toString(currentFile.length())) + " byte) " + "\n" + "Created : " + (new Date(lastModified)) + "\n" + "Contains : " + file + " Files," + directory + " Folder");
                    selectedFile = null;
                }

            });

            PopUpMenu.add(newFile);
            PopUpMenu.add(newFolder);
            PopUpMenu.add(properties);
        }
        return PopUpMenu;
    }

    /**
     * The Upest Panel that have address and search bar and back and forward and undo of file manger
     * this panel set in North of main panel
     *
     * @return the panel
     */
    private JPanel UpPanel() {
        JPanel UpPanel = new JPanel(new BorderLayout());
        Icon backIcon = new ImageIcon("back.png");
        Icon forwardIcon = new ImageIcon("Forward.png");
        Icon undoIcon = new ImageIcon("undo.png");
        JButton back = new JButton(backIcon);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 1) {
                        System.out.println(paths.size() + " ");
                        int size = paths.size();
                        if (size > 0) {
                            Paths.setText(paths.get(size - 2));
                            getPaths.add(paths.get(size - 1));
                            paths.remove(size - 2);
                            currentFile = new File(Paths.getText());
                            System.out.println(currentFile.getName() + "     " + currentFile.getPath());
                            update();
                        }
                    }
                } catch (Exception e22) {
                    return;
                }
            }
        });
        JButton forward = new JButton(forwardIcon);
        JButton undo = new JButton(undoIcon);
        forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 1) {
                        System.out.println(getPaths.size() + " ");
                        int size = getPaths.size();
                        if (size > 0) {
                            Paths.setText(getPaths.get(size - 2));
                            paths.add(getPaths.get(size - 1));
                            getPaths.remove(size - 2);
                            currentFile = new File(Paths.getText());
                            System.out.println(currentFile.getName() + "     " + currentFile.getPath());
                            update();
                        }
                    }
                } catch (Exception e22) {
                    return;
                }
            }

        });
        undo.setBackground(Color.LIGHT_GRAY);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Show == 1) {
                    if (currentFile.getName().equals("Desktop"))
                        return;
                    File file = new File(currentFile.getParent());
                    currentFile = file;
                    SetPath();
                    update();
                } else {
                    try {
                        File file = new File(currentFile.getParent());
                        currentFile = file;
                        SetPath();
                        update();
                    } catch (Exception e1) {
                    }
                }
            }
        });
        back.setMaximumSize(new

                Dimension(10, 10));
//        back.setMinimumSize(new Dimension(5, 5));
        back.setPreferredSize(new

                Dimension(35, 35));
        forward.setMaximumSize(new

                Dimension(10, 10));
//        forward.setMinimumSize(new Dimension(5, 5));
        forward.setPreferredSize(new

                Dimension(35, 35));
        undo.setMaximumSize(new

                Dimension(20, 20));
//        undo.setMinimumSize(new Dimension(5, 5));
        undo.setPreferredSize(new

                Dimension(45, 45));
        JPanel West = new JPanel(new FlowLayout());
        West.add(back);
        West.add(forward);
        West.add(undo);
        UpPanel.add(West, BorderLayout.WEST);
        Paths = new

                JTextField(System.getProperty(Path));
        Paths.setPreferredSize(new

                Dimension(500, 20));
        Paths.setMaximumSize(new

                Dimension(500, 20));
        Paths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(e.getActionCommand());
                    currentFile = file;
                    update();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "There is NO Path like That", "Error Massage", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        UpPanel.add(Paths, BorderLayout.CENTER);
        Search = new

                JTextField("Search");
        Search.setPreferredSize(new

                Dimension(150, 20));
        Search.setMaximumSize(new

                Dimension(150, 20));
        Search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n')
                    if (Search.getText().equals("")) {
                        update();
                        SetPath();
                        return;
                    }
                File[] files = new File[1];
                files[0] = currentFile;
                searching(files, Search.getText());
                files = new File[fileSearch.size()];
                for (int i = 0; i < fileSearch.size(); i++) {
                    files[i] = fileSearch.get(i);
                }
                tableModel.setFiles(files);
                fileSearch = new ArrayList<>();
            }
        });
        UpPanel.add(Search, BorderLayout.EAST);

        return UpPanel;
    }

    /**
     * The Down Panel that have tow icon to set the show formation
     *
     * @return the panel
     */
    private JPanel DownPanel() {
        JPanel DownPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Icon Grid = new ImageIcon("list (2).png");
        Icon Table = new ImageIcon("grid (2).png");
        JButton Grids = new JButton(Grid);
        Grids.setMaximumSize(new Dimension(40, 40));
        Grids.setPreferredSize(new Dimension(40, 40));
        Grids.setMinimumSize(new Dimension(40, 40));
        Grids.setSize(new Dimension(40, 40));
        Grids.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Show == 0) {
                    Show = 1;
                    startState = currentFile.getPath();
                    //TODO convert
                    Convert();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });
        JButton Tables = new JButton(Table);
        Tables.setMaximumSize(new Dimension(40, 40));
        Tables.setPreferredSize(new Dimension(40, 40));
        Tables.setMinimumSize(new Dimension(40, 40));
        Tables.setSize(new Dimension(40, 40));
        Tables.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Show == 1) {
                    Show = 0;
                    startState = currentFile.getPath();
                    //TODO convert
                    Convert();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(Tables, c);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.ipadx = 1;
        c1.ipady = 0;
        c1.gridx = 15;
        c1.gridy = 0;
        panel.add(Grids, c1);
        JPanel panel1 = new JPanel();
        int HowMany = currentFile.listFiles().length;
        textArea = new JTextArea();
        textArea.setSize(new Dimension(300, 300));
        textArea.setText(Integer.toString(HowMany) + "items" + "   ");
        panel1.add(textArea);
        DownPanel.add(panel, BorderLayout.EAST);
        DownPanel.add(panel1, BorderLayout.WEST);
        return DownPanel;
    }

    /**
     * set the file for cut or copy
     */
    private void CopyORCut() {
        copyFile = null;
        System.out.println(currentFile.getName() + "  ");
        selectedFile = currentFile.listFiles();
        if (!currentFile.isDirectory()) {
            File[] files = new File[1];
            files[0] = currentFile;
            copyFile = files;
        } else {
            File[] files = new File[selectedFile.length];
            int counter = 0;
            for (File file : selectedFile) {
                files[counter] = file;
                counter++;
            }
            copyFile = files;
        }
        selectedFile = null;
    }

    /**
     * Rename the file that u select that if some thing get wrong told to u
     */
    public void RenameFile() {
        //TODO  is ok?
        int count = 0;
        if (selectedFile.length == 1) {
            JFrame frame = new JFrame("RenameFile");
            String newName = JOptionPane.showInputDialog(frame, "New Name");
            File[] files = currentFile.listFiles();
            for (File file : files) {
                if (fileSystemView.getSystemDisplayName(file).equals(newName))
                    count++;
                break;
            }

            if (count > 0) {
                JOptionPane.showMessageDialog(frame, "The Process not done !! Please Try Again", "Error Massage", JOptionPane.ERROR_MESSAGE);
            }
            if (count == 0) {
                System.out.println(newName);
                File file = new File((Path + "\\" + newName));
                currentFile.renameTo(file);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please Select one Item", "Error Massage", JOptionPane.ERROR_MESSAGE);
        }
        if (selectedFile == null) {
            return;
        }
    }

    /**
     * Create new Directory in current path
     */
    public void CreateNewFolder() {
        JFrame frame = new JFrame("CreateNewFolder");
        String newName = JOptionPane.showInputDialog(frame, "New Folder Name");
        File[] files = currentFile.listFiles();
        int count = 0;
        for (File file : files) {
            if (fileSystemView.getSystemDisplayName(file).equals(newName))
                if (file.isDirectory())
                    count++;
            break;
        }
//        if (JOptionPane.CANCEL_OPTION == Integer.parseInt(newName)) {
//            JOptionPane.showMessageDialog(null , "U R Process was Cancel" , "Massage" , JOptionPane.CANCEL_OPTION);
//        }
        if (count > 0)
            newName += "Copy";
        File file = new File(Path + "\\" + newName);
        if (!file.mkdir()) {
            JOptionPane.showMessageDialog(frame, "The Process Not Done !! Please Try Again", "Error Massage", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "The Process Not Done !! Please Try Again", "Error Massage", JOptionPane.ERROR_MESSAGE);
        }

        /*treeModel.insertNodeInto(new DefaultMutableTreeNode(file), currentNode, currentNode.getChildCount());*/
        update();
    }

    /**
     * Create new file in current path with the type of file
     */
    public void CreateNewFile() {
        JFrame frame = new JFrame("CreateNewFile");
        String newName = JOptionPane.showInputDialog(frame, "New File Name With Type :");
        //see have file like that
        File[] files = currentFile.listFiles();
        int count = 0;
        for (File file : files) {
            if (fileSystemView.getSystemDisplayName(file).equals(newName))
                count++;
            break;
        }
        if (count != 0) {
            //TODO bad form
            System.out.println("hello");
            String[] strings = newName.split(Pattern.quote("."), 2);
            System.out.println(strings.length);
            strings[0] = strings[0] + "-Copies";
            System.out.println(strings[0]);
//            newName = strings[0] + "." + strings[1];
            System.out.println(newName);
        }

        File file = new File(Path + "\\" + newName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "the procces not done !! please try again", "Errored Massage", JOptionPane.ERROR_MESSAGE);
        }
        update();
    }

    /**
     * Copy the file (or array of the files) int current path
     *
     * @param copyFile    the file(s) want to copy
     * @param currentFile the file that want to be copied
     */
    private static void CopyFile(File[] copyFile, File currentFile) {
        File file1;
        File file2;
        FileInputStream inputStream;
        FileOutputStream outputStream;
        int lenght;
        byte[] buffer = new byte[2048];
        for (File i : copyFile) {
            try {
                if (i.isDirectory()) {
                    file2 = new File(currentFile + "\\" + i.getName());
                    file2.mkdir();
                    CopyFile(i.listFiles(), file2);

                } else {
                    file1 = new File(i.getAbsolutePath());
                    file2 = new File(currentFile + "\\" + i.getName());
                    outputStream = new FileOutputStream(file2);
                    inputStream = new FileInputStream(file1);
                    while ((lenght = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, lenght);
                    }
                    inputStream.close();
                    outputStream.close();
                }
            } catch (IOException in) {
                System.out.println("There Is Something Wrong");
            }
        }
    }

    /**
     * delete the file
     *
     * @param file the file that was be deleted
     */
    public static void delete(File[] file) {
        File file1 = new File(currentFile.getParent());
        for (File m : file) {
            if (m == null)
                return;
            if (m.isDirectory()) {
                delete(m.listFiles());
            }
            file1 = new File(m.getParent());
            m.delete();
        }
        currentFile = file1;
    }

    /**
     * Search for a file
     *
     * @param file the current file
     * @param str  the string that want to be searched
     */
    private void searching(File[] file, String str) {
        for (File file1 : file) {
            if (file1.isDirectory()) {
                if (file1.getName().contains(str)) {
                    fileSearch.add(file1);
                    searching(file1.listFiles(), str);

                }
            } else {
                if (!file1.isDirectory()) {
                    if (file1.getName().contains(str)) {
                        fileSearch.add(file1);
                    }
                }
            }
        }
    }

    /**
     * the Data of the pro that should pro start with that
     *
     * @param string the string of rules
     */
    private void Data(String string) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream("File.txt"));
            printStream.print(string);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "some Thing go Wrong");
        }
    }

    /**
     * read the data from file
     */
    public static void ReadData() {
        String Data = "";
        try {
            FileReader fileReader = new FileReader("File.txt");
            int Count;
            while ((Count = fileReader.read()) != -1) {
                Data += (char) Count;
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Some Thing Go Wrong (the File Not exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Data.equals("")) {
            String[] strings = Data.split("/", 7);
            System.out.println(strings[0] + "  " + strings[1] + " " + strings[2] + "  " + strings[3] + "  " + strings[4] + "  "+ strings[5] + "  "+ strings[6] + "  " + strings.length);
            Lookandfeel = strings[0];
            startState = strings[1];
            Show = Integer.parseInt(strings[2]);
            syncFile = strings[3];
            timeToSync = strings[4];
            computerPort = strings[5];
            computerIP = strings[6];
        }
    }

    /**
     * Create the Tree for file manager
     */
    private void createTree() {
        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent tse) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
                File file = (File) node.getUserObject();
                currentFile = file;
                currentNode = node;
                Path = file.getPath();
                if (Path.equals("::{20D04FE0-3AEA-1069-A2D8-08002B30309D}")) {
                    Path = "This PC";
                }
                if (Path.equals("::{031E4825-7B94-4DC3-B131-E946B44C8DD5}")) {
                    Path = "Libraries";
                }
                getChildren(node, file);
                paths.add(Path);
                Paths.setText(Path);
                if (Show == 0) {
                    System.out.println("aas" + Path);
                    panel.remove(Grid);
                    Grid.removeAll();
                    Grid = GridUpdate();
//                    JScrollPane scrollPane = new JScrollPane(Grid);
//                    scrollPane.createVerticalScrollBar();
//                    scrollPane.createHorizontalScrollBar();
//                    scrollPane.setVisible(true);
                    panel.add(Grid, BorderLayout.CENTER);
                    panel.revalidate();
                    panel.repaint();
                } else if (Show == 1) {
                    Table();
                }
                selectedFile = null;
            }
        };
        File fileRoot = fileSystemView.getRoots()[0];
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
        getChildren(root, fileRoot);
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        tree.addTreeSelectionListener(treeSelectionListener);
        tree.expandRow(0);
        tree.setVisibleRowCount(10);
        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\b') {
                    if (currentFile.getName().equals("Desktop"))
                        return;
                    File file = new File(currentFile.getParent());
                    currentFile = file;
                    SetPath();
//                    update();
                }

            }
        });
        TScrollPane = new JScrollPane(tree);
        TScrollPane.createVerticalScrollBar();
        TScrollPane.createHorizontalScrollBar();
        TScrollPane.setVisible(true);
        if (Show == 0) {
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TScrollPane, Grid);
            splitPane.setVisible(true);
            panel.add(splitPane, BorderLayout.WEST);
        } else if (Show == 1) {
            //TODO hete
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TScrollPane, table);
            splitPane.setVisible(true);
            panel.add(splitPane, BorderLayout.WEST);
        }
    }

    /**
     * set the childrned for Table
     *
     * @param node the tree node
     * @param file current file
     */
    private void getChildren(DefaultMutableTreeNode node, File file) {
        //in current file we search for baby@@
        File[] files = file.listFiles();
//        int HowMany = file.listFiles().length;
//        textArea.setText(Integer.toString(HowMany) + "items" + "   ");
        for (File file1 : files) {
            if (!file1.isDirectory())
                continue;
            DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(file1);
            node.add(node1);
        }
    }

    /**
     * Create Model for Table
     */
    private void Table() {
        // Create a Model For Table
        if (currentFile == null)
            currentFile = new File(Path);
        File[] files = currentFile.listFiles();
        tableModel.setFiles(files);
        table.setModel(tableModel);
        try {
            Icon icon = fileSystemView.getSystemIcon(files[0]);
            table.setRowHeight(icon.getIconHeight() + 7);
            table.getColumnModel().getColumn(0).setPreferredWidth(35);
            table.getColumnModel().getColumn(1).setPreferredWidth(370);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
            table.getColumnModel().getColumn(3).setPreferredWidth(230);
            table.setAutoCreateRowSorter(true);
            table.setShowHorizontalLines(true);
            table.setShowVerticalLines(true);
            table.setDragEnabled(true);
            //TODO
            table.setTransferHandler(new FileListTransferHandler(table));
            frame.setTitle(fileSystemView.getSystemDisplayName(currentFile));
        } catch (Exception e) {
        }

    }

    /**
     * set the information for table
     */
    private void setInfoTable() {
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] ints = table.getSelectionModel().getSelectedIndices();
                File[] files = currentFile.listFiles();
                File[] files1 = new File[ints.length];
                int counter = 0;
                for (Integer i : ints) {
                    try {
                        currentFile = files[i];
                        files1[counter] = files[i];
                        counter++;
                    } catch (Exception e1) {
                    }
                }
                selectedFile = files1;
            }
        });
        taScrollPane = new JScrollPane(table);
        splitPane.add(taScrollPane);
        panel.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Set the JPanel for bigger size of folder
     *
     * @return the Grid Jpanel
     */
    private JPanel Gird() {
        int size = currentFile.listFiles().length;
        Grid = new JPanel(new FlowLayout(/*GridLayout(size/5 , 5)*/));
//        Grid.setMaximumSize(new Dimension(500, 200));
        //motmaen nistam
//        Grid.add(new DrawRect());
        File[] files = currentFile.listFiles();
        System.out.println(currentFile.listFiles().length);
        for (File file : files) {
            JPanel panels = new JPanel(new BorderLayout(2, 2));
            panels.setMaximumSize(new Dimension(100, 100));
            panels.setMinimumSize(new Dimension(100, 100));
            panels.setPreferredSize(new Dimension(100, 100));
            panels.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e))
                        popupMenu().show(panels, e.getX(), e.getY());
                }
            });
            panels.setSize(100, 100);
            JButton button = new JButton();
            button.setMaximumSize(new Dimension(70, 70));
            button.setPreferredSize(new Dimension(70, 70));
            button.setMinimumSize(new Dimension(70, 70));
            button.setSize(new Dimension(70, 70));
            JLabel textField = new JLabel();
            textField.setText(file.getName());
            textField.setMaximumSize(new Dimension(file.getName().length(), 10));
            Icon icon = fileSystemView.getSystemIcon(file);
            button.setIcon(icon);
            long lastModified = currentFile.lastModified();
            ArrayList<String> directory = new ArrayList<>();
            ArrayList<String> filess = new ArrayList<>();
            if (file.isDirectory()) {
                for (File X : file.listFiles()) {
                    if (X.isDirectory())
                        directory.add(X.getName());
                    else
                        filess.add(X.getName());
                }
            }
            long s = file.length();
            float d = (float) (s / 1000000);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFile = file;
                    if (SwingUtilities.isRightMouseButton(e))
                        popupMenu().show(button, e.getX(), e.getY());
                    if (e.getClickCount() == 2) {
                        SetPath();
                        if (currentFile.isDirectory()) {
                            panel.remove(Grid);
                            Grid.removeAll();
                            Grid = GridUpdate();
                            panel.add(Grid, BorderLayout.CENTER);
                            panel.revalidate();
                            panel.repaint();
                            selectedFile = null;
                        } else {
                            try {
                                desktop.open(currentFile);
                                currentFile = currentFile.getParentFile();
                                SetPath();
                                panel.remove(Grid);
                                Grid.removeAll();
                                Grid = GridUpdate();
                                panel.add(Grid, BorderLayout.CENTER);
                                panel.revalidate();
                                panel.repaint();
                                selectedFile = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            if (directory.size() >= 2) {
                if (filess.size() >= 2)
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + "," + directory.get(1) + "<br> Files :" + filess.get(0) + "," + filess.get(1) + "... </html>");
                else if (filess.size() == 1) {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + "," + directory.get(1) + "<br> Files :" + filess.get(0) + " </html>");
                } else {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + "," + directory.get(1) + " </html>");
                }
            } else if (directory.size() == 1) {
                if (filess.size() >= 2)
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + "<br> Files :" + filess.get(0) + "," + filess.get(1) + "... </html>");
                else if (filess.size() == 1) {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + "<br> Files :" + filess.get(0) + " </html>");
                } else {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Folders : " + directory.get(0) + " </html>");
                }
            } else {
                if (filess.size() >= 2)
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Files :" + filess.get(0) + "," + filess.get(1) + "... </html>");
                else if (filess.size() == 1) {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + "<br> Files :" + filess.get(0) + " </html>");
                } else {
                    panels.setToolTipText("<html> Date created : " + (new Date(lastModified)) + "<br>  Size : " + d + "MB (" + (Long.toString(file.length())) + " byte)" + " </html>");
                }
            }
            panels.add(button, BorderLayout.CENTER);
            panels.add(textField, BorderLayout.SOUTH);
            Grid.add(panels);
        }
        return Grid;
    }

    /**
     * Update THe Grid Panel
     *
     * @return the updated Grid Panel
     */
    public JPanel GridUpdate() {
        Grid = Gird();
        Grid.revalidate();
        Grid.repaint();
        return Grid;
    }

    public static String getLookandfeel() {
        return Lookandfeel;
    }

    public static void setLookandfeel(String lookandfeel) {
        Lookandfeel = lookandfeel;
    }

    /**
     * Update  the panel(Grid or Table)
     */
    private void update() {
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
        if (Show == 0) {
            System.out.println(currentFile.getPath());
            splitPane.remove(taScrollPane);
            panel.remove(splitPane);
            Grid.removeAll();
            panel.remove(Grid);
            Grid = GridUpdate();
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TScrollPane, Grid);
            splitPane.setVisible(true);
            panel.add(splitPane, BorderLayout.WEST);
            panel.revalidate();
            panel.repaint();
        } else if (Show == 1) {
            Table();
            selectedFile = null;
        }
        int HowMany = currentFile.listFiles().length;
        textArea.setText(Integer.toString(HowMany) + "items" + "   ");

    }

    /**
     * Define the Type Of the File
     *
     * @param file the File That want to know the type
     * @return the type of file
     */
    public static String Type(File file) {
        String path = file.getName();
        String[] strings = path.split(Pattern.quote("."));
        if (strings.length < 2)
            return "FileFolder";
        if (file.isDirectory())
            return "FileFolder";
        if (strings[strings.length - 1].equals("pdf"))
            return "PDF File";
        if (strings[strings.length - 1].equals("jpg"))
            return "JPG File";
        if (strings[strings.length - 1].equals("png"))
            return "PNG File";
        if (strings[strings.length - 1].equals("exe"))
            return "Application";
        if (strings[strings.length - 1].equals("zip"))
            return "WinRAR ZIP archive";
        if (strings[strings.length - 1].equals("mp4"))
            return "KMP-MP4 Audio/Video File";
        if (strings[strings.length - 1].equals("txt"))
            return "Text Document";
        if (strings[strings.length - 1].equals("html"))
            return "HTML File";
        if (strings[strings.length - 1].equals("mp3"))
            return "KMP-MPEG Layer3 Audio File";
        if (strings[strings.length - 1].equals("docx"))
            return "Microsoft Office Word Document";
        if (strings[strings.length - 1].equals("c"))
            return "C Source File";
        if (strings[strings.length - 1].equals("java"))
            return "Java Source File";
        return strings[strings.length - 1];
    }

    /**
     * When Cover form Table showing to Folder Showing or in other way
     */
    private void Convert() {
        if (Show == 0) {
            System.out.println(currentFile.getName());
            taScrollPane.remove(table);
            splitPane.remove(taScrollPane);
            panel.remove(splitPane);
            Grid = Gird();
            panel.remove(Grid);
            Grid.removeAll();
            Grid = GridUpdate();
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TScrollPane, Grid);
            splitPane.setVisible(true);
            panel.add(splitPane, BorderLayout.WEST);
            panel.revalidate();
            panel.repaint();
            selectedFile = null;
        } else if (Show == 1) {
            System.out.println(currentFile.getName());
            panel.remove(Grid);
            splitPane.remove(Grid);
            panel.remove(splitPane);
            Table();
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TScrollPane, table);
            splitPane.setVisible(true);
            //todo mouse
            panel.add(splitPane, BorderLayout.WEST);
            setInfoTable();//////jsdjsdladj

            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu().show(table, e.getX(), e.getY());
                        selectedFile = null;
                    }
                    if (e.getClickCount() == 2) {
                        SetPath();
                        if (currentFile.isDirectory()) {
                            update();
                            selectedFile = null;
                        } else {
                            try {
                                desktop.open(currentFile);
                                currentFile = currentFile.getParentFile();
                                SetPath();
                                update();
                                selectedFile = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        SetPath();
                        if (currentFile.isDirectory()) {
                            System.out.println(selectedFile[0].getName() + "assasa");
                            update();
                            selectedFile = null;
                        } else {
                            try {
                                desktop.open(currentFile);
                                currentFile = currentFile.getParentFile();
                                update();
                                SetPath();
                                selectedFile = null;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    if (e.getKeyChar() == '\b') {
                        if (currentFile.getName().equals("Desktop"))
                            return;
                        File file = new File(currentFile.getParent());
                        currentFile = file;
                        SetPath();
                        update();
                    }
                    if (e.getKeyChar() == KeyEvent.VK_UP) {
//                     = table.getRowCount() - 1 ;
                    }

                }
            });
            panel.revalidate();
            panel.repaint();
            selectedFile = null;
        }
    }

    class FileTreeCellRenderer extends DefaultTreeCellRenderer {


        private FileSystemView fileSystemView;

        private JLabel label;

        FileTreeCellRenderer() {
            label = new JLabel();
            label.setOpaque(true);
            fileSystemView = FileSystemView.getFileSystemView();
        }

        @Override
        public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean selected,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            File file = (File) node.getUserObject();
            currentFile = file;
            label.setIcon(fileSystemView.getSystemIcon(file));
            label.setText(fileSystemView.getSystemDisplayName(file));
            label.setToolTipText(file.getPath());
            if (selected) {
                label.setBackground(backgroundSelectionColor);
                label.setForeground(textSelectionColor);
            } else {
                label.setBackground(backgroundNonSelectionColor);
                label.setForeground(textNonSelectionColor);
            }
            return label;
        }
    }

    /**
     * Add the files that are contained within the directory of this node.
     * Thanks to Hovercraft Full Of Eels for the SwingWorker fix.
     */
    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    Table();
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    System.out.print(1);
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

}