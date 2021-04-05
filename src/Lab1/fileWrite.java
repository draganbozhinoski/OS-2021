package Lab1;

import java.io.*;
import java.nio.Buffer;

public class fileWrite {
    public static void prochitajSamoglaski(File izvor,File destinacija) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(izvor)));
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
            String line = null;
            while((line = br.readLine()) != null)
            {
                int samoglaski=0;
                char [] charNiza = line.toCharArray();
                for(int i=0;i<charNiza.length;i++)
                {
                    if(Character.toLowerCase(charNiza[i]) == 'a' ||
                            Character.toLowerCase(charNiza[i]) == 'e' ||
                            Character.toLowerCase(charNiza[i]) == 'i' ||
                            Character.toLowerCase(charNiza[i]) == 'o' ||
                            Character.toLowerCase(charNiza[i]) == 'u')
                        samoglaski++;
                }
                bw.write("Za stringot " + line +" imame " + samoglaski + " samoglaski!");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Exception thrown IO!");
        }
        finally{
            if(br != null) br.close();
            if(bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        File izvor = new File("izvor.txt");
        File destination = new File("destinacija.txt");
        if(izvor.createNewFile())
        {
            System.out.println("Nema od kade da se chita");
        }
        destination.createNewFile();
        prochitajSamoglaski(izvor,destination);
    }
}
