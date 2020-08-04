import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBar {
    /**
     * Set the Menu Bar of File Manger
     *
     * @return The JMenuBar
     */
    public JMenuBar setMenuBar() {
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
        JMenuItem newFolder = new JMenuItem("NewFolder");
        newFolder.setMnemonic('o');
        newFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        newFolder.setToolTipText("Create New Folder In This Phat");
        JMenuItem deleteSelected = new JMenuItem("Delete Selected");
        deleteSelected.setMnemonic('D');
        deleteSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        deleteSelected.setToolTipText("Delete Selected File(s) OR Folder(s)");
        JMenuItem syncFiles = new JMenuItem("Sync File");
        syncFiles.setMnemonic('S');
        syncFiles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        syncFiles.setToolTipText("Sync the File OR Folder");
        JMenuItem rename = new JMenuItem("Rename");
        rename.setMnemonic(KeyEvent.VK_F2);
        rename.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rename.setToolTipText("Rename The File(s) OR Folder(s)");
        JMenuItem cut = new JMenuItem("Cut");
        cut.setMnemonic('t');
        cut.setToolTipText("Cut the Selected File OR Folder ");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        JMenuItem copy = new JMenuItem("Copy");
        copy.setMnemonic('o');
        copy.setToolTipText("Copy The Selected File OR Folder ");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        JMenuItem paste = new JMenuItem("Paste");
        paste.setMnemonic('P');
        paste.setToolTipText("Paste The Copied File OR Folder ");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        JMenuItem sync = new JMenuItem("Sync");
        sync.setMnemonic('S');
        sync.setToolTipText("Sync Selected File OR Folder ");
        sync.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        JMenuItem aboutMe = new JMenuItem("About Me");
        aboutMe.setMnemonic('A');
        aboutMe.setToolTipText("About The Person That Create This Pro");
        aboutMe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        help.add(aboutMe);
        JMenuItem helpItem = new JMenuItem("Help_Item");
        helpItem.setMnemonic('I');
        helpItem.setToolTipText("Talk About This Pro And What is Do");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        JMenuItem setting = new JMenuItem("Setting");
        setting.setMnemonic('S');
        setting.setToolTipText("Set the Pro How Do ");
        setting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
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
}
