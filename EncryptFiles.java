import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class EncryptFiles {

    private String arr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.*+-#@&*$%^)(!";

    byte[] fileStreamLoad(String fn) {
        byte buffer[];
        try {
            File fl = new File(fn);
            FileInputStream fo = new FileInputStream(fl);
            buffer = new byte[(int) fl.length()];
            fo.read(buffer, 0, (int) fl.length());
            fo.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            buffer = new byte[1];
            buffer[0] = -1;
            return buffer;
        }

    }

    static boolean isEncrypted(byte fs[]) {
        if (new String(Arrays.copyOfRange(fs, 0, 9)).equals("encrypted")) {
            return true;
        } else {
            return false;
        }
    }

    int fileEncrypt(String fn, byte fs[], String password) {
        if (password.trim() != "" && password.trim() != null) {
            try {
                FileOutputStream fw = new FileOutputStream(fn);
                String password_array[] = password.split("");
                fw.write("encrypted".getBytes()); // converts the encrypted word to byte and writes it to file
                for (int i = 0; i < fs.length; i++) { // traverses each byte and writes it to the file after performing
                                                      // the xor with password
                    fw.write(arr.indexOf(password_array[i % password.length()]) ^ fs[i]);
                }
                for (int i = 0; i < password_array.length; i++) {
                    fw.write(fs[i]); // Writing the password to the end of file
                }
                fw.close();
                return 0;
            } catch (IOException e) {
                System.out.println(e);
                return -1;
            }
        } else {
            return -1;
        }
    }

    int fileDecrypt(String fn, byte fs[], String password) { // Method to decrypt the file
        if (verifyPassword(password, fs)) { // first it checks if the file password is correct or not then it moves
                                            // further
            try {
                FileOutputStream fw = new FileOutputStream(fn); // FileoutputStream is acquired to to write the data to
                                                                // the file
                String password_array[] = password.split("");
                byte[] removedEncryptedFlagbyte = Arrays.copyOfRange(fs, 9, fs.length - password.length());
                for (int i = 0; i < removedEncryptedFlagbyte.length; i++) {
                    fw.write(arr.indexOf(password_array[i % password.length()]) ^ removedEncryptedFlagbyte[i]);
                }

                fw.close();
                return 0;
            } catch (IOException e) {
                System.out.println(e);
                return -1;
            }
        } else {
            return -1;
        }
    }

    ArrayList<String> encryptDirectory(ArrayList<String> fl, String password) {
        ArrayList<String> enc_err = new ArrayList<String>();
        for (int i = 0; i < fl.size(); i++) {
            byte fs[] = fileStreamLoad(fl.get(i));
            if (!isEncrypted(fs)) {
                if (fileEncrypt(fl.get(i), fs, password) != 0) {
                    enc_err.add(fl.get(i));
                }
            } else {
                enc_err.add(fl.get(i));
            }
        }
        return enc_err;
    }

    ArrayList<String> decryptDirectory(ArrayList<String> fl, String password) {
        ArrayList<String> enc_err = new ArrayList<String>();
        for (int i = 0; i < fl.size(); i++) {
            byte fs[] = fileStreamLoad(fl.get(i));
            if (isEncrypted(fs)) {
                if (fileDecrypt(fl.get(i), fs, password) != 0) {
                    enc_err.add(fl.get(i));
                }
            } else {
                enc_err.add(fl.get(i));
            }
        }
        return enc_err;
    }

    private boolean verifyPassword(String password, byte fs[]) {
        if (password.trim() != "" && password.trim() != null) {
            for (int i = 0; i < password.length(); i++) {
                if (arr.indexOf(password.charAt(i)) == ((fs[(fs.length - password.length()) + i]) ^ fs[9 + i])) {
                    continue;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}