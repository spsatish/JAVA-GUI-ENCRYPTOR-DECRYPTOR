import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GInterface implements ActionListener {
    private JButton file_chooser;
    private JButton encrypt;
    private JButton decrypt;
    private JFrame win;
    private String folder_name;
    private JTextField text_field;
    private JTextField end_input;
    private JLabel location_name;

    GInterface() {
        win = new JFrame("Encryption Utility");
        win.setLocationRelativeTo(null);
        win.setSize(800, 400);
        win.setLayout(null);
        win.setResizable(false);
        ImageIcon icon = new ImageIcon("icon.png");
        win.setIconImage(icon.getImage());
        Color window_frame_color = new Color(255, 255, 255);
        win.getContentPane().setBackground(window_frame_color);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color button_color = new Color(245, 245, 243, 243);

        Font label_font = new Font("Serif", Font.BOLD, 20); // Font for label
        JLabel file_label = new JLabel("Select Folder for Encryption ");// Label Instruction for choose file
        file_label.setFont(label_font);
        file_label.setBounds(0, 0, 300, 50); // Setting up the label position

        location_name = new JLabel("");
        location_name.setBounds(300, 40, 300, 20); // Setting up the label position

        // button for choosing file
        file_chooser = new JButton("Choose Folder");
        file_chooser.setBounds(300, 10, 300, 30);
        file_chooser.setFocusPainted(false); // remove the selection around the button text on click
        file_chooser.setBackground(window_frame_color);
        file_chooser.addActionListener(this);

        // Label for taking password input

        JLabel password_label = new JLabel("Enter The Password ");
        password_label.setFont(label_font);
        password_label.setBounds(0, 70, 300, 50);

        // Input for taking password
        text_field = new JTextField();
        text_field.setFont(label_font);
        text_field.setBounds(300, 80, 300, 30);

        // Label to take end of the
        JLabel end_input_label = new JLabel("Enter the end/extension of file");
        end_input_label.setFont(label_font);
        end_input_label.setBounds(0, 140, 300, 50);

        // File End input

        end_input = new JTextField();
        end_input.setFont(label_font);
        end_input.setBounds(300, 150, 300, 30);

        // EncryptButton

        encrypt = new JButton("Encrypt");
        encrypt.setFont(label_font);
        encrypt.setBackground(button_color);
        encrypt.setFocusPainted(false);
        encrypt.setBounds(25, 250, 300, 40);
        encrypt.addActionListener(this);

        decrypt = new JButton("Decrypt");
        decrypt.setFont(label_font);
        decrypt.setBackground(button_color);
        decrypt.setFocusPainted(false);
        decrypt.setBounds(350, 250, 300, 40);
        decrypt.addActionListener(this);

        win.add(file_label);
        win.add(file_chooser);
        win.add(password_label);
        win.add(text_field);
        win.add(end_input_label);
        win.add(end_input);
        win.add(encrypt);
        win.add(decrypt);
        win.add(location_name);
        win.setVisible(true);
    }

    public static void main(String ar[]) {
        GInterface ob = new GInterface();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encrypt) {
            Encryptor ob = new Encryptor(folder_name, text_field.getText(), end_input.getText(), "enc", win);
            JOptionPane.showMessageDialog(this.win, "Encryption Process Completed!", "Encrypted",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == decrypt) {
            Encryptor ob = new Encryptor(folder_name, text_field.getText(), end_input.getText(), "dec", win);
            JOptionPane.showMessageDialog(this.win, "Decryption Process Completed!", "Decrypted",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == file_chooser) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (fileChooser.showOpenDialog(this.win) == JFileChooser.APPROVE_OPTION) {
                folder_name = fileChooser.getSelectedFile().getAbsolutePath();
                location_name.setText(folder_name);
            }
        }
    }
}
