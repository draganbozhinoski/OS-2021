import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class WorkerThread extends Thread{
    Socket socket = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    Cwriter censusWriter;
    public WorkerThread(Socket socket,Cwriter census) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        censusWriter = census;
    }

    @Override
    public void run() {
        try {
            dos.writeUTF("Vnesi indeks: ");
            dos.flush();
            String indeks = dis.readUTF();
            dos.writeUTF("Vnesi ime i prezime: ");
            dos.flush();
            String imePrezime = dis.readUTF();
            censusWriter.writeToFile(indeks,imePrezime);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try{
            if(dis != null)
            {
                dis.close();
            }
            if(dos != null)
            {
                dos.flush();
                dos.close();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
