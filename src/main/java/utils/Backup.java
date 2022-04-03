package utils;

import java.io.*;
import java.net.URL;
import java.util.List;


public class Backup {
        public static <K> void save(List<K> savableFile, String path) throws IOException, SecurityException {
            if (!path.substring(path.lastIndexOf(".")+1).equals("knk"))
                path += ".knk";
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(savableFile);
            oos.close();
        }

        @SuppressWarnings("unchecked")
        public static <K> List<K> open(String path) throws IOException, ClassNotFoundException, SecurityException {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<K> fileReaded = (List<K>) ois.readObject();
            ois.close();
            return fileReaded;
        }
    }

