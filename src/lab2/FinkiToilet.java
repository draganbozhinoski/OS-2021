package lab2;

import java.util.concurrent.Semaphore;

public class FinkiToilet {

    public static class Toilet {

        public void vlezi() {
            System.out.println("Vleguva...");
        }

        public void izlezi() {
            System.out.println("Izleguva...");
        }
    }
    static Semaphore toiletSem;
    static Semaphore manQ;
    static Semaphore womanQ;
    static int numWoman;
    static int numMan;
    public static void init() {
        toiletSem = new Semaphore(1);
        manQ = new Semaphore(1);
        womanQ = new Semaphore(1);
        numWoman = 0;
        numMan = 0;
    }

    public static class Man extends Thread{

        private Toilet toilet;

        public Man(Toilet toilet) {
            this.toilet = toilet;
        }

        public void enter() throws InterruptedException {
            manQ.acquire();
            if(numMan == 0)
            {
                toiletSem.acquire();
                toilet.vlezi();
            }
            numMan++;
            manQ.release();
        }

        public void exit() throws InterruptedException {
            manQ.acquire();
            toilet.izlezi();
            numMan--;
            if(numMan==0)
                toiletSem.release();
            manQ.release();
        }

        @Override
        public void run() {
            super.run();
        }
    }

    public static class Woman extends Thread{

        private Toilet toilet;

        public Woman(Toilet toilet) {
            this.toilet = toilet;
        }

        public void enter() throws InterruptedException {
        }

        public void exit() throws InterruptedException {
        }

        @Override
        public void run() {
            super.run();
        }
    }
}

