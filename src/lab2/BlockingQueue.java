package lab2;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class BlockingQueue<T> {

    T[] contents;
    int capacity;
    int momentalnoChlenovi;
    Semaphore semafor = new Semaphore(1);

    public BlockingQueue(int capacity) {
        contents = (T[]) new Object[capacity];
        this.capacity = capacity;
        momentalnoChlenovi=0;
    }

    public void enqueue(T item) throws InterruptedException {
        semafor.acquire();
        if(momentalnoChlenovi<capacity) {
            contents[momentalnoChlenovi++] = item;
            //System.out.println("Dodadov");
        }
        semafor.release();
    }

    public T dequeue() throws InterruptedException {
        semafor.acquire();
        //System.out.println(momentalnoChlenovi);
        if(momentalnoChlenovi>0)
        {
            //System.out.println("VLezen");
            T zachuvan = contents[0];
            T[] newArray = (T[]) new Object[momentalnoChlenovi-1];
            contents[0] = null;
            for(int i=1;i<momentalnoChlenovi;i++)
            {
                newArray[i-1]=contents[i];
            }
            momentalnoChlenovi--;
            //System.out.println("Deq'd");
            semafor.release();
            contents = (T[]) new Object[momentalnoChlenovi];
            for(int i=0;i<momentalnoChlenovi;i++) {
                contents[i] = newArray[i];
            }
            return zachuvan;
        }
        semafor.release();
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> bq = new BlockingQueue<>(5);
        String[] strings = new String[5];
        for(int i=0;i<5;i++)
        {
            String c = "Loading "+i;
            strings[i] = c;
        }
        ThreadEnq enq = new ThreadEnq(bq,strings);
        ThreadDeq deq = new ThreadDeq(bq,strings);
        enq.start();
        deq.start();
        enq.join();
        deq.join();
        System.out.println(Arrays.toString(bq.contents));
    }
}
class ThreadEnq extends Thread
{
    BlockingQueue<String> bq;
    String[] strings;
    public ThreadEnq(BlockingQueue<String> bq,String[] strings) {
        this.bq = bq;
        this.strings = strings;
    }

    @Override
    public void run() {
        for(int i=0;i<5;i++)
        {
            try {
                bq.enqueue(strings[i]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class ThreadDeq extends Thread
{
    BlockingQueue<String> bq;
    String[] strings;
    public ThreadDeq(BlockingQueue<String> bq,String[] strings) {
        this.bq = bq;
        this.strings = strings;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                bq.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}