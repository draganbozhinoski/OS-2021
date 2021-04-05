package Lab1;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class imenik {
    public static void findRecursive(File f, Date datum) throws FileNotFoundException {
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        if (!f.isDirectory()) {
            throw new FileNotFoundException();
        }

        for (File z : f.listFiles()) {
            if (f.isDirectory())
                findRecursive(f, datum);
        }
        Date fileDate = new Date(f.lastModified());
        if (f.isFile()) {
            if (f.getName().endsWith(".jpg") && fileDate.after(datum) || f.getName().endsWith(".bmp") && fileDate.after(datum)) {
                System.out.println(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String fileString = br.readLine();
        File fajl = new File(fileString);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-7);
        Date sedumDate = c.getTime();
        findRecursive(fajl,sedumDate);
    }
}
