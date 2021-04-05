package lab2;

import java.util.ArrayList;
import java.util.List;

public class TwoThreads {


    public static void main(String[] args) throws InterruptedException {
        List<Integer> lista = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            lista.add(i);
        }
        ThreadClassLettersNumbers spoeni = new ThreadClassLettersNumbers(lista);
        ThreadNumbers broevi = new ThreadNumbers(spoeni);
        ThreadChars chars = new ThreadChars(spoeni);
        chars.start();
        chars.join();
        broevi.start();
        broevi.join();
    }


}
class ThreadClassLettersNumbers{
    public List<Integer> lista;

    public ThreadClassLettersNumbers(List<Integer> lista) {
        this.lista = lista;
    }
}
class ThreadChars extends Thread{
    ThreadClassLettersNumbers tcln;

    public ThreadChars(ThreadClassLettersNumbers tcln) {
        this.tcln = tcln;
    }

    @Override
    public void run() {
        for(Integer z : tcln.lista)
        {
            int a=z;
            System.out.println((char)(a+65));
        }
    }
}
class ThreadNumbers extends Thread{
    ThreadClassLettersNumbers tcln;

    public ThreadNumbers(ThreadClassLettersNumbers tcln) {
        this.tcln = tcln;
    }

    @Override
    public void run() {
        for(Integer z : tcln.lista)
        {
            System.out.println(z);
        }
    }
}
/*
class ThreadClassNumbers extends Thread {

    @Override
    public void run() {
        for(int i = 0; i<10;i++) System.out.println(i);
    }
}


class ThreadClassLetters extends Thread {

    @Override
    public void run() {
        for(int i = 0; i<10;i++) System.out.println((char)(i + 65));
    }
}*/