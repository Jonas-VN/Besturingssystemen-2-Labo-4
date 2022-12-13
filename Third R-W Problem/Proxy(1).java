package lab4;


import java.util.concurrent.Semaphore;



public class Proxy {
    private static Proxy instance = null;

    Semaphore ordermutex = new Semaphore(1);
    Semaphore rmutex = new Semaphore(1);
    Semaphore accessmutex = new Semaphore(1);
    int readerCount = 0;

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
            ordermutex.acquire();
            rmutex.acquire();
            if (readerCount == 0) accessmutex.acquire();
            readerCount++;

            ordermutex.release();
            rmutex.release();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void postRead(int pid) {
        // TODO: synchronization after read goes here
        try {
            rmutex.acquire();
            readerCount--;
            if (readerCount == 0) accessmutex.release();
            rmutex.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void preWrite(int pid) {
        // TODO: synchronization before write goes here
        try {
            ordermutex.acquire();
            accessmutex.acquire();
            ordermutex.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void postWrite(int pid) {
        // TODO: synchronization after write goes here
        accessmutex.release();
    }
}
