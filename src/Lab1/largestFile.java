package Lab1;

import java.io.File;
import java.io.FileNotFoundException;

public class largestFile {
    public void findLargest(String filePath) throws FileNotFoundException {
        File fajl = new File(filePath);
        if(!fajl.exists())
        {
            throw new FileNotFoundException();
        }
        if(!fajl.isDirectory())
        {
            throw new FileNotFoundException();
        }
        File [] fajlovi = fajl.listFiles();
        String maxFile = " ";
        long maxLength=0;
        assert fajlovi != null;
        for(File z : fajlovi)
        {
            if(z.isFile()) {
                if (z.length() > maxLength) {
                    maxLength = z.length();
                    maxFile = z.getAbsolutePath();
                }
            }
        }
        System.out.println("Najgolemiot fajl vo datotekata e: " + maxFile + ", i ima golemina: " + maxLength);
    }
}
