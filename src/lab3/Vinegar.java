package lab3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Vinegar {
    static Semaphore C = new Semaphore(2);
    static Semaphore O = new Semaphore(2);
    static Semaphore H = new Semaphore(4);
    static Semaphore cLock = new Semaphore(1);
    static Semaphore ready = new Semaphore(0);
    static Semaphore cHere = new Semaphore(0);
    static Semaphore oHere = new Semaphore(0);
    static Semaphore hHere = new Semaphore(0);
    static Semaphore done = new Semaphore(0);
    static Semaphore leave = new Semaphore(0);
    static int numC = 0;
    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new C());
            threads.add(new H());
            threads.add(new H());
            threads.add(new O());
        }
        // run all threads in background
        boolean deadlock = false;
        for(Thread z : threads)
        {
            z.start();
            Thread.sleep(300);
            if(z.isInterrupted())
                deadlock=true;
        }

        // after all of them are started, wait each of them to finish for maximum 2_000 ms

        // for each thread, terminate it if it is not finished
        if(deadlock)
            System.out.println("Possible deadlock!");
        else
            System.out.println("Process finished.");

    }

    static class C extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {
            C.acquire();
            cLock.acquire();
            if(numC == 1) // koordinator
            {
                numC=0;
                cLock.release();
                cHere.release();
                System.out.println("C here.");
                cHere.acquire(2);
                oHere.acquire(2);
                hHere.acquire(4);
                ready.release(7);
                System.out.println("Molecule bonding.");
                Thread.sleep(100);
                System.out.println("C done.");
                done.acquire(7);
                System.out.println("Molecule created.");
                leave.release(7);
                C.release();
            }
            else // prv C
            {
                numC++;
                cLock.release();
                cHere.release();
                System.out.println("C here.");
                ready.acquire();
                System.out.println("Molecule bonding.");
                Thread.sleep(100);
                System.out.println("C done.");
                done.release();
                leave.acquire();
                C.release();
            }
        }
    }

    static class H extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {
            H.acquire();
            hHere.release();
            System.out.println("H here.");
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);
            System.out.println("H done.");
            done.release();
            leave.acquire();
            H.release();
        }
    }

    static class O extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void execute() throws InterruptedException {
            O.acquire();
            oHere.release();
            System.out.println("O here.");
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);
            System.out.println("O done.");
            done.release();
            leave.acquire();
            O.release();
        }
    }

}