package lab3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Singleton extends Thread{
    static Semaphore numLock = new Semaphore(1);
    static int zemeni = 0;
    private static volatile Singleton singleton;

    private Singleton() {

    }

    @Override
    public void run() {
        super.run();
        try {
            getInstance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Singleton getInstance() throws InterruptedException {
        // TODO: 3/29/20 Synchronize this
        numLock.acquire();
        if(zemeni==0) {
            zemeni++;
            numLock.release();
            singleton = new Singleton();
            System.out.println("Zemam istanca..");
            return singleton;
        }
        numLock.release();
        System.out.println("Ne e vratena istanca");
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for(int i=0;i<200;i++)
        {
            threads.add(new Singleton());
        }
        for(Thread z : threads)
        {
            z.start();
            Thread.sleep(200);
        }
        // TODO: 3/29/20 Simulate the scenario when multiple threads call the method getInstance
    }

}