import java.util.Scanner;
import java.util.*;

class Node<E> {
    protected E data;
    protected Node<E> next;
    protected Node<E> prev;

    public Node() {
        data = null;
        next = null;
        prev = null;
    }

    public Node(E data, Node<E> prev, Node<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}

class DLinkedList<E> {
    private Node<E> head, tail;

    public DLinkedList() {
        head = null;
        tail = null;
    }

    public void insertFirst(E e) {
        Node<E> first = new Node(e, null, head);

        if (head != null)
            head.prev = first;
        if (tail == null)
            tail = first;
        head = first;
    }

    public void insertLast(E e) {
        if (head != null) {
            Node<E> last = new Node(e, tail, null);
            tail.next = last;
            tail = last;
        } else
            this.insertFirst(e);
    }

    public void insertAfter(E e, Node<E> n) {
        if (n == tail) {
            insertLast(e);
            return;
        }
        Node<E> node = new Node(e, n, n.next);
        n.next.prev = node;
        n.next = node;
    }

    public void insertBefore(E e, Node<E> n) {
        if (n == head) {
            insertFirst(e);
            return;
        }
        Node<E> node = new Node(e, n.prev, n);
        n.prev.next = n;
        n.prev = n;
    }

    public void deleteFirst() {
        if (head != null) {
            Node<E> node = head;
            if (head.next == null)
                head = tail = null;
            else {
                head = head.next;
                head.prev = null;
            }
        }
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public void printList() {

        Node<E> dvizi = head;
        while (dvizi != null) {
            System.out.print(dvizi.data + " ");
            dvizi = dvizi.next;
        }

    }
}


public class Listi {

    public static DLinkedList<Integer> splitList(DLinkedList<DLinkedList<Integer>> lista) {
        Node<DLinkedList<Integer>> jazol = lista.getHead();
        DLinkedList<Integer> novaLista = new DLinkedList<>();
        while (jazol != null) {
            Node<Integer> podJazol = jazol.data.getHead();
            //System.out.println("Pocnue podjazol: " + podJazol.data);
            while (podJazol != null) {
                if (podJazol.data % 2 == 0) {
                    novaLista.insertLast(podJazol.data);
                }
                podJazol = podJazol.next;
            }
            jazol = jazol.next;
        }
        return novaLista;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DLinkedList<DLinkedList<Integer>> lista = new DLinkedList<>();
        System.out.println("Vnesi broj na kutie:");
        int n = in.nextInt();//broj na jazli vo golemata lista
        System.out.println("Vnesi broj na topcinja po kutija:");
        int m = in.nextInt();//broj na jazli vo podlistite

        for (int i = 0; i < n; i++) {
            System.out.println("Vnesuvaj vo Kutija " + i);
            DLinkedList<Integer> temp = new DLinkedList();
            for (int j = 0; j < m; j++) {
                System.out.println("Vnesi topce broj: " + j);
                int broj = in.nextInt();
                temp.insertLast(broj);
            }
            lista.insertLast(temp);
        }
        DLinkedList<Integer> novaLista = splitList(lista);
        System.out.println("Novata lista e: ");
        novaLista.printList();
    }
}
