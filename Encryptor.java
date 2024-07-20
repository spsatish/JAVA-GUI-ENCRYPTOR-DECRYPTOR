import java.util.ArrayList;
import java.io.File;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JList;

public class Encryptor {
    protected Frame parent_frame;
    protected String location = null;
    protected String end = null;
    protected String password = null;
    protected String operation = "enc";
    protected EncryptFiles ec = new EncryptFiles();

    Encryptor(String location, String password, String end, String operation, Frame parent) {
        parent_frame = parent;
        if (location == null) {
            System.exit(1);
        }
        if (password == null) {
            System.exit(1);
        }

        if (operation.equals("enc")) { // If the operation is enc then encryption is taking place
            if (location != null && password != null) {
                File f = FilesValidation.open(location);
                if (f.isFile()) {
                    byte fs[] = ec.fileStreamLoad(location);
                    if (!EncryptFiles.isEncrypted(fs) && password.trim() != "") {
                        ec.fileEncrypt(location, fs, password);
                    } else {
                        JOptionPane.showMessageDialog(parent_frame, "Could not Encrypt File\n" + location,
                                "File Encrypt Error (File Already Encrypted)", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    if (end != null) {
                        ArrayList<String> file_list = FilesValidation.getFolderFilesToEncrypt(location,
                                FilesValidation.open(location).listFiles(), end);
                        ArrayList<String> unencrypted = ec.encryptDirectory(file_list, password);
                        if (unencrypted.size() > 0) {
                            JList list = new JList(unencrypted.toArray());
                            JScrollPane pane = new JScrollPane(list);
                            JPanel panel = new JPanel();
                            panel.add(pane);
                            pane.getViewport().add(list);
                            JOptionPane.showMessageDialog(parent_frame, pane,
                                    "File Encrypt Error (File Already Encrypted)", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        ArrayList<String> file_list = FilesValidation.getFolderFilesToEncrypt(location,
                                FilesValidation.open(location).listFiles());
                        ArrayList<String> unencrypted = ec.encryptDirectory(file_list, password);
                        if (unencrypted.size() > 0) {
                            JList list = new JList(unencrypted.toArray());
                            JScrollPane pane = new JScrollPane(list);
                            JPanel panel = new JPanel();
                            panel.add(pane);
                            pane.getViewport().add(list);
                            JOptionPane.showMessageDialog(parent_frame, pane,
                                    "File Encrypt Error (File Already Encrypted)", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        } else { // Handle the decryption of the file triggered when the operation is not enc.
            if (location != null && password != null) {
                File f = FilesValidation.open(location);
                if (f.isFile()) { // Checks if the selected item is a single file, and in this case encrypts the
                                  // individual file
                    byte fs[] = ec.fileStreamLoad(location);
                    if (EncryptFiles.isEncrypted(fs)) {
                        ec.fileDecrypt(location, fs, password);
                    } else {
                        JOptionPane.showMessageDialog(parent_frame, "Could not decrypt File\n" + location,
                                "File Decrypt Error (Unencrypted File or wrong password)",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {// In case of folder selected the files in the folder are encrypted one by one.
                    if (end != null) {
                        ArrayList<String> file_list = FilesValidation.getFolderFilesToEncrypt(location,
                                FilesValidation.open(location).listFiles(), end);
                        ArrayList<String> undecrypted = ec.decryptDirectory(file_list, password);
                        if (undecrypted.size() > 0) {
                            JList list = new JList(undecrypted.toArray());
                            JScrollPane pane = new JScrollPane(list);
                            JPanel panel = new JPanel();
                            panel.add(pane);
                            pane.getViewport().add(list);
                            JOptionPane.showMessageDialog(parent_frame, pane,
                                    "File Decrypt Error (Unencrypted File or wrong password)",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        ArrayList<String> file_list = FilesValidation.getFolderFilesToEncrypt(location,
                                FilesValidation.open(location).listFiles());
                        ArrayList<String> undecrypted = ec.decryptDirectory(file_list, password);
                        if (undecrypted.size() > 0) {
                            JList list = new JList(undecrypted.toArray());
                            JScrollPane pane = new JScrollPane(list);
                            JPanel panel = new JPanel();
                            panel.add(pane);
                            pane.getViewport().add(list);
                            JOptionPane.showMessageDialog(parent_frame, pane,
                                    "File Decrypt Error (Unencrypted File or wrong password)",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }
    }

}