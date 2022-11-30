package lab4;


public class Proxy
{
    private static Proxy instance = null;
    private int readers = 0;
    private int writers = 0;


    private Proxy()
    {
        
    }

    public static synchronized Proxy getInstance()
    {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }

    private void Wait() {
        try {
            wait();
        } catch (InterruptedException ignored) {
        }
    }

    public synchronized void preRead(int pid)
    {
        // TODO: synchronization before read goes here
        while (writers > 0)
            Wait();
        readers++;
    }

    public synchronized void postRead(int pid)
    {
        // TODO: synchronization after read goes here
        readers--;
        notifyAll();
    }

    public synchronized void preWrite(int pid) {

        // TODO: synchronization before write goes here
        while (readers > 0 || writers> 0) {
            Wait();
        }
        writers++;
    }

    public synchronized void postWrite(int pid)
    {
        // TODO: synchronization after write goes here
        writers--;
        notifyAll();
    }
}
