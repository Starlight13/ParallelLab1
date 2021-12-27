import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class CAS {

    private String casName;
    private AtomicReference<Runnable> lockingThread = new AtomicReference<>();
    private LinkedBlockingQueue<Runnable> waitingThreads;

    public CAS(LinkedBlockingQueue<Runnable> waitingThreads, String casName) {
        this.waitingThreads = waitingThreads;
        this.casName = casName;
    }

    public void LOCK() {
        if (Thread.currentThread().equals(lockingThread.get())) {
            System.out.println("You can't lock twice");
            return;
        }
        while (!lockingThread.compareAndSet(null, Thread.currentThread())) {
            Thread.yield();
        }
        System.out.println(casName + " locked by: " + Thread.currentThread().getName());
    }

    public void UNLOCK() {
        if (!Thread.currentThread().equals(lockingThread.get())) {
            System.out.println("You can't unlock when the cas is not locked");
            return;
        }
        System.out.println(casName + " unlocked by: " + Thread.currentThread().getName());
        lockingThread.set(null);
    }

    public void WAIT() throws InterruptedException {
        Thread thread = Thread.currentThread();
        if (!thread.equals(lockingThread.get())) {
            System.out.println("You should lock before using wait");
            return;
        }
        waitingThreads.put(thread);
        System.out.println(casName + " waiting: " + Thread.currentThread().getName());
        UNLOCK();
        while (waitingThreads.contains(thread)) {
            Thread.yield();
        }
        LOCK();
        System.out.println(casName + " stopped waiting: " + Thread.currentThread().getName());
    }

    public void NOTIFY() throws InterruptedException {
        waitingThreads.take();
        System.out.println("Notify: " + Thread.currentThread().getName());
    }

    public void NOTIFYALL() {
        waitingThreads.clear();
        System.out.println("Notify all: " + Thread.currentThread().getName());
    }
}
