package lab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class CountLetter {
        /**
         * Promenlivata koja treba da go sodrzi brojot na pojavuvanja na bukvata A
         */
        Semaphore s = new Semaphore(1);
        static int count = 0;
        /**
         * TODO: definirajte gi potrebnite elementi za sinhronizacija
         */

        public void init() {
        }

        class Counter extends Thread {

            public void count(String data) throws InterruptedException {
                s.acquire();
                count += (int) data.chars().filter(a -> a == 'E').count();
                s.release();
            }
            private String data;

            public Counter(String data) {
                this.data = data;
            }

            @Override
            public void run() {
                try {
                    count(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            try {
                CountLetter environment = new CountLetter();
                environment.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void start() throws Exception {

            init();

            HashSet<Thread> threads = new HashSet<Thread>();
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            String pom = bf.readLine();
            String [] data = pom.split("");

            for(int i = 0; i< data.length; i++) {

                Counter c = new Counter(data[i]);
                threads.add(c);
            }
            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }
            System.out.println(count);


        }
}
