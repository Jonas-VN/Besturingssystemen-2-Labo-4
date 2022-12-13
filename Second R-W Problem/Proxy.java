package lab4;


import java.util.concurrent.Semaphore;

public class Proxy
{
    private static Proxy instance = null;
    private static int readerCount = 0;
    private static final Semaphore mutex = new Semaphore(1);
    private static final Semaphore readerCountProtection = new Semaphore(1);
    private static final Semaphore readSemaphore = new Semaphore(1);
    private static final Semaphore writerSemaphore = new Semaphore(1);


    private Proxy()
    {

    }

    public static synchronized Proxy getInstance()
    {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }

    public void preRead(int pid)
    {
        // TODO: synchronization before read goes here
        try {
            mutex.acquire();
            readSemaphore.acquire();
            readerCountProtection.acquire();
            readerCount++;
            if (readerCount == 1) writerSemaphore.acquire();
        } catch (InterruptedException ignored) {
        } finally {
            readerCountProtection.release();
        }

    }

    public void postRead(int pid)
    {
        // TODO: synchronization after read goes here
        try {
            readerCountProtection.acquire();
            readerCount--;
            if (readerCount == 0) writerSemaphore.release();
        } catch (InterruptedException ignored) {
        } finally {
            readerCountProtection.release();
            readSemaphore.release();
            mutex.release();
        }
    }

    public void preWrite(int pid) {

        // TODO: synchronization before write goes here
        try {
            readSemaphore.acquire();
            writerSemaphore.acquire();
        } catch (InterruptedException ignored) {
        }
    }

    public void postWrite(int pid)
    {
        // TODO: synchronization after write goes here
        writerSemaphore.release();
        readSemaphore.release();
    }
}
