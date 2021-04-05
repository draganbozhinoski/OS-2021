import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class Server extends Thread implements Cwriter{
    int port;
    String filePath;
    ServerSocket serverSocket = null;
    PrintWriter pw = null;
    Semaphore sem = new Semaphore(1);
    public Server(int port,String filePath) throws FileNotFoundException {
        this.port = port;
        this.filePath = filePath;
        pw = new PrintWriter(new File(filePath));
    }


    @Override
    public void run() {
        super.run();
        System.out.println("Trying to start the server..");
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server failed to start..");
        }
        System.out.println("Server started!");
        while(true)
        {
            try{
                Socket socket = serverSocket.accept();
                new WorkerThread(socket,this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void writeToFile(String indeks,String nameSurname) throws InterruptedException {
        sem.acquire();
        pw.append(String.format("%s %s %s",indeks,nameSurname, LocalDateTime.now().toString()));
        pw.flush();
        sem.release();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Server serw = new Server(9999,"census.txt");
        serw.start();
    }
}
