package lab3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class BasketballTournament {

    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Player p = new Player();
            threads.add(p);
        }
        boolean term = false;
        for (Thread z : threads) {
            z.start();
            Thread.sleep(5000);
            if (z.isInterrupted()) {
                term = true;
                z.interrupt();
            }
        }

        // run all threads in background

        // after all of them are started, wait each of them to finish for maximum 5_000 ms

        // for each thread, terminate it if it is not finished
        if (term)
            System.out.println("Possible deadlock!");
        else
            System.out.println("Tournament finished.");
    }
}

class Player extends Thread {
    static Semaphore sala = new Semaphore(20);
    static Semaphore kabina = new Semaphore(10);
    static Semaphore coordLock = new Semaphore(1);
    static Semaphore ready = new Semaphore(0);
    static Semaphore done = new Semaphore(0);
    static Semaphore leave = new Semaphore(0);
    static int numPlayers = 0;

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
        coordLock.acquire();
        if (numPlayers == 19) // zemi go koordinator
        {
            numPlayers++;
            coordLock.release();
            sala.acquire();
            System.out.println("Player " + numPlayers + " inside.");
            kabina.acquire();
            System.out.println("In dressing room.");
            Thread.sleep(10);
            kabina.release();
            ready.release(20);
            ready.acquire();
            System.out.println("Game started.");
            Thread.sleep(100);
            done.release();
            System.out.println("Player done.");
            done.acquire(20);
            System.out.println("Game finished.");
            coordLock.acquire();
            numPlayers--;
            coordLock.release();
            sala.release();
            leave.release(20);
            leave.acquire();

        }
        else // obicen igrac
        {
            numPlayers++;
            coordLock.release();
            sala.acquire();
            System.out.println("Player " + numPlayers + " inside.");
            kabina.acquire();
            System.out.println("In dressing room.");
            Thread.sleep(10);// this represent the dressing time
            kabina.release();
            ready.acquire();
            System.out.println("Game started.");
            Thread.sleep(100);
            System.out.println("Player done.");
            done.release();
            leave.acquire();
            sala.release();
            coordLock.acquire();
            numPlayers--;
            coordLock.release();
        }
//        // at most 20 players should print this in parallel
//        System.out.println("Player inside.");
//        // at most 10 players may enter in the dressing room in parallel
//        System.out.println("In dressing room.");
//        Thread.sleep(10);// this represent the dressing time
//        // after all players are ready, they should start with the game together
//        System.out.println("Game started.");
//        Thread.sleep(100);// this represent the game duration
//        System.out.println("Player done.");
//        // only one player should print the next line, representing that the game has finished
//        System.out.println("Game finished.");
    }
}
