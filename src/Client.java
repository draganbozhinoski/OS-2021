import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread{
    String serverName;
    Socket socket = null;
    int port;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    Scanner sc = null;

    public Client(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        try{
            socket = new Socket(serverName,port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String indeks = dis.readUTF();
            System.out.println(indeks);
            String ind = sc.nextLine();
            dos.writeUTF(ind);
            dos.flush();
            String nextCmd = dis.readUTF();
            System.out.println(nextCmd);
            String name = sc.nextLine();
            dos.writeUTF(name);
            dos.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client klient = new Client("localhost",9999);
        klient.start();
    }
}
