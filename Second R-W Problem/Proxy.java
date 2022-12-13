package lab4;


import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Proxy
{
    private static Proxy instance = null;
    private static final AtomicInteger readerCount = new AtomicInteger(0);
    private static final Semaphore mutex = new Semaphore(1);
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
            readerCount.incrementAndGet();
            if (readerCount.get() == 1) writerSemaphore.acquire();
        } catch (InterruptedException ignored) {
        }
    }

    public void postRead(int pid)
    {
        // TODO: synchronization after read goes here
        readerCount.decrementAndGet();
        if (readerCount.get() == 0) writerSemaphore.release();
        readSemaphore.release();
        mutex.release();
    }

    public void preWrite(int pid)
    {
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
