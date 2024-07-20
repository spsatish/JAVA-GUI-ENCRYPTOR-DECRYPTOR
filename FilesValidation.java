import java.util.ArrayList;
import java.io.File;

public class FilesValidation{
    
    static File open(String f){ // f here will hold the destination or folder location:-
        return new File(f);
    }
    
    //Method to get the files inside a folder
    static ArrayList<String> getFolderFilesToEncrypt(String path,File f[]){
        ArrayList<String> files = new ArrayList<String>();
        for(int i=0;i<f.length;i++){
            if(f[i].isFile()){
                files.add(path+"/"+f[i].getName());
            }
        }
        return files;
    }  
    //Method to get the files inside a folder according to the file name
    static ArrayList<String> getFolderFilesToEncrypt(String path,File f[],String ext){
        ArrayList<String> files = new ArrayList<String>();
        for(int i=0;i<f.length;i++){
            if(f[i].isFile() && f[i].getName().endsWith(ext)){
                files.add(path+"/"+f[i].getName());
            }
        }
        return files;
    }
    
    
}

