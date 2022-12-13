package lab4;


import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class Proxy {
    private static Proxy instance = null;

    Semaphore mutex = new Semaphore(1);
    Semaphore rmutex = new Semaphore(10);
    Semaphore wmutex = new Semaphore(1);
    int readerCount = 0;

    AtomicInteger readers;

    private Proxy() {
    }

    public static synchronized Proxy getInstance() {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }


    public void preRead(int pid) {
        // TODO: synchronization before read goes here
        try {
            //mutex.acquire();
            rmutex.acquire();
          //  readerCount++;
            readers.incrementAndGet();
           /* if (readerCount == 1) */wmutex.acquire();
            //mutex.release();
            rmutex.release();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void postRead(int pid) {
        // TODO: synchronization after read goes here
        try {
            rmutex.acquire();
            //readerCount--;
            readers.getAndDecrement();
          /*  if (readerCount == 0)*/ wmutex.release();
            rmutex.release();
            //mutex.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void preWrite(int pid) {
        // TODO: synchronization before write goes here
        try {
           // mutex.acquire();
            wmutex.acquire();
           /* mutex.release();*/
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void postWrite(int pid) {
        // TODO: synchronization after write goes here
       // mutex.release();
        wmutex.release();
    }
}
