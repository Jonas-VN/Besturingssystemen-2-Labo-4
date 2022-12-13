package lab4;


import java.util.concurrent.Semaphore;

public class Proxy
{
    private static Proxy instance = null;
    private static int readerCount = 0;
    private static Semaphore readSemaphore = new Semaphore(1);
    private static Semaphore writerSemaphore = new Semaphore(1);


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
            readSemaphore.acquire();
            readerCount++;
            if (readerCount == 1) writerSemaphore.acquire();
        } catch (InterruptedException ignored) {
        } finally {
            readSemaphore.release();
        }

    }

    public void postRead(int pid)
    {
        // TODO: synchronization after read goes here
        try {
            readSemaphore.acquire();
            readerCount--;
            if (readerCount == 0) writerSemaphore.release();
        } catch (InterruptedException ignored) {
        } finally {
            readSemaphore.release();
        }
    }

    public void preWrite(int pid) {

        // TODO: synchronization before write goes here
        try {
            writerSemaphore.acquire();
        } catch (InterruptedException ignored) {
        }
    }

    public void postWrite(int pid)
    {
        // TODO: synchronization after write goes here
        writerSemaphore.release();
    }
}
