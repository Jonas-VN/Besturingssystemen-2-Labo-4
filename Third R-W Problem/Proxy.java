package lab4;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.concurrent.locks.ReentrantReadWriteLock.*;


// writer kan niet in de kritieke sectie terwijl een andere thread erin zit.
//zolang een writer erin zit kan geen andere erin. (geen readers/writers)
public class Proxy {
    private int readers = 0;
    private int writers = 0;

    private int writeReq = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static Proxy instance = null;

    private Proxy() {
    }


    public static synchronized Proxy getInstance() {
        if (instance == null)
            instance = new Proxy();
        return instance;
    }


    public void preRead(int pid) {
        // TODO: synchronization before read goes here
        lock.readLock().lock();

    }

    public void postRead(int pid) {
        // TODO: synchronization after read goes here
        lock.readLock().unlock();

    }
        public void preWrite (int pid){
            // TODO: synchronization before write goes here
            lock.writeLock().lock();
        }

        public void postWrite (int pid){
            // TODO: synchronization after write goes here
            lock.writeLock().unlock();

        }
    }
